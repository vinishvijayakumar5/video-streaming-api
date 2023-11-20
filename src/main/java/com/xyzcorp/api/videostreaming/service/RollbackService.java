package com.xyzcorp.api.videostreaming.service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RollbackService {

    private MetadataService metadataService;
    private ContentService contentService;

    /**
     * This method delete the video content and its associated metadata,
     * views and impressions
     * @param contentId
     * @return ResponseEntity with HTTP STATUS 204
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity delete(String contentId) {
        long parsedContentId = Long.valueOf(contentId);

        long metadataId = contentService.getMetadataId(parsedContentId);

        // delete content, then on success delete views
        contentService.delete(parsedContentId);

        // delete metadata, then on success delete impression
        metadataService.delete(metadataId);

        return ResponseEntity.noContent().build();
    }

}
