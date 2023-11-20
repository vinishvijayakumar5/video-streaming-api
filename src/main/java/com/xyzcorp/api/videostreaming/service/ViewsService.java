package com.xyzcorp.api.videostreaming.service;

import com.xyzcorp.api.videostreaming.entity.Content;
import com.xyzcorp.api.videostreaming.entity.Views;
import com.xyzcorp.api.videostreaming.repository.ViewsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ViewsService {

    private ViewsRepository viewsRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteViews(long contentId) {
        viewsRepository.delete(contentId);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void addView(Content content) {
        viewsRepository.save(Views.builder()
                .content(content)
                .createdOn(LocalDateTime.now())
                .build());
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public int getViewsCount(long contentId) {
       return viewsRepository.countByContentId(contentId);
    }
}
