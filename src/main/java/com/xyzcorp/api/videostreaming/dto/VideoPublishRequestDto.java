package com.xyzcorp.api.videostreaming.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
public class VideoPublishRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String synopsis;

    @NotBlank
    private String director;

    @NotBlank
    private String caste;

    @Min(1900)
    private int yearOfRelease;

    @NotBlank
    private String genre;

    @NotBlank
    private String runningTime;

    @NotBlank
    private String content;
}
