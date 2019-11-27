import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RfbloyaltySharedModule } from 'app/shared/shared.module';
import { RfbEventComponent } from './rfb-event.component';
import { RfbEventDetailComponent } from './rfb-event-detail.component';
import { RfbEventUpdateComponent } from './rfb-event-update.component';
import { RfbEventDeleteDialogComponent, RfbEventDeletePopupComponent } from './rfb-event-delete-dialog.component';
import { rfbEventPopupRoute, rfbEventRoute } from './rfb-event.route';

const ENTITY_STATES = [...rfbEventRoute, ...rfbEventPopupRoute];

@NgModule({
  imports: [RfbloyaltySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RfbEventComponent,
    RfbEventDetailComponent,
    RfbEventUpdateComponent,
    RfbEventDeleteDialogComponent,
    RfbEventDeletePopupComponent
  ],
  entryComponents: [RfbEventDeleteDialogComponent]
})
export class RfbloyaltyRfbEventModule {}
