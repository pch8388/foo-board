package me.study.foostudy.utils;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

public class DatabaseCleanup implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	private final MongoDBContainer mongoDbContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		mongoDbContainer.start();

		TestPropertyValues.of(
			"spring.data.mongodb.uri=mongodb://" +
				mongoDbContainer.getContainerIpAddress() + ":" +
				mongoDbContainer.getMappedPort(27017) + "/test"
		).applyTo(applicationContext);
	}
}
