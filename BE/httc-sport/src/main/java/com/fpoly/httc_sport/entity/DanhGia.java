package com.fpoly.httc_sport.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DanhGia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	Integer mocSao;
	String nhanXet;
	
	@ManyToOne @JoinColumn(name = "maSan")
	San san;
	
	@ManyToOne @JoinColumn(name = "username")
	User user;
}
