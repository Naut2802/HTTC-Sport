package com.fpoly.httc_sport.entity;

import java.util.List;
import java.util.Set;

import com.fpoly.httc_sport.event.listener.UserListener;
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
@EntityListeners(UserListener.class)
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
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	Wallet wallet;
	
	@ManyToOne @JoinColumn(name = "vip")
	Vip vip;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	List<RentInfo> rentInfos;
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	List<Review> reviews;
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	List<Bill> bills;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	List<RefreshTokenWhiteList> tokens;

	@ManyToMany(fetch = FetchType.EAGER)
	List<Role> roles;
}
