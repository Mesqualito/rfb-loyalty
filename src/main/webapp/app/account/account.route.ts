import {Routes} from '@angular/router';

import {activateRoute, passwordResetFinishRoute, passwordResetInitRoute, passwordRoute, registerRoute, sessionsRoute, settingsRoute, socialRegisterRoute} from './';

const ACCOUNT_ROUTES = [
    activateRoute,
    passwordRoute,
    passwordResetFinishRoute,
    passwordResetInitRoute,
    registerRoute,
    sessionsRoute,
    socialRegisterRoute,
    settingsRoute
];

export const accountState: Routes = [{
    path: '',
    children: ACCOUNT_ROUTES
}];
