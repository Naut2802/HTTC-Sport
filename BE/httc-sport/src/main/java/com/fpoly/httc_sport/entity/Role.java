package com.fpoly.httc_sport.entity;

import com.fpoly.httc_sport.utils.Enum.RoleEnum;
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
public class Role extends AbstractIdEntity {
	@Enumerated(EnumType.STRING)
	@Column(unique = true)
	RoleEnum roleName;
	String description;
}
