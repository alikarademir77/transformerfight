package com.aequilibrium.transformerscoring.model;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;

import com.aequilibrium.transformerscoring.model.entity.TechnicalSpec;
import com.aequilibrium.transformerscoring.model.entity.Transformer;
import com.aequilibrium.transformerscoring.model.rest.TransformerType;
import com.aequilibrium.transformerscoring.service.TransformerFightService.FightResult;
import com.aequilibrium.transformerscoring.service.TransformerFightService.OneOnOneFight;

public class OverallRuleTest {

	@Test
	public void testAutobotWon() {
		OverallRule rule = new OverallRule();
		Transformer autobot = new Transformer();
		TechnicalSpec autobotSpec = TechnicalSpec.builder()
				.strength(7)
				.intelligence(8)
				.speed(4)
				.endurance(5)
				.firepower(6)
				.build();
		TechnicalSpec decepticonSpec = TechnicalSpec.builder()
				.strength(7)
				.intelligence(8)
				.speed(3)
				.endurance(5)
				.firepower(4)
				.build();
		
		Transformer decepticon = new Transformer();
		
		autobot.setTechnicalSpec(autobotSpec);
		autobot.setTransformerType(TransformerType.Autobot);
		
		decepticon.setTechnicalSpec(decepticonSpec);
		decepticon.setTransformerType(TransformerType.Decepticon);
		
		OneOnOneFight fight = new OneOnOneFight(autobot, decepticon);
		Optional<FightResult> result = rule.apply(fight);
		
		assertTrue(result.isPresent());
		assertTrue(result.get().getWinner().get().getTransformerType()==TransformerType.Autobot);
	}

	@Test
	public void testDecepticonWon() {
		OverallRule rule = new OverallRule();
		Transformer autobot = new Transformer();
		TechnicalSpec autobotSpec = TechnicalSpec.builder()
				.strength(7)
				.intelligence(8)
				.speed(2)
				.endurance(5)
				.firepower(6)
				.build();
		TechnicalSpec decepticonSpec = TechnicalSpec.builder()
				.strength(7)
				.intelligence(8)
				.speed(3)
				.endurance(8)
				.firepower(4)
				.build();

		Transformer decepticon = new Transformer();
		
		autobot.setTechnicalSpec(autobotSpec);
		autobot.setTransformerType(TransformerType.Autobot);
		
		decepticon.setTechnicalSpec(decepticonSpec);
		decepticon.setTransformerType(TransformerType.Decepticon);
		
		OneOnOneFight fight = new OneOnOneFight(autobot, decepticon);
		Optional<FightResult> result = rule.apply(fight);
		
		assertTrue(result.isPresent());
		assertTrue(result.get().getWinner().get().getTransformerType()==TransformerType.Decepticon);
	}

	@Test
	public void testRuleNotApplied() {
		OverallRule rule = new OverallRule();
		Transformer autobot = new Transformer();
		TechnicalSpec autobotSpec = TechnicalSpec.builder()
				.strength(7)
				.intelligence(8)
				.speed(2)
				.endurance(5)
				.firepower(6)
				.build();
		TechnicalSpec decepticonSpec = TechnicalSpec.builder()
				.strength(7)
				.intelligence(8)
				.speed(3)
				.endurance(5)
				.firepower(5)
				.build();

		Transformer decepticon = new Transformer();
		
		autobot.setTechnicalSpec(autobotSpec);
		autobot.setTransformerType(TransformerType.Autobot);
		
		decepticon.setTechnicalSpec(decepticonSpec);
		decepticon.setTransformerType(TransformerType.Decepticon);
		
		OneOnOneFight fight = new OneOnOneFight(autobot, decepticon);
		Optional<FightResult> result = rule.apply(fight);
		
		assertFalse(result.isPresent());

	}

}
