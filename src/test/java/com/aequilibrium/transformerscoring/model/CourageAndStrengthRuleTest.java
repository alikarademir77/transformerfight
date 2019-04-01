package com.aequilibrium.transformerscoring.model;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;

import com.aequilibrium.transformerscoring.model.entity.TechnicalSpec;
import com.aequilibrium.transformerscoring.model.entity.Transformer;
import com.aequilibrium.transformerscoring.model.rest.TransformerType;
import com.aequilibrium.transformerscoring.service.TransformerFightService.FightResult;
import com.aequilibrium.transformerscoring.service.TransformerFightService.OneOnOneFight;

public class CourageAndStrengthRuleTest {

	@Test
	public void testAutobotWon() {
		CourageAndStrengthRule rule = new CourageAndStrengthRule();
		Transformer autobot = new Transformer();
		TechnicalSpec autobotSpec = TechnicalSpec.builder()
				.courage(7)
				.strength(8)
				.build();
		TechnicalSpec decepticonSpec = TechnicalSpec.builder()
				.courage(2)
				.strength(5)
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
		CourageAndStrengthRule rule = new CourageAndStrengthRule();
		Transformer autobot = new Transformer();
		TechnicalSpec autobotSpec = TechnicalSpec.builder()
				.courage(2)
				.strength(5)
				.build();
		TechnicalSpec decepticonSpec = TechnicalSpec.builder()
				.courage(7)
				.strength(8)
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
		CourageAndStrengthRule rule = new CourageAndStrengthRule();
		Transformer autobot = new Transformer();
		TechnicalSpec autobotSpec = TechnicalSpec.builder()
				.courage(5)
				.strength(5)
				.build();
		TechnicalSpec decepticonSpec = TechnicalSpec.builder()
				.courage(7)
				.strength(8)
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
