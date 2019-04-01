package com.aequilibrium.transformerscoring.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aequilibrium.transformerscoring.model.rest.FightResponse;
import com.aequilibrium.transformerscoring.model.rest.TransformerDto;
import com.aequilibrium.transformerscoring.service.TransformerFightService;
import com.aequilibrium.transformerscoring.service.TransformerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(path="/battlefield/v1")
@Api(value="battlefield", description="Transformer Battle Field Management System")
public class TransformerController {

	@Autowired
	TransformerService transformerService;

	@Autowired
	TransformerFightService fightService;

	@ApiOperation(value = "View list of all transformers", response = TransformerDto[].class)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "All transformer has been retrieved")})	
	@GetMapping(path="/transformers", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseEntity<TransformerDto[]> listAllTransformers() {
		List<TransformerDto> response = transformerService.listTransformers();
		return new ResponseEntity<>(response.toArray(new TransformerDto[0]), HttpStatus.OK);
	}

	@ApiOperation(value = "Retrieve details of transformer with given id", response = TransformerDto.class)	
	@ApiResponses(value = { 
				@ApiResponse(code = 200, message = "Transformer with the input id has been retrieved"),
				@ApiResponse(code = 400, message = "Invalid Id supplied"),
				@ApiResponse(code = 404, message = "Transformer not found") })
	@GetMapping(path="/transformers/{id}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseEntity<TransformerDto> retrieveTransformer(@ApiParam(value = "Id of transformer to retrieve", required = true) @PathVariable Long id) {
		TransformerDto response = transformerService.retrieveTransformer(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}	

	@ApiOperation(value = "Create a new transformer", response = TransformerDto.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "New transformer has been created"),
			@ApiResponse(code = 400, message = "Invalid payload request") })
	@PostMapping(path="/transformers", consumes= MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseEntity<TransformerDto> newTransformer(@ApiParam(value = "Details of transformer to create", required = true) @Valid @RequestBody TransformerDto payload) {
		TransformerDto response = transformerService.createTransformer(payload);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}	

	@ApiOperation(value = "Update an existing transformer", response = TransformerDto.class)	
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Transformer has been updated"),
			@ApiResponse(code = 400, message = "Invalid payload or id specified"),
			@ApiResponse(code = 404, message = "Transformer to update not found")})	
	@PutMapping(path="/transformers/{id}", consumes= MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseEntity<TransformerDto> updateTransformer(@ApiParam(value = "Details of transformer to update", required = true) @Valid @RequestBody TransformerDto payload, 
			@ApiParam(value = "Id of existing transformer to update", required = true) @PathVariable Long id) {
		TransformerDto response = transformerService.updateTransformer(payload, id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}	

	@ApiOperation(value = "Delete an existing transformer", response = Void.class)	
	@ApiResponses(value = { 
			@ApiResponse(code = 204, message = "Transformer has been deleted"),
			@ApiResponse(code = 400, message = "Invalid Id supplied"),			
			@ApiResponse(code = 404, message = "Transformer to update not found")})		
	@DeleteMapping(path="/transformers/{id}")
	ResponseEntity<Void> deleteTransformer(@ApiParam(value = "Id of existing transformer to delete", required = true) @PathVariable Long id) {
		transformerService.deleteTransformer(id);
		return new ResponseEntity<Void>( HttpStatus.NO_CONTENT);
	}	

	@ApiOperation(value = "Score result of fight for a list of transformers", response = FightResponse.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 400, message = "Invalid Id(s) supplied"),			
			@ApiResponse(code = 404, message = "Transformer(s) to update not found"),		
			@ApiResponse(code = 200, message = "Fight score has been computed")})		
	@PostMapping(path="/transformers/fight", consumes= MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseEntity<FightResponse> fight(@RequestBody List<Long> transformerIds) {
		FightResponse response = fightService.scoreFighting(transformerIds);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}	
	
}
