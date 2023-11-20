package com.xyzcorp.api.videostreaming.service;

import com.xyzcorp.api.videostreaming.dto.VideoPublishRequestDto;
import com.xyzcorp.api.videostreaming.entity.Metadata;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@AllArgsConstructor
public class PublishingService {

    private MetadataService metadataService;
    private ContentService contentService;

    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity publish(VideoPublishRequestDto request) {
        Metadata metadata = metadataService.persist(request);
        long contentId = contentService.persist(request.getContent(), metadata);
        return ResponseEntity.ok(Map.of("contentId", contentId));
    }

}
