package com.xyzcorp.api.videostreaming.controller;

import com.xyzcorp.api.videostreaming.dto.GenericResponseDto;
import com.xyzcorp.api.videostreaming.service.RollbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/private/v1/video-stream/rollback")
public class VideoRollbackController {

    private RollbackService rollbackService;

    @Autowired
    public VideoRollbackController(RollbackService rollbackService) {
        this.rollbackService = rollbackService;
    }

    @DeleteMapping("{contentId}")
    @Operation(summary = "Delete a video")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "When the video has been deleted successfully.",
                    content = { @Content(schema = @Schema(implementation = GenericResponseDto.class)) }),
            @ApiResponse(responseCode = "404", description = "When data not found.",
                    content = { @Content(schema = @Schema(implementation = GenericResponseDto.class)) }) })
    public ResponseEntity delete(@PathVariable String contentId) {
        return rollbackService.delete(contentId);
    }

}
