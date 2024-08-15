package br.com.lecom.oauth.environment.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import br.com.lecom.oauth.environment.controller.filter.EnvironmentsFilter;
import br.com.lecom.oauth.environment.model.Environments;
import br.com.lecom.oauth.environment.repository.EnvironmentsRepositoryQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class EnvironmentsRepositoryImpl implements EnvironmentsRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Environments> filter(final EnvironmentsFilter filter, final Pageable pageable) {

		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Environments> criteriaQuery = criteriaBuilder.createQuery(Environments.class);
		final Root<Environments> root = criteriaQuery.from(Environments.class);
		criteriaQuery.where(this.createRestrictions(filter, criteriaBuilder, root));
		criteriaQuery.orderBy(this.createOrder(criteriaBuilder, root, pageable));

		final TypedQuery<Environments> query = entityManager.createQuery(criteriaQuery);
		this.addPageRestrictions(query, pageable);
		return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> total(filter));
	}

	private Predicate[] createRestrictions(final EnvironmentsFilter filter, final CriteriaBuilder criteriaBuilder,
			final Root<Environments> root) {
		final List<Predicate> predicates = new ArrayList<>();

		if (Objects.nonNull(filter.getHostname())) {
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("hostname")),
					criteriaBuilder.lower(criteriaBuilder.literal("%" + filter.getHostname() + "%"))));
		}
		if (Objects.nonNull(filter.getStartDate())) {
			predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), filter.getStartDate()));
		}
		if (Objects.nonNull(filter.getEndDate())) {
			predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), filter.getEndDate()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private void addPageRestrictions(TypedQuery<Environments> query, Pageable pageable) {
		query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());
	}

	private Long total(final EnvironmentsFilter filter) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		final Root<Environments> root = criteria.from(Environments.class);
		criteria.where(createRestrictions(filter, builder, root));
		criteria.select(builder.count(root));
		return entityManager.createQuery(criteria).getSingleResult();
	}

	private List<Order> createOrder(final CriteriaBuilder criteriaBuilder, final Root<Environments> root,
			final Pageable pageable) {
		final List<Order> orders = new ArrayList<>();

		if (pageable.getSort().isUnsorted()) {
			orders.add(criteriaBuilder.asc(root.get("hostname")));
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
