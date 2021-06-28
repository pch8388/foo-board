package me.study.foostudy.board.application;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.study.foostudy.board.domain.Post;
import me.study.foostudy.board.domain.PostRepository;
import me.study.foostudy.board.dto.RequestPostDto;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	public Mono<Post> saveNewPost(RequestPostDto postDto) {
		return this.postRepository.save(postDto.toEntity());
	}
}
