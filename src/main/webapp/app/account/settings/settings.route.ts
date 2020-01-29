import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { SettingsComponent } from './settings.component';

export const settingsRoute: Route = {
  path: 'settings',
  component: SettingsComponent,
  data: {
    authorities: ['ROLE_USER', 'ROLE_ORGANIZER', 'ROLE_ROOT', 'ROLE_RUNNER'],
    pageTitle: 'Settings'
  },
  canActivate: [UserRouteAccessService]
};
