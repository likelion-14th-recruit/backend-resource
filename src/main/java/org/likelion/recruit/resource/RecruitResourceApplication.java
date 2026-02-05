package org.likelion.recruit.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RecruitResourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecruitResourceApplication.class, args);
	}

}
