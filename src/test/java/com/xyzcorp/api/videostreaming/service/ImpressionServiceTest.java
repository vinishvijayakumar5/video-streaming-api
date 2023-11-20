package com.xyzcorp.api.videostreaming.service;

import com.xyzcorp.api.videostreaming.AbstractTestData;
import com.xyzcorp.api.videostreaming.repository.ImpressionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ImpressionServiceTest extends AbstractTestData {

    @Mock
    private ImpressionRepository impressionRepository;
    @InjectMocks
    private ImpressionService impressionService;

    @Test
    void test_deleteImpressions() {
        Mockito.when(impressionRepository.delete(Mockito.anyLong())).thenReturn(1);

        impressionService.deleteImpressions(1);

        Mockito.verify(impressionRepository, Mockito.times(1)).delete(1);
    }

    @Test
    void test_addImpression() {
        Mockito.when(impressionRepository.save(Mockito.any())).thenReturn(Mockito.any());

        impressionService.addImpression(getMetadata());

        Mockito.verify(impressionRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void test_getImpressionCount() {
        Mockito.when(impressionRepository.countByMetadataId(Mockito.anyLong())).thenReturn(1);

        int impressionCount = impressionService.getImpressionCount(1);

        Assertions.assertEquals(1, impressionCount);
    }
}