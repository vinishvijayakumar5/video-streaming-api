package com.xyzcorp.api.videostreaming.controller;

import com.xyzcorp.api.videostreaming.dto.GenericResponseDto;
import com.xyzcorp.api.videostreaming.dto.MetadataResponseDto;
import com.xyzcorp.api.videostreaming.dto.MetadataSearchRequestDto;
import com.xyzcorp.api.videostreaming.service.MetadataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/private/v1/video-stream/search")
public class VideoSearchController {

    private MetadataService metadataService;

    @Autowired
    public VideoSearchController(MetadataService metadataService) {
        this.metadataService = metadataService;
    }

    @GetMapping()
    @Operation(summary = "Search videos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "When data found.",
                    content = { @Content(schema = @Schema(implementation = GenericResponseDto.class)) }),
            @ApiResponse(responseCode = "404", description = "When data not found.",
                    content = { @Content(schema = @Schema(implementation = GenericResponseDto.class)) }) })
    public List<MetadataResponseDto> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String director,
            @RequestParam(required = false) String caste,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer yearOfRelease) {
        return metadataService.search(MetadataSearchRequestDto.builder()
                        .title(title)
                        .caste(caste)
                        .director(director)
                        .genre(genre)
                        .yearOfRelease(yearOfRelease)
                .build());
    }

}
