package com.xyzcorp.api.videostreaming.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyzcorp.api.videostreaming.AbstractTestData;
import com.xyzcorp.api.videostreaming.dto.ContentResponseDto;
import com.xyzcorp.api.videostreaming.dto.ContentWithMetadataResponseDto;
import com.xyzcorp.api.videostreaming.entity.Content;
import com.xyzcorp.api.videostreaming.exception.DataNotFoundException;
import com.xyzcorp.api.videostreaming.repository.ContentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ContentServiceTest extends AbstractTestData {

    @Mock
    private ContentRepository contentRepository;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;
    @Mock
    private ViewsService viewsService;
    @InjectMocks
    private ContentService contentService;

    @Test
    void test_persist() {
        Mockito.when(contentRepository.save(Mockito.any())).thenReturn(getContent());

        long id = contentService.persist("SRC", getMetadata());

        Assertions.assertEquals(1, id);
    }

    @Test
    void test_delete() {
        Mockito.when(contentRepository.delete(Mockito.anyLong())).thenReturn(1);

        contentService.delete(1);

        Mockito.verify(viewsService, Mockito.times(1)).deleteViews(1);
    }

    @Test
    void test_delete_throwError() {
        Mockito.when(contentRepository.delete(Mockito.anyLong())).thenReturn(0);

        DataNotFoundException dataNotFoundException = assertThrows(DataNotFoundException.class,
                () -> contentService.delete(1));

        assertEquals("Video content not found", dataNotFoundException.getMessage());

        Mockito.verify(contentRepository, Mockito.times(1)).delete(1);
        Mockito.verify(viewsService, Mockito.never()).deleteViews(1);
    }

    @Test
    void test_getMetadataId() {
        Mockito.when(contentRepository.findContentById(Mockito.anyLong())).thenReturn(getContent());

        long metadataId = contentService.getMetadataId(1);

        Assertions.assertEquals(1, metadataId);
    }

    @Test
    void test_loadContent() {
        Mockito.when(contentRepository.findContentById(Mockito.anyLong())).thenReturn(getContent());
        Mockito.when(objectMapper.convertValue(Mockito.any(), Mockito.eq(ContentWithMetadataResponseDto.class)))
                .thenReturn(getContentWithMetadataResponseDto());

        ContentWithMetadataResponseDto dto = contentService.loadContent(1);

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
        Mockito.when(contentRepository.findContentById(Mockito.anyLong())).thenReturn(getContent());

        ContentResponseDto dto = contentService.getContentToPlay(1);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals("SRC", dto.getSource());
    }

    @Test
    void test_getById() {
        Mockito.when(contentRepository.findContentById(Mockito.anyLong())).thenReturn(getContent());

        Content content = contentService.getById(1);

        Assertions.assertNotNull(content);
        Assertions.assertEquals("SRC", content.getSource());
        Assertions.assertEquals("Europe", content.getMetadata().getTitle());
        Assertions.assertEquals("c1, c3, c5", content.getMetadata().getCaste());
        Assertions.assertEquals("GEN", content.getMetadata().getGenre());
        Assertions.assertEquals("3h", content.getMetadata().getRunningTime());
        Assertions.assertEquals("KPM", content.getMetadata().getDirector());
        Assertions.assertEquals("SYS", content.getMetadata().getSynopsis());
        Assertions.assertEquals(2022, content.getMetadata().getYearOfRelease());
    }

    @Test
    void test_getById_whenNoDataFound() {
        Mockito.when(contentRepository.findContentById(Mockito.anyLong())).thenReturn(null);

        DataNotFoundException dataNotFoundException = assertThrows(DataNotFoundException.class,
                () -> contentService.getById(1));

        assertEquals("Video content not found", dataNotFoundException.getMessage());
    }

    @Test
    void test_getViewsCount() {
        Mockito.when(viewsService.getViewsCount(Mockito.anyLong())).thenReturn(1);

        int count = contentService.getViewsCount(1);

        Assertions.assertEquals(1, count);
    }
}