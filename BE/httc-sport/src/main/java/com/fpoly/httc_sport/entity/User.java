package com.fpoly.httc_sport.entity;

import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	String id;
	@Column(unique = true)
	String username;
	String password;
	@Column(unique = true)
	String email;
	String firstName;
	String lastName;
	String phoneNumber;
	Boolean status;
	
	@ManyToOne @JoinColumn(name = "vip")
	Vip vip;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	Set<ThongTinDatSan> listThongTinDatSan;
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	Set<DanhGia> listDanhGia;
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	Set<HoaDon> listHoaDon;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	Set<RefreshTokenWhiteList> tokens;

	@ManyToMany(fetch = FetchType.EAGER)
	Set<Role> roles;
}
