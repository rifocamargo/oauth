package br.com.lecom.oauth.environment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lecom.oauth.environment.model.Environments;

public interface EnvironmentsRepository extends JpaRepository<Environments, Long>, EnvironmentsRepositoryQuery {
	Optional<Environments> findByHostname(String hostname);
}
