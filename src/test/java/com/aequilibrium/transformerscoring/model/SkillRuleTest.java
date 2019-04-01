package com.aequilibrium.transformerscoring.model;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;

import com.aequilibrium.transformerscoring.model.entity.TechnicalSpec;
import com.aequilibrium.transformerscoring.model.entity.Transformer;
import com.aequilibrium.transformerscoring.model.rest.TransformerType;
import com.aequilibrium.transformerscoring.service.TransformerFightService;
import com.aequilibrium.transformerscoring.service.TransformerFightService.FightResult;
import com.aequilibrium.transformerscoring.service.TransformerFightService.OneOnOneFight;

public class SkillRuleTest {

	@Test
	public void testAutobotWon() {
		SkillRule skillRule = new SkillRule();
		Transformer autobot = new Transformer();
		TechnicalSpec autobotSpec = TechnicalSpec.builder()
				.skill(7)
				.build();
		TechnicalSpec decepticonSpec = TechnicalSpec.builder()
				.skill(3)
				.build();

		Transformer decepticon = new Transformer();
		
		autobot.setTechnicalSpec(autobotSpec);
		autobot.setTransformerType(TransformerType.Autobot);
		
		decepticon.setTechnicalSpec(decepticonSpec);
		decepticon.setTransformerType(TransformerType.Decepticon);
		
		OneOnOneFight fight = new OneOnOneFight(autobot, decepticon);
		Optional<FightResult> result = skillRule.apply(fight);
		
		assertTrue(result.isPresent());
		assertTrue(result.get().getWinner().get().getTransformerType()==TransformerType.Autobot);
	}

	@Test
	public void testDecepticonWon() {
		SkillRule skillRule = new SkillRule();
		Transformer autobot = new Transformer();
		TechnicalSpec autobotSpec = TechnicalSpec.builder()
				.skill(4)
				.build();
		TechnicalSpec decepticonSpec = TechnicalSpec.builder()
				.skill(8)
				.build();

		Transformer decepticon = new Transformer();
		
		autobot.setTechnicalSpec(autobotSpec);
		autobot.setTransformerType(TransformerType.Autobot);
		
		decepticon.setTechnicalSpec(decepticonSpec);
		decepticon.setTransformerType(TransformerType.Decepticon);
		
		OneOnOneFight fight = new OneOnOneFight(autobot, decepticon);
		Optional<FightResult> result = skillRule.apply(fight);
		
		assertTrue(result.isPresent());
		assertTrue(result.get().getWinner().get().getTransformerType()==TransformerType.Decepticon);
	}

	@Test
	public void testRuleNotApplied() {
		SkillRule skillRule = new SkillRule();
		Transformer autobot = new Transformer();
		TechnicalSpec autobotSpec = TechnicalSpec.builder()
				.skill(4)
				.build();
		TechnicalSpec decepticonSpec = TechnicalSpec.builder()
				.skill(6)
				.build();

		Transformer decepticon = new Transformer();
		
		autobot.setTechnicalSpec(autobotSpec);
		autobot.setTransformerType(TransformerType.Autobot);
		
		decepticon.setTechnicalSpec(decepticonSpec);
		decepticon.setTransformerType(TransformerType.Decepticon);
		
		OneOnOneFight fight = new OneOnOneFight(autobot, decepticon);
		Optional<FightResult> result = skillRule.apply(fight);
		
		assertFalse(result.isPresent());

	}

}
