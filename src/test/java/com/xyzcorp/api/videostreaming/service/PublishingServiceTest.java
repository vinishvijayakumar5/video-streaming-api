package com.xyzcorp.api.videostreaming.service;

import com.xyzcorp.api.videostreaming.AbstractTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class PublishingServiceTest extends AbstractTestData {

    @Mock
    private MetadataService metadataService;
    @Mock
    private ContentService contentService;
    @InjectMocks
    private PublishingService publishingService;

    @Test
    void test_publish() {
        Mockito.when(metadataService.persist(Mockito.any())).thenReturn(getMetadata());
        Mockito.when(contentService.persist(Mockito.any(), Mockito.any())).thenReturn(1L);

        ResponseEntity response = publishingService.publish(getVideoPublishRequestDto());

        Mockito.verify(metadataService, Mockito.times(1)).persist(Mockito.any());
        Mockito.verify(contentService, Mockito.times(1)).persist(Mockito.any(), Mockito.any());

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
    }
}