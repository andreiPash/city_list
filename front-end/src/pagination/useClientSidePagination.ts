import {computed, Ref, ref} from "vue";

interface PaginationConfig<T> {
    rowsPerPage?: Ref<number>;
    arrayToPaginate: Ref<T[]>;
    currentPage: Ref<number>;
}

export function usePagination<T>(config: PaginationConfig<T>) {
    const rowsPerPage = config.rowsPerPage || ref(100);

    const paginatedArray = computed(() => {
            if (config.arrayToPaginate.value._embedded !== undefined && config.arrayToPaginate.value._embedded.cityModelList !== undefined) {
                // console.log(config.arrayToPaginate.value._embedded);
                // config.arrayToPaginate.value._embedded.cityModelList.slice(
                //     (config.currentPage.value - 1) * rowsPerPage.value,
                //     config.currentPage.value * rowsPerPage.value
                // )
                return config.arrayToPaginate.value._embedded.cityModelList;
            }
        }
    );

    const numberOfPages = computed(() => {
            if (config.arrayToPaginate.value !== undefined && config.arrayToPaginate.value.page !== undefined) {
                console.error('array: ' + paginatedArray.value);
                console.error('numberOfPages: ' + config.arrayToPaginate.value.page.totalPages);
                console.error('rowsPerPage: ' + config.rowsPerPage);
                return config.arrayToPaginate.value.page.totalPages;
            }
        }
    );

    return {
        paginatedArray,
        numberOfPages
    };
}
