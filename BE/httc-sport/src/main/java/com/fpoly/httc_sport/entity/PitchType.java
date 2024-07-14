package com.fpoly.httc_sport.entity;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PitchType {
	@Id
	Integer typeId;
	String typeName;
	
	@OneToMany(mappedBy = "pitchType", fetch = FetchType.EAGER)
	Set<Pitch> listPitch;
}
