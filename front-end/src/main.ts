import { createApp } from 'vue'
import App from './App.vue'
import KeycloakService from "@/security/KeycloakService";

const renderApp = () => {
    createApp(App).mount('#app');
}

KeycloakService.CallLogin(renderApp);
