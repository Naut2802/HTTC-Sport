package com.fpoly.httc_sport.mapper;

import com.fpoly.httc_sport.dto.request.PitchRequest;
import com.fpoly.httc_sport.dto.response.PitchDetailsResponse;
import com.fpoly.httc_sport.dto.response.PitchResponse;
import com.fpoly.httc_sport.entity.Address;
import com.fpoly.httc_sport.entity.Pitch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PitchMapper {
	@Mapping(target = "imageSet", ignore = true)
	Pitch toPitch(PitchRequest request);
	Address toAddress(PitchRequest request);
	
	@Mapping(source = "address.street", target = "street")
	@Mapping(source = "address.ward", target = "ward")
	@Mapping(source = "address.district", target = "district")
	@Mapping(source = "address.city", target = "city")
	PitchResponse toPitchResponse(Pitch pitch);
	
	@Mapping(source = "address.street", target = "street")
	@Mapping(source = "address.ward", target = "ward")
	@Mapping(source = "address.district", target = "district")
	@Mapping(source = "address.city", target = "city")
	PitchDetailsResponse toPitchDetailsResponse(Pitch pitch);
	
	@Mapping(target = "imageSet", ignore = true)
	@Mapping(target = "address.street", source = "street")
	@Mapping(target = "address.ward", source = "ward")
	@Mapping(target = "address.district", source = "district")
	@Mapping(target = "address.city", source = "city")
	void updatePitch(@MappingTarget Pitch pitch, PitchRequest request);
}