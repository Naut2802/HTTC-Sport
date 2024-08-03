package com.fpoly.httc_sport.entity;

import com.fpoly.httc_sport.utils.Enum.VipEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Vip {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	@Column(unique = true, nullable = false)
	@Enumerated(EnumType.STRING)
	VipEnum level;
	@Column(nullable = false)
	int min;
	@Column(nullable = false)
	int max;
	@Column(nullable = false)
	Float discountRate;
	
	@OneToMany(mappedBy = "vip", fetch = FetchType.EAGER)
	List<User> users = new ArrayList<>();
}
