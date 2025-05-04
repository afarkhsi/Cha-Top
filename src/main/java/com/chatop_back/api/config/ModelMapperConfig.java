package com.chatop_back.api.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chatop_back.api.model.Rental;
import com.chatop_back.api.payload.RentalSingleResponse;

@Configuration
public class ModelMapperConfig {
	@Bean
	public ModelMapper modelMapper() {
	    ModelMapper modelMapper = new ModelMapper();
	    
	    modelMapper.addConverter(context -> {
	        Rental rental = context.getSource();
	        return new RentalSingleResponse(
	            rental.getId(),
	            rental.getName(),
	            rental.getSurface(),
	            rental.getPrice(),
	            "http://localhost:3001/uploads/" + rental.getPicture(), 
	            rental.getDescription(),
	            rental.getOwner().getId(),
	            rental.getCreated_at(),
	            rental.getUpdated_at()
	        );
	    }, Rental.class, RentalSingleResponse.class);
	    
	    return modelMapper;
	}
}