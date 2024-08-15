package br.com.lecom.oauth.environment.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.lecom.oauth.environment.user.controller.filter.UserFilter;
import br.com.lecom.oauth.environment.user.model.User;

public interface UserRepositoryQuery {
	public Page<User> filter(final long clientId, final UserFilter filter, final Pageable pageable);
}
