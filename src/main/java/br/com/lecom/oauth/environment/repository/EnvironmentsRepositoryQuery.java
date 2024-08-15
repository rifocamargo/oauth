package br.com.lecom.oauth.environment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.lecom.oauth.environment.controller.filter.EnvironmentsFilter;
import br.com.lecom.oauth.environment.model.Environments;

public interface EnvironmentsRepositoryQuery {
	public Page<Environments> filter(final EnvironmentsFilter filter, final Pageable pageable);
}
