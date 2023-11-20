package com.xyzcorp.api.videostreaming.service;

import com.xyzcorp.api.videostreaming.dto.ContentResponseDto;
import com.xyzcorp.api.videostreaming.dto.ContentWithMetadataResponseDto;
import com.xyzcorp.api.videostreaming.dto.MetadataResponseDto;
import com.xyzcorp.api.videostreaming.dto.StatisticsResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class StreamingService {

    private MetadataService metadataService;
    private ContentService contentService;

    @Transactional(propagation = Propagation.REQUIRED)
    public ContentWithMetadataResponseDto loadContent(String contentId) {
        return contentService.loadContent(Long.valueOf(contentId));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ContentResponseDto getContentToPlay(String contentId) {
        return contentService.getContentToPlay(Long.valueOf(contentId));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<MetadataResponseDto> getMetadata() {
        return metadataService.get();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public StatisticsResponseDto getStatistics(String contentId) {
        long parsedContentId = Long.valueOf(contentId);
        long metadataId = contentService.getMetadataId(parsedContentId);
        return StatisticsResponseDto.builder()
                .views(contentService.getViewsCount(parsedContentId))
                .impression(metadataService.getImpressionCount(metadataId))
                .build();
    }

}
