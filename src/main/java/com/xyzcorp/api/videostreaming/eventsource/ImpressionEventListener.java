package com.xyzcorp.api.videostreaming.eventsource;

import com.xyzcorp.api.videostreaming.eventsource.event.ImpressionEvent;
import com.xyzcorp.api.videostreaming.service.MetadataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import static java.lang.String.format;

@Component
@Slf4j
public class ImpressionEventListener {

    @Autowired
    private MetadataService metadataService;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void listen(ImpressionEvent impressionEvent) {
        log.info(format("Impression event has been captured for content [%s]", impressionEvent.getContentId()));
        metadataService.addImpression(impressionEvent.getMetaId());
    }
}
