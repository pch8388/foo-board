package me.study.foostudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@SpringBootApplication
@EnableReactiveMongoAuditing
public class FooStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(FooStudyApplication.class, args);
	}

}
