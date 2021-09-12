package me.study.foostudy.board.application;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.study.foostudy.board.domain.PostRepository;
import me.study.foostudy.board.dto.RequestPostDto;
import me.study.foostudy.board.dto.RequestUpdatePostDto;
import me.study.foostudy.board.dto.ResponsePostDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	public Mono<ResponsePostDto> saveNewPost(RequestPostDto postDto, String userId) {
		return this.postRepository.save(postDto.toEntityWithWriter(userId))
			.map(ResponsePostDto::convertFromEntity);
	}

	public Flux<ResponsePostDto> findAll(PageRequest pageRequest) {
		return this.postRepository.findByIdNotNullOrderByCreatedDateDesc(pageRequest)
			.map(ResponsePostDto::convertFromEntity);
	}

	public Mono<ResponsePostDto> updatePost(String postId, RequestUpdatePostDto updatePostDto, String userId) {
		return this.postRepository.findById(postId)
			.flatMap(post -> {
				post.updateContent(userId, updatePostDto.getUpdateContent());
				return this.postRepository.save(post);
			})
			.map(ResponsePostDto::convertFromEntity)
			.switchIfEmpty(Mono.error(new IllegalArgumentException("잘못된 post id")));
	}

	public Mono<Void> deletePost(String postId, String userId) {
		return this.postRepository.findById(postId)
			.flatMap(post -> {
				post.validatePermission(userId);
				return this.postRepository.delete(post);
			});
	}

	public Mono<ResponsePostDto> findPostsById(String postId) {
		return this.postRepository.findById(postId)
			.map(ResponsePostDto::convertFromEntity)
			.switchIfEmpty(Mono.error(new IllegalArgumentException("잘못된 post id")));
	}
}
