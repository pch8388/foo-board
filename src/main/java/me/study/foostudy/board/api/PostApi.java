package me.study.foostudy.board.api;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.study.foostudy.board.application.PostService;
import me.study.foostudy.board.dto.RequestPostDto;
import me.study.foostudy.board.dto.RequestUpdatePostDto;
import me.study.foostudy.board.dto.ResponsePostDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostApi {

	private final PostService postService;

	@PostMapping
	public Mono<ResponseEntity<?>> createNewPost(@Valid @RequestBody Mono<RequestPostDto> postDto) {
		return postDto.flatMap(this.postService::saveNewPost)
			.map(savedItem ->
				ResponseEntity.created(URI.create("/posts/" + savedItem.getId()))
					.body(savedItem));
	}

	@GetMapping
	public Flux<ResponsePostDto> listPost() {
		return this.postService.findAll();
	}

	@PatchMapping("/{id}")
	public Mono<ResponseEntity<?>> updatePost(
		@PathVariable("id") String postId,
		@Valid @RequestBody Mono<RequestUpdatePostDto> updatePostDto) {
		return updatePostDto.flatMap(dto -> this.postService.updatePost(postId, dto))
			.map(updatedItem ->
				ResponseEntity.ok().body(updatedItem));
	}
}
