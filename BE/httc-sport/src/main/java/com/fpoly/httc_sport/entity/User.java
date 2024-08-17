package com.fpoly.httc_sport.entity;

import com.fpoly.httc_sport.event.listener.UserListener;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(UserListener.class)
public class User extends AbstractEntity {
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
	@JoinColumn(nullable = false)
	Wallet wallet;
	
	@ManyToOne
	@JoinColumn(name = "vip", nullable = false)
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
