<template>
  <div class="container">
    <pagination-component
        class="pagination-component"
        v-model="currentPage"
        :numberOfPages="numberOfPages"
    />
    <table class="table-fill">
      <thead>
      <tr>
        <th>Id</th>
        <th class="text-left">Name</th>
        <th class="text-left">Image</th>
      </tr>
      </thead>
      <tbody class="table-hover">
      <tr v-for="city in cities" :key="city.id" @click='onCityClick(city)'>
        <td>{{ city.id }}</td>
        <td class="text-left">{{ city.name }}</td>
        <td class="text-left"><img v-bind:src="city.link"></td>
      </tr>
      </tbody>
    </table>
    <EditCityPopup v-if="popupTrigger.isDisplayed"
                   :TogglePopup="() => TogglePopup()"
                   v-bind:cityForEdit="cityForEdit.value">
      <h2>Edit city</h2>
      <div class="popup-content">
      <span>City id: {{cityForEdit.id}}</span>
      <span>City Name:</span>
      <input type="text" v-model="cityForEdit.name" />
      <span>City Image URL:</span>
      <input type="text" v-model="cityForEdit.link"/>
      </div>
    </EditCityPopup>
  </div>
</template>

<script lang="ts" setup>
import {defineComponent, onMounted, ref, watch} from "vue";
import PaginationComponent from "./components/PaginationComponent.vue";
import {CityModel, useCitiesApi} from "./services/useCitiesApi";
import KeyCloakService from "@/security/KeycloakService";
import EditCityPopup from "@/components/EditCityPopup.vue";

const popupTrigger = ref({
  isDisplayed: false
});

const TogglePopup = () => {
  popupTrigger.value.isDisplayed = !popupTrigger.value.isDisplayed
}

const currentPage = ref(1);
const rowsPerPage = ref(30);

const {cities, citiesAreLoading, loadCities, numberOfPages, cityForEdit} = useCitiesApi(
    currentPage,
    rowsPerPage
);

const onCityClick = (city: CityModel) => {
  console.log(city.id);
  console.log(popupTrigger.value.isDisplayed);
  TogglePopup();
  console.log(popupTrigger.value.isDisplayed);
  cityForEdit.value = city;
  console.log(city.id);
  console.log(city.name);
  console.log(city.link);
  console.log(cityForEdit.value.id);
  console.log(cityForEdit.value.name);
  console.log(cityForEdit.value.link);

}

onMounted(async () => loadCities());
</script>

<style lang="scss">
body {
  padding: 15px;
  font-family: "Inter", sans-serif;
  background: #1D976C; /* fallback for old browsers */
  background: -webkit-linear-gradient(
          to left,
          #93F9B9,
          #1D976C
  ); /* Chrome 10-25, Safari 5.1-6 */
  background: linear-gradient(
          to left,
          #93F9B9,
          #1D976C
  ); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
}

.pagination-component {
  margin-top: 15px;
  margin-bottom: 15px;
}

.container {
  display: flex;
  align-items: center;
  flex-direction: column;
}

.table-fill {
  max-width: 1000px;
  background: white;
  border-radius: 14px;
  border-collapse: collapse;
  overflow: hidden;
  height: 320px;
  margin: auto;
  padding: 5px;
  width: 100%;
  animation: float 5s infinite;
  //box-shadow: rgb(0 0 0 / 0%) 0px 0px 0px 0px, rgb(0 0 0 / 0%) 0px 0px 0px 0px,
  //rgb(0 0 0 / 12%) 0px 1px 1px 0px, rgb(60 66 87 / 16%) 0px 0px 0px 1px,
  //rgb(0 0 0 / 0%) 0px 0px 0px 0px, rgb(0 0 0 / 0%) 0px 0px 0px 0px,
  //rgb(60 66 87 / 8%) 0px 2px 5px 0px;
}

th {
  border-bottom: 4px solid #60d394;
  border-right: 1px solid #C1C3D1;
  font-size: 16px;
  font-family: "Inter", sans-serif;
  padding: 12px;
  font-weight: 800;
  text-align: center;
  vertical-align: middle;
}

th:first-child {
  border-top-left-radius: 3px;
}

th:last-child {
  border-top-right-radius: 3px;
  border-right: none;
}

tr {
  border-top: 1px solid #C1C3D1;
  border-bottom-: 1px solid #C1C3D1;
  color: #666B85;
  font-size: 16px;
  font-weight: normal;
  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);
}

tr:hover td {
  background: #4E5066;
  color: #FFFFFF;
  border-top: 1px solid #22262e;
}

tr:first-child {
  border-top: none;
}

tr:last-child {
  border-bottom: none;
}

tr:nth-child(odd) td {
  background: #EBEBEB;
}

tr:nth-child(odd):hover td {
  background: #4E5066;
}

tr:last-child td:first-child {
  border-bottom-left-radius: 3px;
}

tr:last-child td:last-child {
  border-bottom-right-radius: 3px;
}

td {
  background: #FFFFFF;
  padding: 12px;
  text-align: center;
  vertical-align: middle;
  font-family: "Inter", sans-serif;
  font-size: 18px;
  border-right: 1px solid #C1C3D1;
}

td:last-child {
  border-right: 0px;
}

th.text-left {
  text-align: left;
}

th.text-center {
  text-align: center;
}

th.text-right {
  text-align: right;
}

td.text-left {
  text-align: left;
}

td.text-center {
  text-align: center;
}

td.text-right {
  text-align: right;
}

img {
  border-radius: 5px;
  display: block;
  margin-left: auto;
  margin-right: auto;
  width: 50%;
}
</style>
