package com.fpoly.httc_sport.mapper;

import com.fpoly.httc_sport.dto.response.BillResponse;
import com.fpoly.httc_sport.entity.Bill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BillMapper {
	@Mapping(source = "user.id", target = "userId")
	@Mapping(source = "pitch.id", target = "pitchId")
	@Mapping(source = "pitch.pitchName", target = "pitchName")
	@Mapping(source = "paymentMethod.method", target = "paymentMethod")
	BillResponse toBillResponse(Bill bill);
}