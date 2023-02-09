package com.helmes.apashkovich.city;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import com.helmes.apashkovich.city.impl.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CityController {
    public static final String CITIES_URI = "/cities";
    public static final String CITIES_PAGEABLE_URI = "/cities/pageable";
    public static final String CITY_UPDATE_URI = "/city/{id}/update";

    private final CityModelAssembler cityModelAssembler;
    private final CityService cityService;
    private final PagedResourcesAssembler<City> pagedResourcesAssembler;

    /**
     * @return CollectionModel of all Cities in db
     */
    @GetMapping(CITIES_URI)
    public CollectionModel<EntityModel<City>> all() {
        List<EntityModel<City>> cities = cityService.findAll().stream()
                .map(employee -> EntityModel.of(employee,
                        linkTo(methodOn(CityController.class).all()).withRel("cities")))
                .collect(Collectors.toList());
        return CollectionModel.of(cities, linkTo(methodOn(CityController.class).all()).withSelfRel());
    }

    /**
     * @param nameFilter      Filter for the Name if required
     * @param page            number of the page returned
     * @param size            number of entries in each page
     * @param sortList        list of columns to sort on
     * @param sortOrder       sort order. Can be ASC or DESC
     * @return PagedModel object in Hateoas with customers after filtering and sorting
     */
    @GetMapping(CITIES_PAGEABLE_URI)
    public PagedModel<CityModel> fetchCustomersWithPagination(
            @RequestParam(defaultValue = "") String nameFilter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "") List<String> sortList,
            @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder) {
        Page<City> customerPage = cityService.fetchCustomerDataAsPageWithFilteringAndSorting(nameFilter, page, size, sortList, sortOrder.toString());
        // Use the pagedResourcesAssembler and cityModelAssembler to convert data to PagedModel format
        return pagedResourcesAssembler.toModel(customerPage, cityModelAssembler);
    }

    /**
     * @param id             id of the City to update
     * @param cityUpdateDto  dto object with changes
     * @return HttpEntity of updated City
     */
    @PatchMapping(CITY_UPDATE_URI)
    public HttpEntity<CityModel> update(@PathVariable("id") Long id, @RequestBody CityUpdateDto cityUpdateDto) {
        CityModel updated = cityModelAssembler.toModel(cityService.update(cityUpdateDto.getName(), cityUpdateDto.getLink(), id));
        updated.add(linkTo(methodOn(CityController.class).fetchCustomersWithPagination(null, 0, 20, null, Sort.Direction.DESC)).withRel("pageableCityListWithSearchByName"));
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}
