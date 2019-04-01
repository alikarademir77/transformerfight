package com.aequilibrium.transformerscoring.model;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.aequilibrium.transformerscoring.service.TransformerFightService.FightResult;
import com.aequilibrium.transformerscoring.service.TransformerFightService.OneOnOneFight;

@Component
public class OverallRule implements Function<OneOnOneFight, Optional<FightResult>> {

	@Override
	public Optional<FightResult> apply(OneOnOneFight f) {
		FightResult result = null;
		if(f.getAutobotTransformer().isPresent() && 
		   f.getDecepticonTransformer().isPresent() && 
	       f.getAutobotTransformer().get().getOverallRating() > f.getDecepticonTransformer().get().getOverallRating()) {
			result = new FightResult(
					null, 
					f.getAutobotTransformer().get(), 
					null, 
					f.getDecepticonTransformer().get());
		}else if(f.getAutobotTransformer().isPresent() && 
				 f.getDecepticonTransformer().isPresent() && 
				 f.getDecepticonTransformer().get().getOverallRating() > f.getAutobotTransformer().get().getOverallRating()) {
			result = new FightResult(
					null, 
					f.getDecepticonTransformer().get(),
					f.getAutobotTransformer().get(),
					null);					
		}
		return Optional.ofNullable(result);		
	}

}
