package com.aequilibrium.transformerscoring.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.aequilibrium.transformerscoring.model.entity.TechnicalSpec;
import com.aequilibrium.transformerscoring.model.entity.Transformer;
import com.aequilibrium.transformerscoring.model.rest.FightResponse;
import com.aequilibrium.transformerscoring.model.rest.TransformerType;
import com.aequilibrium.transformerscoring.repository.TransformerRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TransformerFightServiceTest {

	@Autowired
	TransformerFightService fightService;
	
	@MockBean
	TransformerRepository transformerRepository;

	@Test
	public void scoreFighting() {
		List<Long> transformerIds = new ArrayList<>();
		transformerIds.add(1L);
		transformerIds.add(2L);
		transformerIds.add(3L);
		
		Transformer t1 = getTransformer1();
		Transformer t2 = getTransformer2();
		Transformer t3 = getTransformer3();
		
		when(transformerRepository.findById(1L)).thenReturn(Optional.of(t1));
		when(transformerRepository.findById(2L)).thenReturn(Optional.of(t2));
		when(transformerRepository.findById(3L)).thenReturn(Optional.of(t3));
		
		FightResponse response = fightService.scoreFighting(transformerIds);
		
		assertTrue(response.getNumberOfBattles()==1);
		assertTrue(response.getWinningTeam()==TransformerType.Decepticon);
		assertTrue(response.getSurvivorsFromLosingTeam().size()==1);
		assertTrue(response.getSurvivorsFromLosingTeam().contains(3L));
		
		

	}

	private Transformer getTransformer1() {
		TechnicalSpec techSpec = TechnicalSpec.builder()
		.strength(8)
		.intelligence(9)
		.speed(2)
		.endurance(6)
		.rank(7)
		.courage(5)
		.firepower(6)
		.skill(10)
		.build();

		Transformer mockTransformer = new Transformer();
		mockTransformer.setId(1L);
		mockTransformer.setName("Soundwave");
		mockTransformer.setTransformerType(TransformerType.Decepticon);
		mockTransformer.setTechnicalSpec(techSpec);
		
		return mockTransformer;
	}

	private Transformer getTransformer2() {
		TechnicalSpec techSpec = TechnicalSpec.builder()
		.strength(6)
		.intelligence(6)
		.speed(7)
		.endurance(9)
		.rank(5)
		.courage(2)
		.firepower(9)
		.skill(7)
		.build();

		Transformer mockTransformer = new Transformer();
		mockTransformer.setId(2L);
		mockTransformer.setName("Bluestreak");
		mockTransformer.setTransformerType(TransformerType.Autobot);
		mockTransformer.setTechnicalSpec(techSpec);
		
		return mockTransformer;
	}
	
	private Transformer getTransformer3() {
		TechnicalSpec techSpec = TechnicalSpec.builder()
		.strength(4)
		.intelligence(4)
		.speed(4)
		.endurance(4)
		.rank(4)
		.courage(4)
		.firepower(4)
		.skill(4)
		.build();

		Transformer mockTransformer = new Transformer();
		mockTransformer.setId(3L);
		mockTransformer.setName("Hubcap");
		mockTransformer.setTransformerType(TransformerType.Autobot);
		mockTransformer.setTechnicalSpec(techSpec);
		
		return mockTransformer;
	}
	
}
