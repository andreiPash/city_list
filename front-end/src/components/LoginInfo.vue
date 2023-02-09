<template>
  <div class="loginInfo">
    <h2>Login: {{ Login() }}</h2>
    <h2>Roles: {{ UserRoles()?.join("; ") }}</h2>
    <h2>Access Token: {{ AccessToken() }}</h2>
    <h2>Has role for edit: {{ HasRoleForEdit() }}</h2>
    <button @click="LogOut">Log Out</button>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import KeyCloakService from "../security/KeycloakService";

export default defineComponent({
  name: "LoginInfo",
  methods: {
    Login() {
      return KeyCloakService.GetUserName();
    },
    AccessToken() {
      return KeyCloakService.GetAccessToken();
    },
    LogOut() {
      return KeyCloakService.CallLogOut();
    },
    UserRoles() {
      return KeyCloakService.GetUserRoles();
    },
    HasRoleForEdit() {
      return KeyCloakService.HasRoleForEdit();
    },
  },
});
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
