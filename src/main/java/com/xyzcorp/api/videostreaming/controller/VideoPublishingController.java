package com.xyzcorp.api.videostreaming.controller;

import com.xyzcorp.api.videostreaming.dto.GenericResponseDto;
import com.xyzcorp.api.videostreaming.dto.VideoPublishRequestDto;
import com.xyzcorp.api.videostreaming.service.PublishingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/private/v1/video-stream/publish")
public class VideoPublishingController {

    private PublishingService publishingService;

    @Autowired
    public VideoPublishingController(PublishingService publishingService) {
        this.publishingService = publishingService;
    }

    @PostMapping
    @Operation(summary = "Publish a video")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "When the video has been published successfully.",
                    content = { @Content(schema = @Schema(implementation = GenericResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "When the video title already exists.",
                    content = { @Content(schema = @Schema(implementation = GenericResponseDto.class)) }) })
    public ResponseEntity publish(@RequestBody VideoPublishRequestDto request) {
        return publishingService.publish(request);
    }

}
