package me.study.foostudy.user.application;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.study.foostudy.user.domain.UserRepository;
import me.study.foostudy.user.dto.RequestUserDto;
import me.study.foostudy.user.dto.ResponseUserDto;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public Mono<ResponseUserDto> register(RequestUserDto requestUserDto) {
		return this.userRepository.save(requestUserDto.createUser())
			.map(ResponseUserDto::convertFromEntity);
	}
}
