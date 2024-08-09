package com.fpoly.httc_sport.mapper;

import com.fpoly.httc_sport.dto.response.TransactionResponse;
import com.fpoly.httc_sport.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
	@Mapping(source = "wallet.id", target = "walletId")
	@Mapping(source = "rentInfo.pitch.id", target = "rentInfo.pitchId")
	@Mapping(source = "rentInfo.pitch.pitchName", target = "rentInfo.pitchName")
	TransactionResponse toTransactionResponse(Transaction transaction);
}
