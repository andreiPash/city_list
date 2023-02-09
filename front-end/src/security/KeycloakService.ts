import Keycloak from "keycloak-js";

const keycloakInstance = new Keycloak({
    url: 'http://localhost:8383',
    realm: 'realmA',
    clientId:'vuejs'
});

interface CallbackOneParam<T1 = void, T2 = void> {
    (param1: T1): T2;
}
/**
 * Initializes Keycloak instance and calls the provided callback function if successfully authenticated.
 *
 * @param onAuthenticatedCallback
 */
const Login = (onAuthenticatedCallback: CallbackOneParam): void => {
    keycloakInstance
        .init({ onLoad: "login-required" })
        .then(function (authenticated) {
            authenticated ? onAuthenticatedCallback() : alert("non authenticated");
        })
        .catch((e) => {
            console.dir(e);
            console.log(`keycloak init exception: ${e}`);
        });
};

const UserName = (): string | undefined =>
    keycloakInstance?.tokenParsed?.preferred_username;

const Token = (): string | undefined => keycloakInstance?.token;

const LogOut = () => keycloakInstance.logout();

const UserRoles = (): string[] | undefined => {
    if (keycloakInstance.resourceAccess === undefined) return undefined;
    return keycloakInstance.resourceAccess["vuejs"].roles;
};

const HasRoleForEdit = () : boolean | undefined => {
    return keycloakInstance.hasResourceRole('ROLE_ALLOW_EDIT', 'vuejs');
}

const KeyCloakService = {
    CallLogin: Login,
    GetUserName: UserName,
    GetAccessToken: Token,
    CallLogOut: LogOut,
    GetUserRoles: UserRoles,
    HasRoleForEdit: HasRoleForEdit,
};

export default KeyCloakService;