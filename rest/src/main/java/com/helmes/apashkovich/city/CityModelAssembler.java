package com.helmes.apashkovich.city;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

/**
 * This class extends RepresentationModelAssemblerSupport which is required for Pagination.
 * It converts the City Entity to the CityModel and has the code for it.
 */
@Configuration
public class CityModelAssembler extends RepresentationModelAssemblerSupport<City, CityModel> {
    public CityModelAssembler() {
        super(CityController.class, CityModel.class);
    }

    @Override
    public CityModel toModel(City entity) {
        CityModel model = new CityModel();
        // Both CityModel and City have the same property names. So copy the values from the Entity to the Model
        BeanUtils.copyProperties(entity, model);
        return model;
    }
}
