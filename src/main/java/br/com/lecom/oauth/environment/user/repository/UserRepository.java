package br.com.lecom.oauth.environment.user.repository;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lecom.oauth.environment.user.model.User;
import br.com.lecom.oauth.environment.user.model.User.UserKey;

public interface UserRepository extends JpaRepository<User, UserKey>, UserRepositoryQuery {

	@Cacheable(value = "oauthCache", key = "'UserRepository.findByIdUsernameIgnoreCase('.concat(#username).concat(')')")
	Optional<User> findByIdUsernameIgnoreCase(String username);

	@Cacheable(value = "oauthCache", key = "'UserRepository.findByIdUsernameIgnoreCaseAndIdEnvironmentsId('.concat(#username).concat('-').concat(#environmentsId).concat(')')")
	Optional<User> findByIdUsernameIgnoreCaseAndIdEnvironmentsId(String username, long environmentsId);

	@Cacheable(value = "oauthCache", key = "'UserRepository.findByEmail('.concat(#email).concat(')')")
	Optional<User> findByEmail(String email);

	@Cacheable(value = "oauthCache", key = "'UserRepository.findByEmailAndIdEnvironmentsId('.concat(#email).concat('-').concat(#environmentsId).concat(')')")
	Optional<User> findByEmailAndIdEnvironmentsId(final String email, final long environmentsId);

	@Cacheable(value = "oauthCache", key = "'UserRepository.findByIdUsernameAndIdEnvironmentsId('.concat(#username).concat('-').concat(#environmentsId).concat(')')")
	Optional<User> findByIdUsernameAndIdEnvironmentsId(final String username, final long environmentsId);

}
