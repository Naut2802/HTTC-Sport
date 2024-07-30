package com.fpoly.httc_sport.entity;

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
	@Column(unique = true, nullable = false)
	String username;
	@Column(nullable = false)
	String password;
	@Column(unique = true, nullable = false)
	String email;
	String firstName;
	String lastName;
	String phoneNumber;
	Boolean isEnabled;
	
	@ManyToOne @JoinColumn(name = "vip")
	Vip vip;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	Set<RentInfo> rentInfos;
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	Set<Review> reviews;
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	Set<Bill> bills;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	Set<RefreshTokenWhiteList> tokens;

	@ManyToMany(fetch = FetchType.EAGER)
	Set<Role> roles;
}
