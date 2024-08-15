package br.com.lecom.oauth.environment.user.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import br.com.lecom.oauth.environment.user.controller.filter.UserFilter;
import br.com.lecom.oauth.environment.user.model.User;
import br.com.lecom.oauth.environment.user.repository.UserRepositoryQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class UserRepositoryImpl implements UserRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<User> filter(final long clientId, final UserFilter filter, final Pageable pageable) {

		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		final Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.where(this.createRestrictions(clientId, filter, criteriaBuilder, root));
		criteriaQuery.orderBy(this.createOrder(criteriaBuilder, root, pageable));

		final TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
		this.addPageRestrictions(query, pageable);
		return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> total(clientId, filter));
	}

	private Predicate[] createRestrictions(final long clientId, final UserFilter filter,
			final CriteriaBuilder criteriaBuilder, final Root<User> root) {
		final List<Predicate> predicates = new ArrayList<>();

		predicates.add(criteriaBuilder.equal(root.get("id").get("environments").get("id"), clientId));

		if (Objects.nonNull(filter.getAuthority())) {
			predicates.add(criteriaBuilder.like(root.get("authorities"),
					criteriaBuilder.literal("%" + filter.getAuthority() + "%")));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private void addPageRestrictions(TypedQuery<User> query, Pageable pageable) {
		query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());
	}

	private Long total(final long clientId, final UserFilter filter) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		final Root<User> root = criteria.from(User.class);
		criteria.where(createRestrictions(clientId, filter, builder, root));
		criteria.select(builder.count(root));
		return entityManager.createQuery(criteria).getSingleResult();
	}

	private List<Order> createOrder(final CriteriaBuilder criteriaBuilder, final Root<User> root,
			final Pageable pageable) {
		final List<Order> orders = new ArrayList<>();

		if (pageable.getSort().isUnsorted()) {
			orders.add(criteriaBuilder.asc(root.get("id").get("username")));
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
