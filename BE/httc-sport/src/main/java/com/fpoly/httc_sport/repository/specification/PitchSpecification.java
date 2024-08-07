package com.fpoly.httc_sport.repository.specification;

import com.fpoly.httc_sport.entity.Pitch;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PitchSpecification {
	public static Specification<Pitch> hasEnabled() {
		return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("isEnabled"));
	}
	
	public static Specification<Pitch> hasRateIn(int rate1, int rate2) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("rate"), rate1, rate2);
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
}
