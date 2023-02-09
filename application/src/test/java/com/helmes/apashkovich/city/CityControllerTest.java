package com.helmes.apashkovich.city;

import com.helmes.apashkovich.city.impl.CityService;
import com.helmes.apashkovich.security.JwtAuthConverterProperties;
import com.helmes.apashkovich.security.WebSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CityController.class)
@Import({JwtAuthConverterProperties.class, WebSecurityConfig.class})
public class CityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityService cityService;

    @MockBean
    private CityModelAssembler cityModelAssembler;

    @MockBean
    private PagedResourcesAssembler<City> pagedResourcesAssembler;

    private static final long ID = 1;

    @Test
    void test1() throws Exception {
        given(cityService.findAll()).willReturn(Collections.emptyList());

        ResultActions resultActions = mockMvc.perform(get(CityController.CITIES_URI))
                .andDo(print());

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"));
    }

    @Test
    @WithMockUser(roles = "FAKE_ROLE")
    void updateWithFakeRoleIsForbidden() throws Exception {
        City city = defaultCity();
        given(cityService.update(any(), any(), any())).willReturn(city);

        ResultActions resultActions = mockMvc.perform(patch(CityController.CITY_UPDATE_URI, 1))
                .andDo(print());

        resultActions.andExpect(status().isForbidden());
    }

    private City defaultCity() {
        City city = new City();
        city.setId(ID);
        city.setName("TEST1");
        city.setLink("TestLink");
        return city;
    }
}
