package me.study.foostudy.board.domain;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;

public interface PostRepository extends ReactiveCrudRepository<Post, String> {

	Flux<Post> findByIdNotNull(PageRequest pageRequest);
}
