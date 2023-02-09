package com.helmes.apashkovich.city;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = City.ENTITY)
@Data
@RequiredArgsConstructor
public class City {
    public static final String ENTITY = "city";

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "link")
    private String link;

    public City(String name, String link) {
        this.name = name;
        this.link = link;
    }
}
