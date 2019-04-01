package com.aequilibrium.transformerscoring.model;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.aequilibrium.transformerscoring.service.TransformerFightService.FightResult;
import com.aequilibrium.transformerscoring.service.TransformerFightService.OneOnOneFight;

@Component
public class SkipRule implements Function<OneOnOneFight, Optional<FightResult>> {

	@Override
	public Optional<FightResult> apply(OneOnOneFight f) {
		FightResult result = null;
		if(f.getAutobotTransformer().isPresent() && !f.getDecepticonTransformer().isPresent()) {
			result = new FightResult(
					f.getAutobotTransformer().get(), 
					null, 
					null, 
					null);
		}else if(f.getDecepticonTransformer().isPresent() && !f.getAutobotTransformer().isPresent()) {
			result = new FightResult(
					f.getDecepticonTransformer().get(), 
					null, 
					null,
					null);					
		}
		return Optional.ofNullable(result);		
	}

}
