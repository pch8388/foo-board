package me.study.foostudy.board.api;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import me.study.foostudy.user.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostApi {

	private final PostService postService;

	@PostMapping
	public Mono<ResponseEntity<?>> createNewPosts(
		@Valid @RequestBody Mono<RequestPostDto> postDto, @AuthenticationPrincipal User user) {
		return postDto.flatMap(dto -> this.postService.saveNewPost(dto, user.getId()))
			.map(savedItem ->
				ResponseEntity.created(URI.create("/posts/" + savedItem.getId()))
					.body(savedItem));
	}

	@GetMapping("/{postId}")
	public Mono<ResponseEntity<?>> searchPostsById(@PathVariable String postId) {
		return this.postService.findPostsById(postId)
			.map(ResponseEntity::ok);
	}

	@GetMapping
	public Flux<ResponsePostDto> listPosts() {
		return this.postService.findAll();
	}

	@PatchMapping("/{postId}")
	public Mono<ResponseEntity<?>> updatePosts(
		@PathVariable("postId") String postId,
		@Valid @RequestBody Mono<RequestUpdatePostDto> updatePostDto,
		@AuthenticationPrincipal User user) {
		return updatePostDto.flatMap(dto -> this.postService.updatePost(postId, dto, user.getId()))
			.map(updatedItem ->
				ResponseEntity.ok().body(updatedItem));
	}

	@DeleteMapping("/{postId}")
	public Mono<ResponseEntity<?>> deletePosts(
		@PathVariable("postId") String postId,
		@AuthenticationPrincipal User user) {
		return this.postService.deletePost(postId, user.getId())
			.thenReturn(ResponseEntity.noContent().build());
	}

}
