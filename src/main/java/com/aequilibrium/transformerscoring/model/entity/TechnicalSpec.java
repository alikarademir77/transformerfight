package com.aequilibrium.transformerscoring.model.entity;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(callSuper=false)
@Embeddable
public class TechnicalSpec {
	
	@NotNull 
	@Min(value=1)
	@Max(value=10)
	private Integer strength;
	
	@NotNull
	@Min(value=1)
	@Max(value=10)
	private Integer intelligence;
	
	@NotNull
	@Min(value=1)
	@Max(value=10)
	private Integer speed;

	@NotNull
	@Min(value=1)
	@Max(value=10)
	private Integer endurance;

	@NotNull
	@Min(value=1)
	@Max(value=10)
	private Integer rank;

	@NotNull
	@Min(value=1)
	@Max(value=10)
	private Integer courage;

	@NotNull
	@Min(value=1)
	@Max(value=10)
	private Integer firepower;

	@NotNull
	@Min(value=1)
	@Max(value=10)
	private Integer skill;

}
