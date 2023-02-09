package com.helmes.apashkovich.city;

import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class CityUpdateDto {
    @NonNull
    private String name;
    @NonNull
    private String link;
}
