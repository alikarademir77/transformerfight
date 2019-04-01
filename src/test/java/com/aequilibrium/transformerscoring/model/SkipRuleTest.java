package com.aequilibrium.transformerscoring.model;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;

import com.aequilibrium.transformerscoring.model.entity.TechnicalSpec;
import com.aequilibrium.transformerscoring.model.entity.Transformer;
import com.aequilibrium.transformerscoring.model.rest.TransformerType;
import com.aequilibrium.transformerscoring.service.TransformerFightService.FightResult;
import com.aequilibrium.transformerscoring.service.TransformerFightService.OneOnOneFight;

public class SkipRuleTest {

	@Test
	public void testAutobotSkipped() {
		SkipRule skipRule = new SkipRule();
		Transformer autobot = new Transformer();
		TechnicalSpec autobotSpec = TechnicalSpec.builder()
				.skill(7)
				.build();

		Transformer decepticon = null;
		
		autobot.setTechnicalSpec(autobotSpec);
		autobot.setTransformerType(TransformerType.Autobot);
		
		
		OneOnOneFight fight = new OneOnOneFight(autobot, decepticon);
		Optional<FightResult> result = skipRule.apply(fight);
		
		assertTrue(result.isPresent());
		assertTrue(result.get().getSkipped().get().getTransformerType()==TransformerType.Autobot);
	}

	@Test
	public void testDecepticonWon() {
		SkipRule skipRule = new SkipRule();
		Transformer decepticon = new Transformer();
		TechnicalSpec decepticonSpec = TechnicalSpec.builder()
				.skill(7)
				.build();

		Transformer autobot = null;
		
		decepticon.setTechnicalSpec(decepticonSpec);
		decepticon.setTransformerType(TransformerType.Decepticon);
		
		
		OneOnOneFight fight = new OneOnOneFight(autobot, decepticon);
		Optional<FightResult> result = skipRule.apply(fight);
		
		assertTrue(result.isPresent());
		assertTrue(result.get().getSkipped().get().getTransformerType()==TransformerType.Decepticon);
	}

	@Test
	public void testWontApply() {
		SkipRule skipRule = new SkipRule();
		Transformer decepticon = new Transformer();
		TechnicalSpec decepticonSpec = TechnicalSpec.builder()
				.skill(7)
				.build();

		Transformer autobot = new Transformer();
		TechnicalSpec autobotSpec = TechnicalSpec.builder()
				.skill(7)
				.build();
		
		decepticon.setTechnicalSpec(decepticonSpec);
		decepticon.setTransformerType(TransformerType.Decepticon);

		autobot.setTechnicalSpec(autobotSpec);
		autobot.setTransformerType(TransformerType.Autobot);

		
		OneOnOneFight fight = new OneOnOneFight(autobot, decepticon);
		Optional<FightResult> result = skipRule.apply(fight);
		
		assertFalse(result.isPresent());
	}
	
}
