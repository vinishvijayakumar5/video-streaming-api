package com.xyzcorp.api.videostreaming;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {VideoStreamingApiApplication.class})
@ActiveProfiles("test")
class VideoStreamingApiApplicationTests {


}
