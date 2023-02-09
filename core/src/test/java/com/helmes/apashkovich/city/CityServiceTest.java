package com.helmes.apashkovich.city;

import com.helmes.apashkovich.city.impl.CityRepository;
import com.helmes.apashkovich.city.impl.CityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@Import(CityService.class)
public class CityServiceTest {
    @Autowired
    private CityService service;

    @MockBean
    private CityRepository repository;

    @Test
    public void testUpdate() {
        City city = getDefaultCity(0);
        given(repository.findById(any())).willReturn(Optional.of(city));

        City actual = service.update(city.getName(), city.getLink(), 0L);
        assertThat(actual).isEqualTo(city);
    }

    @Test
    public void testPageable() {
        given(repository.findByNameLike(any(), any())).willReturn(getDefaultPageable());
        Page<City> actual = service.fetchCustomerDataAsPageWithFilteringAndSorting(null, 0, 20, Collections.emptyList(), "DESC");
        assertThat(actual.stream().count()).isEqualTo(20);
        assertThat(actual.getTotalPages()).isEqualTo(1);
    }

    private City getDefaultCity(long id) {
        City result = new City("testName" + id, "testLink" + id);
        result.setId(id);
        return result;
    }

    private Page<City> getDefaultPageable() {
        List<City> result = new ArrayList<>();
        for (long i = 0; i < 20; i++) {
            result.add(getDefaultCity(i));
        }
        return new PageImpl<>(result);
    }
}
