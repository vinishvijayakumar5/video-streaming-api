package com.xyzcorp.api.videostreaming;

import com.xyzcorp.api.videostreaming.dto.ContentResponseDto;
import com.xyzcorp.api.videostreaming.dto.ContentWithMetadataResponseDto;
import com.xyzcorp.api.videostreaming.dto.MetadataResponseDto;
import com.xyzcorp.api.videostreaming.dto.StatisticsResponseDto;
import com.xyzcorp.api.videostreaming.dto.VideoPublishRequestDto;
import com.xyzcorp.api.videostreaming.entity.Content;
import com.xyzcorp.api.videostreaming.entity.Metadata;

import java.time.LocalDateTime;

public class AbstractTestData {

    protected Content getContent() {
        return Content.builder()
                .createdOn(LocalDateTime.now())
                .source("SRC")
                .deleted(0)
                .id(1)
                .metadata(getMetadata())
                .build();
    }

    protected Metadata getMetadata() {
        return Metadata.builder()
                .id(1)
                .title("Europe")
                .caste("c1, c3, c5")
                .genre("GEN")
                .runningTime("3h")
                .yearOfRelease(2022)
                .director("KPM")
                .synopsis("SYS")
                .deleted(0)
                .createdOn(LocalDateTime.now())
                .build();
    }

    protected ContentWithMetadataResponseDto getContentWithMetadataResponseDto() {
        return ContentWithMetadataResponseDto.builder()
                .title("Europe")
                .caste("c1, c3, c5")
                .genre("GEN")
                .runningTime("3h")
                .yearOfRelease(2022)
                .director("KPM")
                .synopsis("SYS")
                .source("SRC")
                .build();
    }

    protected VideoPublishRequestDto getVideoPublishRequestDto() {
        return VideoPublishRequestDto.builder()
                .title("Europe")
                .caste("c1, c3, c5")
                .genre("GEN")
                .runningTime("3h")
                .yearOfRelease(2022)
                .director("KPM")
                .synopsis("SYS")
                .content("SRC")
                .build();
    }

    protected ContentResponseDto getContentResponseDto() {
        return ContentResponseDto.builder()
                .source("SRC")
                .build();
    }

    protected StatisticsResponseDto getStatisticsResponseDto() {
        return StatisticsResponseDto.builder()
                .impression(1)
                .views(1)
                .build();
    }

    protected MetadataResponseDto getMetadataResponseDto() {
        return MetadataResponseDto.builder()
                .title("Europe")
                .caste("c1, c3, c5")
                .genre("GEN")
                .runningTime("3h")
                .yearOfRelease(2022)
                .director("KPM")
                .synopsis("SYS")
                .build();
    }
}
