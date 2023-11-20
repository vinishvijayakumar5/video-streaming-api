package com.xyzcorp.api.videostreaming;

import com.xyzcorp.api.videostreaming.dto.VideoPublishRequestDto;
import com.xyzcorp.api.videostreaming.service.PublishingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {VideoStreamingApiApplication.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RollbackApiIntegrationTests {

	@Autowired
	private MockMvc mvc;
	@Autowired
	private PublishingService publishingService;

	@BeforeEach
	public void setup() {
		publishingService.publish(VideoPublishRequestDto.builder()
				.title("CORP")
				.director("Director")
				.caste("Caste")
				.synopsis("Synopsis")
				.genre("Genre")
				.runningTime("1h")
				.yearOfRelease(2023)
				.content("content")
				.build());
	}

	@Test
	public void test_rollback_givenParameters_thenReturnSuccess() throws Exception {
		mvc.perform(MockMvcRequestBuilders.delete("/api/private/v1/video-stream/rollback/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

	}

}
