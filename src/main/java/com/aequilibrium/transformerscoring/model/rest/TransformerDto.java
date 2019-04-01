package com.aequilibrium.transformerscoring.model.rest;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
@EqualsAndHashCode(callSuper=false)
@ApiModel
public class TransformerDto {
	
	@ApiModelProperty(value = "Id", name = "transformerId") 
	private Long transformerId;
	
	@ApiModelProperty(value = "The transformer name", name="name", required=true)
    @NotBlank
	private String name;

	@ApiModelProperty(value = "Transformer type", name="type", allowableValues = "Autobot, Decepticon", required=true)
	@NotNull
	private TransformerType type;

	@ApiModelProperty(value = "Technical specs of the transformer", name="spec")
	@NotNull
	@Valid
	private TechnicalSpecDto spec;
		
}
