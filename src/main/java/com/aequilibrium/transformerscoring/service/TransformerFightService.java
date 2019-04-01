package com.aequilibrium.transformerscoring.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aequilibrium.transformerscoring.model.CourageAndStrengthRule;
import com.aequilibrium.transformerscoring.model.OverallRule;
import com.aequilibrium.transformerscoring.model.SkillRule;
import com.aequilibrium.transformerscoring.model.NameRule;
import com.aequilibrium.transformerscoring.model.SkipRule;
import com.aequilibrium.transformerscoring.model.entity.Transformer;
import com.aequilibrium.transformerscoring.model.rest.FightResponse;
import com.aequilibrium.transformerscoring.model.rest.TransformerType;
import com.aequilibrium.transformerscoring.repository.TransformerRepository;

@Service
public class TransformerFightService {
	
	public static List<String> specialNames = Arrays.asList("Optimus Prime", "Predaking");

	@Autowired
	private NameRule nameRule;

	@Autowired
	private SkipRule skipRule;

	@Autowired	
	private CourageAndStrengthRule courageAndStrengthRule;

	@Autowired	
	private SkillRule skillRule;

	@Autowired		
	private OverallRule overallRule;
	
	private Comparator<Transformer> rankComparator = (h1, h2) -> h1.getTechnicalSpec().getRank() - h2.getTechnicalSpec().getRank();

	@Autowired
	private TransformerRepository transformerRepository;

	public FightResponse scoreFighting(List<Long> transformerIds) {

		final List<Transformer> transformers = validateIdsAndRetrieveTransformers(transformerIds);
		
		//3 Extract autobots in rank order
		List<Transformer> autobots= transformers
				.stream()
				.filter((t)->TransformerType.Autobot == t.getTransformerType())
				.sorted(rankComparator.reversed())
				.collect(Collectors.toList());

		//4 Extract decepticons in rank order
		List<Transformer> decepticons = transformers
				.stream()
				.filter((t)->TransformerType.Decepticon == t.getTransformerType())
				.sorted(rankComparator.reversed())
				.collect(Collectors.toList());

		//5 Arrange teams to face fighters one on one
		List<OneOnOneFight> oneOnOneFightList = buildOneOnOneList(autobots, decepticons);

		List<Function<OneOnOneFight, Optional<FightResult>>> rulesList = Arrays.asList(nameRule, skipRule, courageAndStrengthRule, skillRule, overallRule); 
		FightResponse fightResponse = null;
		if(isGameEndedWithAllCompetitorsDestroyed(oneOnOneFightList)) {
			//If it is special case of destroying all competitors and ending game immediately
			fightResponse = new FightResponse(0, null, new ArrayList<>());
		}else {
			//Execute fights and have fight results ready	
			List<FightResult> fightResults = oneOnOneFightList
				.stream()
				.map(f->f.scoreFight(rulesList))
				.collect(Collectors.toList());

			//Calculate  number of battles
			long numOfBattles = fightResults
								.stream()
								.filter( f -> f.getSkipped().isPresent() == false)
								.count();
			
			//Calculate  winning team
			TransformerType winningTeam = findWinningTeam(fightResults);
			
			//Calculate  survivors from losing team
			List<Long> survivorsFromLosingTeam = new ArrayList<>();
			if(winningTeam!=null) {
				survivorsFromLosingTeam = fightResults
						.stream()
						.filter(f->f.getSkipped().isPresent())
						.filter(f->winningTeam != f.getSkipped().get().getTransformerType())
						.map(f->f.getSkipped().get().getId())
						.collect(Collectors.toList());
			}
			
			fightResponse = new FightResponse(numOfBattles, winningTeam, survivorsFromLosingTeam);
		}
		
		return fightResponse;
	}

	private List<Transformer> validateIdsAndRetrieveTransformers(List<Long> transformerIds) {
		if(transformerIds==null || transformerIds.size()==0) {
			throw new IllegalArgumentException("Please provide a non empty transformer id list");
		}
		
		final List<Transformer> transformers = new ArrayList<>();
		transformerIds.stream().forEach(transformerId->{
			if(transformerId==null) {
				throw new IllegalArgumentException("Please ensure none of passed in transfor ids are empty");
			}
			Optional<Transformer> retrievedOpt = transformerRepository.findById(transformerId);
			if(!retrievedOpt.isPresent()) {
				throw new IllegalArgumentException(String.format("Please ensure passed in transfor id %d exists", transformerId));				
			}else {
				transformers.add(retrievedOpt.get());
			}
		});
		return transformers;
	};
	
