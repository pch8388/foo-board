package me.study.foostudy.board.application;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.study.foostudy.board.domain.Post;
import me.study.foostudy.board.domain.PostRepository;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	public Mono<Post> saveNewPost(Post post) {
		return this.postRepository.save(post);
	}
}
