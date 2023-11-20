package com.xyzcorp.api.videostreaming;

import com.xyzcorp.api.videostreaming.dto.VideoPublishRequestDto;
import com.xyzcorp.api.videostreaming.service.PublishingService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {VideoStreamingApiApplication.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SearchApiIntegrationTests {

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
		publishingService.publish(VideoPublishRequestDto.builder()
				.title("CORP AB")
				.director("Director")
				.caste("Caste")
				.synopsis("Synopsis")
				.genre("Genre")
				.runningTime("1h")
				.yearOfRelease(2024)
				.content("content")
				.build());
	}

	@Test
	public void test_search_givenParameters_thenReturnSuccess() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/private/v1/video-stream/search?title=CORP&yearOfRelease=2023")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].title", is("CORP")));

	}

	@Test
	public void test_search_givenNoParameters_thenReturnSuccess() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/private/v1/video-stream/search")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));

	}

}
