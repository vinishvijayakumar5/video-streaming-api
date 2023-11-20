package com.xyzcorp.api.videostreaming.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyzcorp.api.videostreaming.dto.ContentResponseDto;
import com.xyzcorp.api.videostreaming.dto.ContentWithMetadataResponseDto;
import com.xyzcorp.api.videostreaming.entity.Content;
import com.xyzcorp.api.videostreaming.entity.Metadata;
import com.xyzcorp.api.videostreaming.eventsource.event.ImpressionEvent;
import com.xyzcorp.api.videostreaming.exception.DataNotFoundException;
import com.xyzcorp.api.videostreaming.repository.ContentRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
public class ContentService {

    private ContentRepository contentRepository;
    private ObjectMapper objectMapper;
    private ApplicationEventPublisher applicationEventPublisher;
    private ViewsService viewsService;

    @Transactional(propagation = Propagation.MANDATORY)
    public long persist(String content, Metadata metadata) {
        Content savedContent = contentRepository.save(Content.builder()
                .source(content)
                .metadata(metadata)
                .createdOn(LocalDateTime.now())
                .build());
        return savedContent.getId();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void delete(long id) {
        int deleted = contentRepository.delete(id);
        if(deleted > 0) {
            viewsService.deleteViews(id);
        } else {
            throw new DataNotFoundException("Video content not found", "E101", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public long getMetadataId(long id) {
        return  getById(id).getMetadata().getId();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public ContentWithMetadataResponseDto loadContent(long id) {
        Content content = getById(id);

        // publish impression
        applicationEventPublisher.publishEvent(ImpressionEvent.builder()
                .contentId(id)
                .metaId(content.getMetadata().getId())
                .build());

        return map(content);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ContentResponseDto getContentToPlay(long id) {
        Content content = getById(id);

        // add view
        viewsService.addView(content);

        return ContentResponseDto.builder()
                .source(content.getSource())
                .build();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Content getById(long id) {
        Content content = contentRepository.findContentById(id);
        if(nonNull(content)) {
            return content;
        }
        throw new DataNotFoundException("Video content not found", "E104", HttpStatus.NOT_FOUND);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public int getViewsCount(long contentId) {
        return viewsService.getViewsCount(contentId);
    }

    private ContentWithMetadataResponseDto map(Content content) {
        ContentWithMetadataResponseDto dto = objectMapper.convertValue(content.getMetadata(),
                ContentWithMetadataResponseDto.class);
        dto.setSource(content.getSource());
        return dto;
    }

}
