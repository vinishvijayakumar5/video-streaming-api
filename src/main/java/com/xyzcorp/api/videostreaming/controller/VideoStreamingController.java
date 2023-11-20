package com.xyzcorp.api.videostreaming.controller;

import com.xyzcorp.api.videostreaming.dto.ContentResponseDto;
import com.xyzcorp.api.videostreaming.dto.ContentWithMetadataResponseDto;
import com.xyzcorp.api.videostreaming.dto.GenericResponseDto;
import com.xyzcorp.api.videostreaming.dto.MetadataResponseDto;
import com.xyzcorp.api.videostreaming.dto.StatisticsResponseDto;
import com.xyzcorp.api.videostreaming.service.StreamingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/private/v1/video-stream")
public class VideoStreamingController {

    private StreamingService streamingService;

    @Autowired
    public VideoStreamingController(StreamingService streamingService) {
        this.streamingService = streamingService;
    }

    @GetMapping("/load/{contentId}")
    @Operation(summary = "Load a video")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "When data found.",
                    content = { @Content(schema = @Schema(implementation = GenericResponseDto.class)) }),
            @ApiResponse(responseCode = "404", description = "When data not found.",
                    content = { @Content(schema = @Schema(implementation = GenericResponseDto.class)) }) })
    public ContentWithMetadataResponseDto load(@PathVariable String contentId) {
        return streamingService.loadContent(contentId);
    }

    @GetMapping("/play/{contentId}")
    @Operation(summary = "Load a video")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "When data found.",
                    content = { @Content(schema = @Schema(implementation = GenericResponseDto.class)) }),
            @ApiResponse(responseCode = "404", description = "When data not found.",
                    content = { @Content(schema = @Schema(implementation = GenericResponseDto.class)) }) })
    public ContentResponseDto play(@PathVariable String contentId) {
        return streamingService.getContentToPlay(contentId);
    }

    @GetMapping()
    @Operation(summary = "List all video's metadata")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "When data found.",
                    content = { @Content(schema = @Schema(implementation = GenericResponseDto.class)) }),
            @ApiResponse(responseCode = "404", description = "When data not found.",
                    content = { @Content(schema = @Schema(implementation = GenericResponseDto.class)) }) })
    public List<MetadataResponseDto> getMetadata() {
        return streamingService.getMetadata();
    }

    @GetMapping("/statistics/{contentId}")
    @Operation(summary = "Get statistics of a video")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "When data found.",
                    content = { @Content(schema = @Schema(implementation = GenericResponseDto.class)) }),
            @ApiResponse(responseCode = "404", description = "When data not found.",
                    content = { @Content(schema = @Schema(implementation = GenericResponseDto.class)) }) })
    public StatisticsResponseDto getStatistics(@PathVariable String contentId) {
        return streamingService.getStatistics(contentId);
    }
}
