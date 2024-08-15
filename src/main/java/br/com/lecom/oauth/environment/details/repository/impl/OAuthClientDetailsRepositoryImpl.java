package br.com.lecom.oauth.environment.details.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import br.com.lecom.oauth.environment.details.controller.filter.OAuthClientDetailsFilter;
import br.com.lecom.oauth.environment.details.model.OAuthClientDetails;
import br.com.lecom.oauth.environment.details.repository.OAuthClientDetailsRepositoryQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class OAuthClientDetailsRepositoryImpl implements OAuthClientDetailsRepositoryQuery {

	private static final String PERCENT = "%";
	private static final String ID = "id";
	private static final String ENVIRONMENTS = "environments";
	private static final String CLIENT_ID = "clientId";
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<OAuthClientDetails> filter(final long environmentId, final OAuthClientDetailsFilter filter,
			final Pageable pageable) {

		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<OAuthClientDetails> criteriaQuery = criteriaBuilder.createQuery(OAuthClientDetails.class);
		final Root<OAuthClientDetails> root = criteriaQuery.from(OAuthClientDetails.class);
		criteriaQuery.multiselect(root.get(ID), root.get(CLIENT_ID), root.get(ENVIRONMENTS));
		criteriaQuery.where(this.createRestrictions(environmentId, filter, criteriaBuilder, root));
		criteriaQuery.orderBy(this.createOrder(criteriaBuilder, root, pageable));

		final TypedQuery<OAuthClientDetails> query = entityManager.createQuery(criteriaQuery);
		this.addPageRestrictions(query, pageable);
		return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> total(environmentId, filter));
	}

	private Predicate[] createRestrictions(final long environmentId, final OAuthClientDetailsFilter filter,
			final CriteriaBuilder criteriaBuilder, final Root<OAuthClientDetails> root) {
		final List<Predicate> predicates = new ArrayList<>();

		predicates.add(criteriaBuilder.equal(root.get(ENVIRONMENTS).get(ID), environmentId));

		if (Objects.nonNull(filter.getClientId())) {
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(CLIENT_ID)),
					criteriaBuilder.lower(criteriaBuilder.literal(PERCENT + filter.getClientId() + PERCENT))));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private void addPageRestrictions(TypedQuery<OAuthClientDetails> query, Pageable pageable) {
		query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());
	}

	private Long total(final long environmentId, final OAuthClientDetailsFilter filter) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		final Root<OAuthClientDetails> root = criteria.from(OAuthClientDetails.class);
		criteria.where(createRestrictions(environmentId, filter, builder, root));
		criteria.select(builder.count(root));
		return entityManager.createQuery(criteria).getSingleResult();
	}

	private List<Order> createOrder(final CriteriaBuilder criteriaBuilder, final Root<OAuthClientDetails> root,
			final Pageable pageable) {
		final List<Order> orders = new ArrayList<>();

		if (pageable.getSort().isUnsorted()) {
			orders.add(criteriaBuilder.asc(root.get(CLIENT_ID)));
		} else {
			pageable.getSort().get().forEach(order -> {
				if (order.isAscending()) {
					orders.add(criteriaBuilder.asc(root.get(order.getProperty())));
				} else if (order.isDescending()) {
					orders.add(criteriaBuilder.desc(root.get(order.getProperty())));
				}
			});
		}
		return orders;
	}
}
