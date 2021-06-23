package me.study.foostudy.board.api;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.study.foostudy.board.application.PostService;
import me.study.foostudy.board.domain.Post;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostApi {

	private final PostService postService;

	@PostMapping
	public Mono<ResponseEntity<?>> createNewPost(@RequestBody Mono<Post> post) {
		return post.flatMap(this.postService::saveNewPost)
			.map(savedItem ->
				ResponseEntity.created(URI.create("/posts/" + savedItem.getId()))
					.body(savedItem));
	}
}