	private TransformerType findWinningTeam(List<FightResult> fightResults) {

		long autobotCount = fightResults
			.stream()
			.filter(f->f.autobotDestroyedOpt.isPresent())
			.count();
		
		long decepticonCount = fightResults
				.stream()
				.filter(f->f.decepticonDestroyedOpt.isPresent())
				.count();
		
		TransformerType winner = null;

		if(autobotCount<decepticonCount) {
			winner = TransformerType.Autobot;
		}else if(autobotCount>decepticonCount) {
			winner = TransformerType.Decepticon;
		}
		
		return winner;
	}

	private List<OneOnOneFight> buildOneOnOneList(List<Transformer> autobots, List<Transformer> decepticons){
		
	 List<OneOnOneFight> fightsList = new ArrayList<>();
	 int maxSize = Math.max(autobots.size(), decepticons.size());
	 for(int i=0;i<maxSize;i++) {
		 fightsList.add(new OneOnOneFight(
				 i<autobots.size() ? autobots.get(i) : null, 
				 i<decepticons.size() ? decepticons.get(i) : null));
	 }
	 
	 return fightsList;
		
	}
	
	private boolean isGameEndedWithAllCompetitorsDestroyed(List<OneOnOneFight> oneOneOneFightList) {
		Optional<OneOnOneFight> match = oneOneOneFightList.stream().filter(f->{
			return  f.getAutobotTransformer().isPresent() && 
					f.getDecepticonTransformer().isPresent() && 
					specialNames.contains(f.getAutobotTransformer().get().getName()) && 
					specialNames.contains(f.getDecepticonTransformer().get().getName());
		}).findAny();
		
		return match.isPresent(); 
	}
	public static class OneOnOneFight{
		
		private final Optional<Transformer> autobotTransformerOpt;
		private final Optional<Transformer> decepticonTransformerOpt;


		public OneOnOneFight(Transformer autobotTransformer, Transformer decepticonTransformer) {
			super();
			this.autobotTransformerOpt = Optional.ofNullable(autobotTransformer);
			this.decepticonTransformerOpt = Optional.ofNullable(decepticonTransformer);
		}

		public Optional<Transformer> getAutobotTransformer() {
			return autobotTransformerOpt;
		}

		public Optional<Transformer> getDecepticonTransformer() {
			return decepticonTransformerOpt;
		}
		
		public FightResult scoreFight(List<Function<OneOnOneFight, Optional<FightResult>>> rulesList) {
			
			Optional<FightResult> matchedRuleOpt = rulesList
					.stream()
					.filter(f->{
						return f.apply(this).isPresent();
						})
					.map(f->f.apply(this).get())
					.findAny();
			
			if(matchedRuleOpt.isPresent()) {
				return matchedRuleOpt.get();
			}else {
				//Since it is still tie, destroy both opponents
				return new FightResult(null, null, autobotTransformerOpt.get(), decepticonTransformerOpt.get());
			}	
		}
	}
	
	public static class FightResult{
		private final Optional<Transformer> skippedOpt;
		private final Optional<Transformer> winnerOpt;
		private final Optional<Transformer> autobotDestroyedOpt;
		private final Optional<Transformer> decepticonDestroyedOpt;
		//TODO: Change to use Builder to construct
		public FightResult(Transformer skipped, Transformer winner, Transformer autobotDestroyed, Transformer decepticonDestroyed) {
			this.skippedOpt = Optional.ofNullable(skipped);
			this.winnerOpt = Optional.ofNullable(winner);
			this.autobotDestroyedOpt = Optional.ofNullable(autobotDestroyed);
			this.decepticonDestroyedOpt = Optional.ofNullable(decepticonDestroyed);
			
		}

		public  Optional<Transformer> getSkipped() {
			return skippedOpt;
		}

		public  Optional<Transformer> getWinner() {
			return winnerOpt;
		}

		public  Optional<Transformer> getAutobotDestroyed() {
			return autobotDestroyedOpt;
		}

		public  Optional<Transformer> getDecepticonDestroyed() {
			return decepticonDestroyedOpt;
		}
		
	}
	
}
