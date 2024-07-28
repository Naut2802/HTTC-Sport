package com.fpoly.httc_sport.mapper;

import com.fpoly.httc_sport.dto.request.RentInfoUpdateRequest;
import com.fpoly.httc_sport.dto.request.RentRequest;
import com.fpoly.httc_sport.dto.response.RentInfoResponse;
import com.fpoly.httc_sport.entity.RentInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RentInfoMapper {
	@Mapping(target = "paymentMethod", ignore = true)
	RentInfo toRentInfo(RentRequest request);
	
	@Mapping(source = "pitch.id", target = "pitchId")
	@Mapping(source = "pitch.pitchName", target = "pitchName")
	RentInfoResponse toRentInfoResponse(RentInfo rentInfo);
	
	void updateRentInfo(@MappingTarget RentInfo rentInfo, RentInfoUpdateRequest request);
}