package com.aequilibrium.transformerscoring.model.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.aequilibrium.transformerscoring.model.rest.TransformerType;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper=false, exclude={"technicalSpec"})
@Entity
@Table(name="transformer",
		uniqueConstraints=@UniqueConstraint(columnNames={"name"}))

public class Transformer {

    @Id
    @GeneratedValue
    @Column(name="id")
	private Long id;
    
    @NotNull
    @Column(name="transformer_type")
    @Enumerated(EnumType.STRING)
    private TransformerType transformerType;
    
    @NotBlank
    @Column(name="name")
    private String name;
    
    @NotNull
    @Embedded
    @Valid
    private TechnicalSpec technicalSpec;
	
	public int getOverallRating() {
		return technicalSpec.getStrength() + technicalSpec.getIntelligence() + 
		 technicalSpec.getSpeed() + technicalSpec.getEndurance() + technicalSpec.getFirepower();
	}
}
