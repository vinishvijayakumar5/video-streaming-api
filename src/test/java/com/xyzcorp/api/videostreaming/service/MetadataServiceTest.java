package com.xyzcorp.api.videostreaming.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyzcorp.api.videostreaming.AbstractTestData;
import com.xyzcorp.api.videostreaming.dto.MetadataResponseDto;
import com.xyzcorp.api.videostreaming.dto.MetadataSearchRequestDto;
import com.xyzcorp.api.videostreaming.entity.Metadata;
import com.xyzcorp.api.videostreaming.exception.DataNotFoundException;
import com.xyzcorp.api.videostreaming.exception.VideoExistsException;
import com.xyzcorp.api.videostreaming.repository.MetadataRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class MetadataServiceTest extends AbstractTestData {

    @Mock
    private MetadataRepository metadataRepository;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private ImpressionService impressionService;
    @InjectMocks
    private MetadataService metadataService;

    @Test
    void test_persist() {
        Mockito.when(metadataRepository.save(Mockito.any())).thenReturn(getMetadata());
        Mockito.when(objectMapper.convertValue(Mockito.any(), Mockito.eq(Metadata.class)))
                .thenReturn(getMetadata());

        Metadata metadata = metadataService.persist(getVideoPublishRequestDto());

        Assertions.assertNotNull(metadata);
        Assertions.assertEquals(1, metadata.getId());
        Assertions.assertEquals("Europe", metadata.getTitle());
        Assertions.assertEquals("c1, c3, c5", metadata.getCaste());
        Assertions.assertEquals("GEN", metadata.getGenre());
        Assertions.assertEquals("3h", metadata.getRunningTime());
        Assertions.assertEquals("KPM", metadata.getDirector());
        Assertions.assertEquals("SYS", metadata.getSynopsis());
        Assertions.assertEquals(2022, metadata.getYearOfRelease());
    }

    @Test
    void test_persist_throwError_whenTitleExists() {
        Mockito.when(metadataRepository.findByTitle(Mockito.anyString())).thenReturn(getMetadata());

        VideoExistsException videoExistsException = assertThrows(VideoExistsException.class,
                () -> metadataService.persist(getVideoPublishRequestDto()));

        assertEquals("Title already exists", videoExistsException.getMessage());
    }

    @Test
    void test_delete() {
        Mockito.when(metadataRepository.delete(1)).thenReturn(1);

        metadataService.delete(1);

        Mockito.verify(metadataRepository, Mockito.times(1)).delete(1);
        Mockito.verify(impressionService, Mockito.times(1)).deleteImpressions(1);
    }

    @Test
    void test_delete_throwError() {
        Mockito.when(metadataRepository.delete(1)).thenReturn(0);

        DataNotFoundException dataNotFoundException = assertThrows(DataNotFoundException.class,
                () -> metadataService.delete(1));

        assertEquals("Video metadata not found", dataNotFoundException.getMessage());

        Mockito.verify(metadataRepository, Mockito.times(1)).delete(1);
        Mockito.verify(impressionService, Mockito.never()).deleteImpressions(1);
    }

    @Test
    void test_isTitleExists() {
        Mockito.when(metadataRepository.findByTitle(Mockito.anyString())).thenReturn(getMetadata());

        boolean found = metadataService.isTitleExists("Europe");

        Assertions.assertTrue(found);
        Mockito.verify(metadataRepository, Mockito.times(1)).findByTitle("Europe");
    }

    @Test
    void test_get() {
        Mockito.when(metadataRepository.findAllMetadata()).thenReturn(List.of(getMetadata()));
        Mockito.when(objectMapper.convertValue(Mockito.any(), Mockito.eq(MetadataResponseDto.class)))
                .thenReturn(getMetadataResponseDto());

        List<MetadataResponseDto> metadataList = metadataService.get();

        Assertions.assertNotNull(metadataList);
        Assertions.assertEquals(1, metadataList.size());
        Assertions.assertEquals("Europe", metadataList.get(0).getTitle());
        Assertions.assertEquals("c1, c3, c5", metadataList.get(0).getCaste());
        Assertions.assertEquals("GEN", metadataList.get(0).getGenre());
        Assertions.assertEquals("3h", metadataList.get(0).getRunningTime());
        Assertions.assertEquals("KPM", metadataList.get(0).getDirector());
        Assertions.assertEquals("SYS", metadataList.get(0).getSynopsis());
        Assertions.assertEquals(2022, metadataList.get(0).getYearOfRelease());
    }

    @Test
    void test_addImpression() {
        Metadata metadata = getMetadata();
        Mockito.when(metadataRepository.findMetadataById(1)).thenReturn(metadata);

        metadataService.addImpression(1);

        Mockito.verify(impressionService, Mockito.times(1)).addImpression(metadata);
    }

    @Test
    void test_addImpression_whenNoMetadataFound() {
        Mockito.when(metadataRepository.findMetadataById(1)).thenReturn(null);

        metadataService.addImpression(1);

        Mockito.verify(impressionService, Mockito.never()).addImpression(getMetadata());
    }

    @Test
    void test_search() {
        Mockito.when(metadataRepository.findAll(Mockito.any(Specification.class))).thenReturn(List.of(getMetadata()));
        Mockito.when(objectMapper.convertValue(Mockito.any(), Mockito.eq(MetadataResponseDto.class)))
                .thenReturn(getMetadataResponseDto());

        List<MetadataResponseDto> dtoList = metadataService.search(MetadataSearchRequestDto.builder()
                        .title("Europe")
                .build());

        Assertions.assertNotNull(dtoList);
        Assertions.assertEquals(1, dtoList.size());
        Assertions.assertEquals("Europe", dtoList.get(0).getTitle());
        Assertions.assertEquals("c1, c3, c5", dtoList.get(0).getCaste());
        Assertions.assertEquals("GEN", dtoList.get(0).getGenre());
        Assertions.assertEquals("3h", dtoList.get(0).getRunningTime());
        Assertions.assertEquals("KPM", dtoList.get(0).getDirector());
        Assertions.assertEquals("SYS", dtoList.get(0).getSynopsis());
        Assertions.assertEquals(2022, dtoList.get(0).getYearOfRelease());
    }

    @Test
    void test_search_whenNoData() {
        Mockito.when(metadataRepository.findAll(Mockito.any(Specification.class))).thenReturn(List.of());

        List<MetadataResponseDto> dtoList = metadataService.search(MetadataSearchRequestDto.builder()
                .title("Europe")
                .build());

        Assertions.assertNotNull(dtoList);
        Assertions.assertEquals(0, dtoList.size());
    }

    @Test
    void test_getImpressionCount() {
        Mockito.when(impressionService.getImpressionCount(1)).thenReturn(1);

        int count = metadataService.getImpressionCount(1);

        Assertions.assertEquals(1, count);
        Mockito.verify(impressionService, Mockito.times(1)).getImpressionCount(1);
    }
}