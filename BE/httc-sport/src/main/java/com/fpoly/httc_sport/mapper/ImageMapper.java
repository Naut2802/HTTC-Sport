package com.fpoly.httc_sport.mapper;

import com.fpoly.httc_sport.dto.response.ImageResponse;
import com.fpoly.httc_sport.entity.Image;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ImageMapper {
	Image toImage(ImageResponse request);
	
	ImageResponse toImageResponse(Image image);
}