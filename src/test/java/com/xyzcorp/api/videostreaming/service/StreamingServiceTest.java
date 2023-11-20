package com.xyzcorp.api.videostreaming.service;

import com.xyzcorp.api.videostreaming.AbstractTestData;
import com.xyzcorp.api.videostreaming.dto.ContentResponseDto;
import com.xyzcorp.api.videostreaming.dto.ContentWithMetadataResponseDto;
import com.xyzcorp.api.videostreaming.dto.MetadataResponseDto;
import com.xyzcorp.api.videostreaming.dto.StatisticsResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class StreamingServiceTest extends AbstractTestData {

    @Mock
    private MetadataService metadataService;
    @Mock
    private ContentService contentService;
    @InjectMocks
    private StreamingService streamingService;

    @Test
    void test_loadContent() {
        Mockito.when(contentService.loadContent(Mockito.anyLong())).thenReturn(getContentWithMetadataResponseDto());

        ContentWithMetadataResponseDto dto = streamingService.loadContent("1");

        Assertions.assertNotNull(dto);
        Assertions.assertEquals("Europe", dto.getTitle());
        Assertions.assertEquals("c1, c3, c5", dto.getCaste());
        Assertions.assertEquals("GEN", dto.getGenre());
        Assertions.assertEquals("3h", dto.getRunningTime());
        Assertions.assertEquals("SRC", dto.getSource());
        Assertions.assertEquals("KPM", dto.getDirector());
        Assertions.assertEquals("SYS", dto.getSynopsis());
        Assertions.assertEquals(2022, dto.getYearOfRelease());
    }

    @Test
    void test_getContentToPlay() {
        Mockito.when(contentService.getContentToPlay(Mockito.anyLong())).thenReturn(getContentResponseDto());

        ContentResponseDto dto = streamingService.getContentToPlay("1");

        Assertions.assertNotNull(dto);
        Assertions.assertEquals("SRC", dto.getSource());
    }

    @Test
    void test_testGetMetadata() {
        Mockito.when(metadataService.get()).thenReturn(List.of(getMetadataResponseDto()));

        List<MetadataResponseDto> dtoList = streamingService.getMetadata();

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
    void test_getStatistics() {
        Mockito.when(metadataService.getImpressionCount(1)).thenReturn(1);
        Mockito.when(contentService.getViewsCount(1)).thenReturn(1);
        Mockito.when(contentService.getMetadataId(1)).thenReturn(1L);

        StatisticsResponseDto dto = streamingService.getStatistics("1");

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(1, dto.getImpression());
        Assertions.assertEquals(1, dto.getViews());
    }
}