package com.fpoly.httc_sport.repository;

import com.fpoly.httc_sport.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
	boolean existsByStreetAndDistrict(String street, String district);
}
