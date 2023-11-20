package com.xyzcorp.api.videostreaming.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyzcorp.api.videostreaming.dto.MetadataResponseDto;
import com.xyzcorp.api.videostreaming.dto.MetadataSearchRequestDto;
import com.xyzcorp.api.videostreaming.dto.VideoPublishRequestDto;
import com.xyzcorp.api.videostreaming.entity.Metadata;
import com.xyzcorp.api.videostreaming.exception.DataNotFoundException;
import com.xyzcorp.api.videostreaming.exception.VideoExistsException;
import com.xyzcorp.api.videostreaming.repository.MetadataRepository;
import com.xyzcorp.api.videostreaming.specs.MetadataSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@Service
@AllArgsConstructor
public class MetadataService {

    private MetadataRepository metadataRepository;
    private ObjectMapper objectMapper;
    private ImpressionService impressionService;

    @Transactional(propagation = Propagation.MANDATORY)
    public Metadata persist(VideoPublishRequestDto request) {
        if(!isTitleExists(request.getTitle())) {
            return metadataRepository.save(map(request));
        }
        throw new VideoExistsException("Title already exists", "E100", HttpStatus.BAD_REQUEST);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void delete(long id) {
        int deleted = metadataRepository.delete(id);
        if(deleted > 0) {
            impressionService.deleteImpressions(id);
        } else {
            throw new DataNotFoundException("Video metadata not found", "E101", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public boolean isTitleExists(String title) {
        return nonNull(metadataRepository.findByTitle(title));
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<MetadataResponseDto> get() {
        return emptyIfNull(metadataRepository.findAllMetadata()).stream()
                .map(this::mapMetadata)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addImpression(long metadataId) {
        Metadata metadata = metadataRepository.findMetadataById(metadataId);
        if(nonNull(metadata)) {
            impressionService.addImpression(metadata);
        }
    }

    /**
     * Search is implemented using JPA Specification.
     * This can also be implemented using Criteria query with predicates.
     * The reason for the selection of JPA Specification is simplicity!
     * @param request
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<MetadataResponseDto> search(MetadataSearchRequestDto request) {
        List<Metadata> metadata = metadataRepository.findAll(getMetadataSpecification(request));
        return emptyIfNull(metadata).stream()
                .map(this::mapMetadata)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public int getImpressionCount(long metadataId) {
        return impressionService.getImpressionCount(metadataId);
    }

    private Metadata map(VideoPublishRequestDto request) {
        Metadata metadata = objectMapper.convertValue(request, Metadata.class);
        metadata.setCreatedOn(LocalDateTime.now());
        return metadata;
    }

    private MetadataResponseDto mapMetadata(Metadata metadata) {
        return objectMapper.convertValue(metadata, MetadataResponseDto.class);
    }

    private static Specification<Metadata> getMetadataSpecification(MetadataSearchRequestDto request) {
        return Specification.where(
                MetadataSpecifications.titleContains(request.getTitle())
                .or(MetadataSpecifications.directorContains(request.getDirector()))
                .or(MetadataSpecifications.casteContains(request.getCaste()))
                .or(MetadataSpecifications.genreContains(request.getGenre()))
                .or(MetadataSpecifications.hasYearOfRelease(request.getYearOfRelease())))
                .and(MetadataSpecifications.isNotDeleted());
    }

}
