package com.aequilibrium.transformerscoring.model;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;

import com.aequilibrium.transformerscoring.model.entity.Transformer;
import com.aequilibrium.transformerscoring.model.rest.TransformerType;
import com.aequilibrium.transformerscoring.service.TransformerFightService;
import com.aequilibrium.transformerscoring.service.TransformerFightService.FightResult;
import com.aequilibrium.transformerscoring.service.TransformerFightService.OneOnOneFight;

public class NameRuleTest {

	@Test
	public void testAutobotWon() {
		NameRule nameRule = new NameRule();
		Transformer autobot = new Transformer();
		Transformer decepticon = new Transformer();
		
		autobot.setName(TransformerFightService.specialNames.get(0));
		autobot.setTransformerType(TransformerType.Autobot);
		decepticon.setTransformerType(TransformerType.Decepticon);
		
		OneOnOneFight fight = new OneOnOneFight(autobot, decepticon);
		Optional<FightResult> result = nameRule.apply(fight);
		
		assertTrue(result.isPresent());
		assertTrue(result.get().getWinner().get().getTransformerType()==TransformerType.Autobot);

	}

	@Test
	public void testDecepticonWon() {
		NameRule nameRule = new NameRule();
		Transformer autobot = new Transformer();
		Transformer decepticon = new Transformer();
		
		decepticon.setName(TransformerFightService.specialNames.get(0));
		autobot.setTransformerType(TransformerType.Autobot);
		decepticon.setTransformerType(TransformerType.Decepticon);
		
		OneOnOneFight fight = new OneOnOneFight(autobot, decepticon);
		Optional<FightResult> result = nameRule.apply(fight);
		
		assertTrue(result.isPresent());
		assertTrue(result.get().getWinner().get().getTransformerType()==TransformerType.Decepticon);

	}

	@Test
	public void testRuleNotApplied() {
		NameRule nameRule = new NameRule();
		Transformer autobot = new Transformer();
		Transformer decepticon = new Transformer();
		
		decepticon.setName("XYZ");
		autobot.setTransformerType(TransformerType.Autobot);
		decepticon.setTransformerType(TransformerType.Decepticon);
		
		OneOnOneFight fight = new OneOnOneFight(autobot, decepticon);
		Optional<FightResult> result = nameRule.apply(fight);
		
		assertFalse(result.isPresent());

	}
	
}
