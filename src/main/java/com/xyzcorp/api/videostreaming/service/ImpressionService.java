package com.xyzcorp.api.videostreaming.service;

import com.xyzcorp.api.videostreaming.entity.Impressions;
import com.xyzcorp.api.videostreaming.entity.Metadata;
import com.xyzcorp.api.videostreaming.repository.ImpressionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ImpressionService {

    private ImpressionRepository impressionRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteImpressions(long metadataId) {
        impressionRepository.delete(metadataId);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void addImpression(Metadata metadata) {
        impressionRepository.save(Impressions.builder()
                .metadata(metadata)
                .createdOn(LocalDateTime.now())
                .build());
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public int getImpressionCount(long metadataId) {
        return impressionRepository.countByMetadataId(metadataId);
    }
}
