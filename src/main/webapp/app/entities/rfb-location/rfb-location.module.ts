import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RfbloyaltySharedModule } from 'app/shared/shared.module';
import { RfbLocationComponent } from './rfb-location.component';
import { RfbLocationDetailComponent } from './rfb-location-detail.component';
import { RfbLocationUpdateComponent } from './rfb-location-update.component';
import { RfbLocationDeleteDialogComponent, RfbLocationDeletePopupComponent } from './rfb-location-delete-dialog.component';
import { rfbLocationPopupRoute, rfbLocationRoute } from './rfb-location.route';

const ENTITY_STATES = [...rfbLocationRoute, ...rfbLocationPopupRoute];

@NgModule({
  imports: [RfbloyaltySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RfbLocationComponent,
    RfbLocationDetailComponent,
    RfbLocationUpdateComponent,
    RfbLocationDeleteDialogComponent,
    RfbLocationDeletePopupComponent
  ],
  entryComponents: [RfbLocationDeleteDialogComponent]
})
export class RfbloyaltyRfbLocationModule {}
