package com.aequilibrium.transformerscoring.model;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.aequilibrium.transformerscoring.service.TransformerFightService.FightResult;
import com.aequilibrium.transformerscoring.service.TransformerFightService.OneOnOneFight;
import com.aequilibrium.transformerscoring.service.TransformerFightService;

@Component
public class NameRule implements Function<OneOnOneFight, Optional<FightResult>> {

	@Override
	public Optional<FightResult> apply(OneOnOneFight f) {
		FightResult result = null;
		if(f.getAutobotTransformer().isPresent() && TransformerFightService.specialNames.contains(f.getAutobotTransformer().get().getName())) {
			result = new FightResult(
					null, 
					f.getAutobotTransformer().get(), 
					null, 
					f.getDecepticonTransformer().isPresent()?f.getDecepticonTransformer().get():null);
		}else if(f.getDecepticonTransformer().isPresent() && TransformerFightService.specialNames.contains(f.getDecepticonTransformer().get().getName())) {
			result = new FightResult(
					null, 
					f.getDecepticonTransformer().get(), 
					f.getAutobotTransformer().isPresent()?f.getAutobotTransformer().get():null,
					null);					
		}
		return Optional.ofNullable(result);
	}
}
