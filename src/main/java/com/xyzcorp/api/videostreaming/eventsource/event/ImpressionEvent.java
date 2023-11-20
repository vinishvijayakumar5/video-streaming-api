package com.xyzcorp.api.videostreaming.eventsource.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ImpressionEvent {
    private long metaId;
    private long contentId;
}
