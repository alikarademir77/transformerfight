package com.aequilibrium.transformerscoring.model.rest;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


//TODO: Add ApiModel and ApiModelProperty
@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper=false)
public class FightResponse {
	private final long numberOfBattles;
	private final TransformerType winningTeam;
	private final List<Long> survivorsFromLosingTeam;
}
