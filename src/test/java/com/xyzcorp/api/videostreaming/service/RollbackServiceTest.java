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
class RollbackServiceTest extends AbstractTestData {

    @Mock
    private MetadataService metadataService;
    @Mock
    private ContentService contentService;
    @InjectMocks
    private RollbackService rollbackService;

    @Test
    void test_delete() {
        Mockito.when(contentService.getMetadataId(1)).thenReturn(1L);

        ResponseEntity entity = rollbackService.delete("1");

        Mockito.verify(contentService, Mockito.times(1)).delete(1);
        Mockito.verify(metadataService, Mockito.times(1)).delete(1);

        Assertions.assertNotNull(entity);
        Assertions.assertTrue(entity.getStatusCode().is2xxSuccessful());
    }
}