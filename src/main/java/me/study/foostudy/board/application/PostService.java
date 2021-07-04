package me.study.foostudy.board.application;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.study.foostudy.board.domain.Post;
import me.study.foostudy.board.domain.PostRepository;
import me.study.foostudy.board.dto.RequestPostDto;
import me.study.foostudy.board.dto.ResponsePostDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	public Mono<ResponsePostDto> saveNewPost(RequestPostDto postDto) {
		return this.postRepository.save(postDto.toEntity())
			.map(ResponsePostDto::convertFromEntity);
	}

	public Flux<ResponsePostDto> findAll() {
		return this.postRepository.findAll()
			.map(ResponsePostDto::convertFromEntity);
	}
}
