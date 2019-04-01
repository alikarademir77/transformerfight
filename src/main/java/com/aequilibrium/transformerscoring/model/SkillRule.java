package com.aequilibrium.transformerscoring.model;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.aequilibrium.transformerscoring.service.TransformerFightService.FightResult;
import com.aequilibrium.transformerscoring.service.TransformerFightService.OneOnOneFight;

@Component
public class SkillRule implements Function<OneOnOneFight, Optional<FightResult>> {

	@Override
	public Optional<FightResult> apply(OneOnOneFight f) {
		FightResult result = null;
		if(f.getAutobotTransformer().isPresent() && 
		   f.getDecepticonTransformer().isPresent() && 
	       f.getAutobotTransformer().get().getTechnicalSpec().getSkill() >= (f.getDecepticonTransformer().get().getTechnicalSpec().getSkill() + 3)) {
			result = new FightResult(
					null, 
					f.getAutobotTransformer().get(), 
					null, 
					f.getDecepticonTransformer().get());
		}else if(f.getAutobotTransformer().isPresent() && 
				 f.getDecepticonTransformer().isPresent() && 
				 f.getDecepticonTransformer().get().getTechnicalSpec().getSkill() >= (f.getAutobotTransformer().get().getTechnicalSpec().getSkill() + 3)) {
			result = new FightResult(
					null, 
					f.getDecepticonTransformer().get(), 
					f.getAutobotTransformer().get(),
					null);					
		}
		return Optional.ofNullable(result);		
	}
}
