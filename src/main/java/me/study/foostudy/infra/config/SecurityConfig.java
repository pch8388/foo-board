package me.study.foostudy.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.SecurityWebFilterChain;

import me.study.foostudy.user.domain.User;
import me.study.foostudy.user.domain.UserRepository;
import me.study.foostudy.user.exception.NotFoundUserException;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
public class SecurityConfig {

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		return http
			.authorizeExchange()
			.anyExchange().authenticated()
			.and()
				.formLogin()
			.and()
				.httpBasic()
			.and()
				.csrf().disable()
			.build();
	}

	@Bean
	public ReactiveUserDetailsService userDetailsService(UserRepository userRepository) {
		return username -> userRepository.findByUsername(username)
			.map(User::toUserDetails)
			.switchIfEmpty(Mono.error(new NotFoundUserException(username)));
	}
}