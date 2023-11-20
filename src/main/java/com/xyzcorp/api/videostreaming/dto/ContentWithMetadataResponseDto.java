package com.xyzcorp.api.videostreaming.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ContentWithMetadataResponseDto {

    private String title;
    private String synopsis;
    private String director;
    private String caste;
    private int yearOfRelease;
    private String genre;
    private String runningTime;
    private String source;

}
