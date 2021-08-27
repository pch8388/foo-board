package me.study.foostudy.infra.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import me.study.foostudy.user.domain.UserRepository;
import me.study.foostudy.user.exception.NotFoundUserException;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
public class SecurityConfig {

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		return http
			.authorizeExchange(exchanges ->
					exchanges.anyExchange()
						.authenticated()
						.and()
						.httpBasic()
						.and()
						.formLogin())
				.csrf().disable()
			.build();
	}

	@Bean
	public ReactiveUserDetailsService userDetailsService(UserRepository userRepository) {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

		return username -> userRepository.findByUsername(username)
			.map(user -> User.builder()
				.username(user.getUsername())
				.password(encoder.encode(user.getPassword()))
				.authorities(user.getAuthorities())
				.build())
			.switchIfEmpty(Mono.error(new NotFoundUserException(username)));
	}

	@Bean
	public CommandLineRunner userLoader(MongoOperations operations) {
		return args -> {
			operations.dropCollection(User.class);
			operations.save(User.builder().username("test").password("test").authorities(
				List.of(new SimpleGrantedAuthority("ROLE_USER"))).build());
		};
	}
}