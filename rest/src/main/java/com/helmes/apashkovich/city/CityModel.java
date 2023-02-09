package com.helmes.apashkovich.city;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

/**
 * The CityModel class extends the Hateoas Representation Model and is required
 * if we want to convert the City Entity to a pagination format
 */
@Getter
@Setter
public class CityModel extends RepresentationModel<CityModel> {
    private Long id;
    private String name;
    private String link;
}
