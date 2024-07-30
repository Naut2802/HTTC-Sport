package com.fpoly.httc_sport.repository.spec;

import com.fpoly.httc_sport.entity.Pitch;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PitchSpecification {
	public static Specification<Pitch> hasEnabled(boolean isEnabled) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("isEnabled"));
	}
	
	public static Specification<Pitch> hasRateIn(Iterable<Integer> rates) {
		return (root, query, criteriaBuilder) -> {
			CriteriaBuilder.In<Integer> inClause = criteriaBuilder.in(root.get("reviews").get("rate"));
			for (Integer rate : rates) {
				inClause.value(rate);
			}
			return inClause;
		};
	}
	
	public static Specification<Pitch> hasAddress(String district, String city) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (district != null) {
				predicates.add(criteriaBuilder.equal(root.get("address").get("district"), district));
			}
			if (city != null) {
				predicates.add(criteriaBuilder.equal(root.get("address").get("city"), city));
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}
	
	public static Specification<Pitch> hasPriceBetween(int price1, int price2) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("price"), price1, price2);
	}
	
	public static Specification<Pitch> hasPitchNameContaining(String pitchName) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("pitchName"), "%" + pitchName + "%");
	}
	
	public static Specification<Pitch> hasType(String type) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("type"), type);
	}
}
