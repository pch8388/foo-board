package me.study.foostudy.user.domain;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, String> {
	Mono<User> findByUsername(String username);
}
