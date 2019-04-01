package com.aequilibrium.transformerscoring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aequilibrium.transformerscoring.exception.TransformerNotFoundException;
import com.aequilibrium.transformerscoring.model.entity.Transformer;
import com.aequilibrium.transformerscoring.model.rest.TransformerDto;
import com.aequilibrium.transformerscoring.repository.TransformerRepository;
import com.aequilibrium.transformerscoring.utility.EntityDtoMapper;


@Service
public class TransformerService {

	@Autowired
	private TransformerRepository transformerRepository;
	
	public TransformerDto retrieveTransformer(Long transformerId) {
		if(transformerId==null) {
			throw new IllegalArgumentException("Please provide a non empty transformer id");
		}
		
		Optional<Transformer> transformerOpt = transformerRepository.findById(transformerId);

		if(transformerOpt.isPresent()) {
			return EntityDtoMapper.mapEntityToDto(transformerOpt.get());
		}else {
			throw new TransformerNotFoundException(String.format("Couldn't find any transformer with id %d", transformerId));
		}
	}
	
	@Transactional
	public TransformerDto createTransformer(TransformerDto transformerPayload) {
		
		if(transformerPayload==null) {
			throw new IllegalArgumentException("Please provide transformer details");
		}
		Transformer transformer = EntityDtoMapper.mapDtoToEntity(transformerPayload);
		transformer = transformerRepository.save(transformer);
		
		return EntityDtoMapper.mapEntityToDto(transformer);
	}

	@Transactional	
	public TransformerDto updateTransformer(TransformerDto transformerPayload, Long transformerId) {
		if(transformerId==null) {
			throw new IllegalArgumentException("Please provide a non empty transformer id");
		}
		if(transformerPayload==null) {
			throw new IllegalArgumentException("Please provide transformer details to update");
		}

		Optional<Transformer> transformerOpt = transformerRepository.findById(transformerId);

		if(transformerOpt.isPresent()) {
			Transformer transformer = 
					EntityDtoMapper.updateTransformerWithDto(transformerOpt.get(), transformerPayload);			
			transformer = transformerRepository.save(transformer);
			
			return EntityDtoMapper.mapEntityToDto(transformer);
		}else {
			throw new TransformerNotFoundException(String.format("Couldn't find any transformer with id %d", transformerId));
		}
	}
	
	@Transactional	
	public void deleteTransformer(Long transformerId) {

		if(transformerId==null) {
			throw new IllegalArgumentException("Please provide a non empty transformer id");
		}

		Optional<Transformer> transformerOpt = transformerRepository.findById(transformerId);

		if(transformerOpt.isPresent()) {
			transformerRepository.delete(transformerOpt.get());
		}else {
			throw new TransformerNotFoundException(String.format("Couldn't find any transformer with id %d", transformerId));
		}
	}
	
	public List<TransformerDto> listTransformers() {
		Iterable<Transformer> transformers = transformerRepository.findAll();

		return EntityDtoMapper.mapEntitiesToDtos(transformers);
	}	
}
