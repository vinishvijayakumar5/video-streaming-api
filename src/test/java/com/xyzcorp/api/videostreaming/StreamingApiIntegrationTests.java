package com.xyzcorp.api.videostreaming;

import com.xyzcorp.api.videostreaming.dto.VideoPublishRequestDto;
import com.xyzcorp.api.videostreaming.service.PublishingService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StreamingApiIntegrationTests {

	@Autowired
	private MockMvc mvc;
	@Autowired
	private PublishingService publishingService;

	@BeforeAll
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
	@Order(1)
	public void test_load_givenContentId_thenReturnSuccess() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/api/private/v1/video-stream/load/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title", is("CORP")));

	}

	@Test
	@Order(2)
	public void test_load_givenInvalidContentId_thenReturnError() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/api/private/v1/video-stream/load/111")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.code", is("E104")));
	}

	@Test
	@Order(3)
	public void test_play_givenContentId_thenReturnSuccess() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/api/private/v1/video-stream/play/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.source", is("content")));

	}

	@Test
	@Order(4)
	public void test_play_givenInvalidContentId_thenReturnError() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/api/private/v1/video-stream/play/111")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.code", is("E104")));
	}

	@Test
	@Order(5)
	public void test_listAllVideos_returnSuccess() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/api/private/v1/video-stream")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].title", is("CORP")));

	}

	@Test
	@Order(6)
	public void test_statistics_givenContentId_thenReturnSuccess() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/api/private/v1/video-stream/statistics/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.impression", greaterThanOrEqualTo(1)))
				.andExpect(jsonPath("$.views", greaterThanOrEqualTo(1)));

	}


}
