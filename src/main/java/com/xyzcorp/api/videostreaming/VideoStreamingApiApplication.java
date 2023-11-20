package com.xyzcorp.api.videostreaming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
@EnableAsync
public class VideoStreamingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoStreamingApiApplication.class, args);
	}

}
