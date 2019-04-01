package com.aequilibrium.transformerscoring.model.rest;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@ApiModel
public class TechnicalSpecDto {
	
	@ApiModelProperty(value = "strength of a transformer", allowableValues = "range[1, 10]")
	@Min(value = 1)
	@Max(value = 10)
	private Integer strength;
	
	@ApiModelProperty(value = "intelligence of a transformer", allowableValues = "range[1, 10]")
	@Min(value = 1)
	@Max(value = 10)
	private Integer intelligence;
	
	@ApiModelProperty(value = "speed of a transformer", allowableValues = "range[1, 10]")
	@Min(value = 1)
	@Max(value = 10)
	private Integer speed;
	
	@ApiModelProperty(value = "endurance of a transformer", allowableValues = "range[1, 10]")
	@Min(value = 1)
	@Max(value = 10)
	private Integer endurance;
	
	@ApiModelProperty(value = "rank of a transformer", allowableValues = "range[1, 10]")
	@Min(value = 1)
	@Max(value = 10)
	private Integer rank;
	
	@ApiModelProperty(value = "courage of a transformer", allowableValues = "range[1, 10]")
	@Min(value = 1)
	@Max(value = 10)
	private Integer courage;
	
	@ApiModelProperty(value = "firepower of a transformer", allowableValues = "range[1, 10]")
	@Min(value = 1)
	@Max(value = 10)
	private Integer firepower;
	
	@ApiModelProperty(value = "skill of a transformer", allowableValues = "range[1, 10]")
	@Min(value = 1)
	@Max(value = 10)
	private Integer skill;	
	
}
