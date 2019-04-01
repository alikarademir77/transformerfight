package com.aequilibrium.transformerscoring.model;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.aequilibrium.transformerscoring.service.TransformerFightService.FightResult;
import com.aequilibrium.transformerscoring.service.TransformerFightService.OneOnOneFight;

@Component
public class CourageAndStrengthRule implements Function<OneOnOneFight, Optional<FightResult>> {

	@Override
	public Optional<FightResult> apply(OneOnOneFight f) {
		FightResult result = null;
		if(f.getAutobotTransformer().isPresent() && 
		   f.getDecepticonTransformer().isPresent() && 
	      (f.getAutobotTransformer().get().getTechnicalSpec().getCourage() >= (f.getDecepticonTransformer().get().getTechnicalSpec().getCourage()+4) && 
		   f.getAutobotTransformer().get().getTechnicalSpec().getStrength() >= (f.getDecepticonTransformer().get().getTechnicalSpec().getStrength()+3))) {
			result = new FightResult(
					null, 
					f.getAutobotTransformer().get(), 
					null, 
					f.getDecepticonTransformer().get());
		}else if(f.getAutobotTransformer().isPresent() && 
					   f.getDecepticonTransformer().isPresent() && 
				      (f.getDecepticonTransformer().get().getTechnicalSpec().getCourage() >= (f.getAutobotTransformer().get().getTechnicalSpec().getCourage()+4) && 
					   f.getDecepticonTransformer().get().getTechnicalSpec().getStrength() >= (f.getAutobotTransformer().get().getTechnicalSpec().getStrength()+3))) {
			result = new FightResult(
					null, 
					f.getDecepticonTransformer().get(), 
					f.getAutobotTransformer().get(),
					null);					
		}
		return Optional.ofNullable(result);
	}
}
