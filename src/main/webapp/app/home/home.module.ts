import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {RfbloyaltySharedModule} from '../shared';

import {HOME_ROUTE, HomeComponent} from './';
import {LeaderboardComponent} from '../leaderboard-component/leaderboard.component';

@NgModule({
    imports: [
        RfbloyaltySharedModule,
        RouterModule.forRoot([ HOME_ROUTE ], { useHash: true })
    ],
    declarations: [
        HomeComponent,
        LeaderboardComponent
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RfbloyaltyHomeModule {}
