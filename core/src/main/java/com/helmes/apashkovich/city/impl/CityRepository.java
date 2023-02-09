package com.helmes.apashkovich.city.impl;

import com.helmes.apashkovich.city.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CityRepository extends JpaRepository<City, Long> {
    String FILTER_CITIES_ON_NAME_QUERY = "select b from City b where UPPER(b.name) like CONCAT('%',UPPER(?1),'%')";
    String UPDATE_QUERY = "UPDATE City b SET b.name = ?1, b.link = ?2 WHERE b.id = ?3";

    @Query(FILTER_CITIES_ON_NAME_QUERY)
    Page<City> findByNameLike(String nameFilter, Pageable pageable);

    @Query(UPDATE_QUERY)
    @Modifying
    void update(String name, String image, Long id);
}
