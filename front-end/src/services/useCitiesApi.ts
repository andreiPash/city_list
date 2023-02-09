import {ref, Ref} from "@vue/reactivity";
import {usePagination} from "@/pagination/useClientSidePagination";
import axios from 'axios';
import keycloakService from "@/security/KeycloakService";

// const URL = "https://jsonplaceholder.typicode.com/todos/";
const URL = "http://localhost:8080/backend/cities/pageable";

export interface CityModel {
    id: number;
    name: string;
    link: string;
}

export function useCitiesApi(
    currentPage: Ref<number>,
    rowsPerPage?: Ref<number>
) {
    const cityForEdit: Ref<CityModel> = ref({});

    const cities: Ref<CityModel[]> = ref([]);

    const citiesAreLoading = ref(false);

    const {paginatedArray, numberOfPages} = usePagination<CityModel>({
        rowsPerPage,
        arrayToPaginate: cities,
        currentPage
    });

    const loadCities = async () => {
        citiesAreLoading.value = true;
        try {
            const config = {
                headers: {
                    Authorization: `Bearer ` + keycloakService.GetAccessToken()
                }
            };
            const result = await axios.get(URL, config);
            console.error(result.data);
            // cities.value = result.data._embedded.cityModelList;
            cities.value = result.data;
        } catch (err) {
            console.log(err);
        } finally {
            citiesAreLoading.value = false;
        }
    };

    return {
        cities: paginatedArray,
        loadCities: loadCities,
        citiesAreLoading: citiesAreLoading,
        numberOfPages,
        cityForEdit
    };
}