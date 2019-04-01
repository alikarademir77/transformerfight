package com.aequilibrium.transformerscoring.utility;

import java.util.ArrayList;
import java.util.List;

import com.aequilibrium.transformerscoring.model.entity.TechnicalSpec;
import com.aequilibrium.transformerscoring.model.entity.Transformer;
import com.aequilibrium.transformerscoring.model.rest.TechnicalSpecDto;
import com.aequilibrium.transformerscoring.model.rest.TransformerDto;

public class EntityDtoMapper {

	public static TransformerDto mapEntityToDto(Transformer entity) {

		TechnicalSpec doObjSpec = entity.getTechnicalSpec();

		TransformerDto dto = TransformerDto
		.builder()
			.name(entity.getName())
			.transformerId(entity.getId())
			.type(entity.getTransformerType())
			.spec(TechnicalSpecDto.builder()
					.strength(doObjSpec.getStrength())
					.intelligence(doObjSpec.getIntelligence())
					.speed(doObjSpec.getSpeed())
					.endurance(doObjSpec.getEndurance())
					.rank(doObjSpec.getRank())
					.courage(doObjSpec.getCourage())
					.firepower(doObjSpec.getFirepower())
					.skill(doObjSpec.getSkill())
					.build())
			.build();
		
		return dto;
	}

	public static Transformer mapDtoToEntity(TransformerDto dto) {
		
		Transformer doObj = new Transformer();
		doObj.setName(dto.getName());
		doObj.setTransformerType(dto.getType());

		TechnicalSpec technicalSpec = TechnicalSpec.builder()
			.strength(dto.getSpec().getStrength())
			.intelligence(dto.getSpec().getIntelligence())
			.speed(dto.getSpec().getSpeed())
			.endurance(dto.getSpec().getEndurance())
			.rank(dto.getSpec().getRank())
			.courage(dto.getSpec().getCourage())
			.firepower(dto.getSpec().getFirepower())
			.skill(dto.getSpec().getSkill())
			.build();
		
		doObj.setTechnicalSpec(technicalSpec);
		
		return doObj;
	}

	public static Transformer updateTransformerWithDto(Transformer existing, TransformerDto dto) {
		existing.setName(dto.getName()!=null?dto.getName(): existing.getName());
		existing.setTransformerType(dto.getType()!=null?dto.getType(): existing.getTransformerType());
		
		TechnicalSpec existingSpec = existing.getTechnicalSpec();
		TechnicalSpecDto dtoSpec = dto.getSpec();
		if(dtoSpec!=null) {
			existingSpec.setStrength(dto.getSpec().getStrength()!=null?
					dto.getSpec().getStrength():existingSpec.getStrength());
			existingSpec.setIntelligence(dto.getSpec().getIntelligence()!=null?
					dto.getSpec().getIntelligence():existingSpec.getIntelligence());
			existingSpec.setSpeed(dto.getSpec().getSpeed()!=null?
					dto.getSpec().getSpeed():existingSpec.getSpeed());
			existingSpec.setEndurance(dto.getSpec().getEndurance()!=null?
					dto.getSpec().getEndurance():existingSpec.getEndurance());
			existingSpec.setRank(dto.getSpec().getRank()!=null?
					dto.getSpec().getRank():existingSpec.getRank());
			existingSpec.setCourage(dto.getSpec().getCourage()!=null?
					dto.getSpec().getCourage():existingSpec.getCourage());
			existingSpec.setFirepower(dto.getSpec().getFirepower()!=null?
					dto.getSpec().getFirepower():existingSpec.getFirepower());
			existingSpec.setSkill(dto.getSpec().getSkill()!=null?
					dto.getSpec().getSkill():existingSpec.getSkill());

			existing.setTechnicalSpec(existingSpec);
		}
		
		return existing;
	}
	
	public static List<TransformerDto> mapEntitiesToDtos(Iterable<Transformer> entities){
		List<TransformerDto> dtos = new ArrayList<>();
	
		for(Transformer entity: entities) {
			TransformerDto dto = EntityDtoMapper.mapEntityToDto(entity);
			dtos.add(dto);
		}
		
		return dtos;
	}
}
