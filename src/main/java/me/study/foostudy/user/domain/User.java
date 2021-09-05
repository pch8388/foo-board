package me.study.foostudy.user.domain;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Document("user")
public class User implements UserDetails {

	@Id
	private String id;

	private String username;
	private String password;
	private Set<Role> roles;

	public User(String username, String password) {
		new User(username, password, Role.ROLE_USER);
	}

	private User(String username, String password, Role... roles) {
		this.username = username;
		this.password = password;
		this.roles = Set.of(roles);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
