package com.xyzcorp.api.videostreaming.service;

import com.xyzcorp.api.videostreaming.AbstractTestData;
import com.xyzcorp.api.videostreaming.repository.ViewsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ViewsServiceTest extends AbstractTestData {

    @Mock
    private ViewsRepository viewsRepository;
    @InjectMocks
    private ViewsService viewsService;

    @Test
    void test_deleteViews() {
        Mockito.when(viewsRepository.delete(Mockito.anyLong())).thenReturn(1);

        viewsService.deleteViews(1);

        Mockito.verify(viewsRepository, Mockito.times(1)).delete(1);
    }

    @Test
    void test_addView() {
        Mockito.when(viewsRepository.save(Mockito.any())).thenReturn(Mockito.any());

        viewsService.addView(getContent());

        Mockito.verify(viewsRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void test_getViewsCount() {
        Mockito.when(viewsRepository.countByContentId(Mockito.anyLong())).thenReturn(1);

        int viewsCount = viewsService.getViewsCount(1);

        Assertions.assertEquals(1, viewsCount);
    }

}