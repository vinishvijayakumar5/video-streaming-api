package com.xyzcorp.api.videostreaming;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PublishApiIntegrationTests {

	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void test_publish_givenPublishContent_thenReturnSuccess() throws Exception {

		mvc.perform(MockMvcRequestBuilders.post("/api/private/v1/video-stream/publish")
						.content(data())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.contentId", greaterThanOrEqualTo(1)));
	}

	@Test
	public void test_publish_givenEmptyBody_thenReturnError() throws Exception {

		mvc.perform(MockMvcRequestBuilders.post("/api/private/v1/video-stream/publish")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void test_publish_givenInvalidPublishContent_thenReturnError() throws Exception {

		mvc.perform(MockMvcRequestBuilders.post("/api/private/v1/video-stream/publish")
						.content(invalidData())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.code", is("E100")));
	}

	private String data() {
		return """
				{
				  "title": "ABC",
				  "synopsis": "ABC 003",
				  "director": "Director",
				  "caste": "Caste1, Caste2",
				  "yearOfRelease": 2023,
				  "genre": "Live show",
				  "runningTime": "1h",
				  "content": "https://cdb.xyzcorp.com/private/web/06956befd1078017df6d302861a095dd/ABC.mp3"
				}
				""";
	}

	private String invalidData() {
		return """
				{
				  "title": "ABC",
				  "director": "Director",
				  "caste": "Caste1, Caste2",
				  "yearOfRelease": 2023,
				  "genre": "Live show",
				  "runningTime": "1h",
				  "content": "https://cdb.xyzcorp.com/private/web/06956befd1078017df6d302861a095dd/ABC.mp3"
				}
				""";
	}

}
