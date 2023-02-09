package com.helmes.apashkovich.city.impl;

import com.helmes.apashkovich.city.City;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository repository;

    /**
     * Pageable City list with built-in searching & sorting
     * @param nameFilter  name filter
     * @param page        required page number
     * @param size        required page size
     * @param sortList    List of properties for sorting
     * @param sortOrder   sorting order
     * @return Pageable list of Cities which are eligible to search criteria
     */
    public Page<City> fetchCustomerDataAsPageWithFilteringAndSorting(String nameFilter, int page, int size, List<String> sortList, String sortOrder) {
        // create Pageable object using the page, size and sort details
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        // fetch the page object by additionally passing pageable with the filters
        return repository.findByNameLike(nameFilter, pageable);
    }

    /**
     * Update City endpoint
     * @param name   new City name
     * @param image  new City image URL
     * @param id     City ID required to be updated
     * @return an updated City
     */
    @Transactional
    public City update(String name, String image, Long id) {
        repository.update(name, image, id);
        return repository.findById(id).orElse(null);
    }

    /**
     * @return List of all Cities.
     */
    public List<City> findAll() {
        return repository.findAll();
    }

    private List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {
        List<Sort.Order> sorts = new ArrayList<>();
        Sort.Direction direction;
        for (String sort : sortList) {
            if (sortDirection != null) {
                direction = Sort.Direction.fromString(sortDirection);
            } else {
                direction = Sort.Direction.DESC;
            }
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }
}
