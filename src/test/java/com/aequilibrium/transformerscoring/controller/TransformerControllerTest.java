package com.aequilibrium.transformerscoring.controller;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.aequilibrium.transformerscoring.controllers.TransformerController;
import com.aequilibrium.transformerscoring.model.rest.FightResponse;
import com.aequilibrium.transformerscoring.model.rest.TechnicalSpecDto;
import com.aequilibrium.transformerscoring.model.rest.TransformerDto;
import com.aequilibrium.transformerscoring.model.rest.TransformerType;
import com.aequilibrium.transformerscoring.service.TransformerFightService;
import com.aequilibrium.transformerscoring.service.TransformerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TransformerController.class)
public class TransformerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TransformerService transformerService;
	
	@MockBean
	private TransformerFightService fightService;

	@Test
	public void testRetrieveDetailsForTransformer() throws Exception {

		long transformerId = 6l;
		String jsonResponse = "{'transformerId':6,'name':'Test','type':'Autobot','spec':{'strength':6,'intelligence':6,'speed':6,'endurance':6,'rank':6,'courage':6,'firepower':6,'skill':6}}";

		TransformerDto mockTransformer = buildMockTransformerDto(transformerId);
		
		Mockito.when(
				transformerService.retrieveTransformer(Mockito.anyLong())).thenReturn(mockTransformer);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/battlefield/v1/transformers/6").accept(
				MediaType.APPLICATION_JSON);
		
		mockMvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(jsonResponse));
	}

	@Test
	public void testRetrieveAllTransformers() throws Exception {

		String jsonResponse = "[{'transformerId':6,'name':'Test','type':'Autobot','spec':{'strength':6,'intelligence':6,'speed':6,'endurance':6,'rank':6,'courage':6,'firepower':6,'skill':6}}]";

		long transformerId = 6l;
		TransformerDto mockTransformer = buildMockTransformerDto(transformerId);
		List<TransformerDto> transformers = Arrays.asList(mockTransformer);
	        
		Mockito.when(
				transformerService.listTransformers()).thenReturn(transformers);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/battlefield/v1/transformers").accept(
				MediaType.APPLICATION_JSON);
		
		mockMvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(jsonResponse));
	}
	
	@Test
	public void testCreateTransformer() throws Exception {

		long transformerId = 1l;
		String jsonRequest = null;
		String jsonResponse = "{'transformerId':1,'name':'Test','type':'Autobot','spec':{'strength':6,'intelligence':6,'speed':6,'endurance':6,'rank':6,'courage':6,'firepower':6,'skill':6}}";

		TransformerDto mockTransformer = buildMockTransformerDto(transformerId);
		TransformerDto mockTransformerRequest = buildMockTransformerDto(null);

		ObjectMapper mapper = new ObjectMapper();
      	jsonRequest = mapper.writeValueAsString(mockTransformerRequest); 

		
		Mockito.when(transformerService.createTransformer(Mockito.any(TransformerDto.class))).thenReturn(mockTransformer);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/battlefield/v1/transformers")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(jsonRequest.getBytes());
		
		mockMvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().json(jsonResponse));
	}

	@Test
	public void testUpdateTransformer() throws Exception {

		long transformerId = 1l;
		String jsonRequest = null;
		String jsonResponse = null;
		//String jsonResponse = "{'transformerId':1,'name':'Updated','type':'Autobot','spec':{'strength':6,'intelligence':6,'speed':6,'endurance':6,'rank':6,'courage':6,'firepower':6,'skill':6}}";

		TransformerDto mockTransformer = buildMockTransformerDto(transformerId);
		TransformerDto mockTransformerRequest = buildMockTransformerDto(null);
		mockTransformerRequest.setName("Updated");

		ObjectMapper mapper = new ObjectMapper();
      	jsonRequest = mapper.writeValueAsString(mockTransformerRequest); 
      	jsonResponse = mapper.writeValueAsString(mockTransformer); 
		
		Mockito.when(transformerService.updateTransformer(Mockito.any(TransformerDto.class), Mockito.anyLong())).thenReturn(mockTransformer);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
				"/battlefield/v1/transformers/1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(jsonRequest.getBytes());
		
		mockMvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(jsonResponse));
	}

	@Test
	public void testDeleteTransformer() throws Exception {

		long transformerId = 1l;
		

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
				"/battlefield/v1/transformers/1").accept(
				MediaType.APPLICATION_JSON);
		
		mockMvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isNoContent());
		
		Mockito.verify(transformerService).deleteTransformer(1L);
	}

	@Test
	public void testFight() throws Exception {

		List<Long> transformerIds = Arrays.asList(1L,2L,3L);
		FightResponse response = new FightResponse(1, TransformerType.Autobot, Arrays.asList(1L));
		

		ObjectMapper mapper = new ObjectMapper();
      	String jsonRequest = mapper.writeValueAsString(transformerIds); 
      	String jsonResponse = mapper.writeValueAsString(response); 

		
		Mockito.when(fightService.scoreFighting(transformerIds)).thenReturn(response);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/battlefield/v1/transformers/fight")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(jsonRequest.getBytes());
		
		mockMvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(jsonResponse));
	}
	
	private TransformerDto buildMockTransformerDto(Long transformerId) {
		TechnicalSpecDto techSpec = TechnicalSpecDto.builder()
		.strength(6)
		.intelligence(6)
		.speed(6)
		.endurance(6)
		.rank(6)
		.courage(6)
		.firepower(6)
		.skill(6)
		.build();

		TransformerDto mockTransformer = 
				TransformerDto.builder()
				.transformerId(transformerId)
				.name("Test")
				.type(TransformerType.Autobot)
				.spec(techSpec)
				.build();
		
		return mockTransformer;
	}	
}
