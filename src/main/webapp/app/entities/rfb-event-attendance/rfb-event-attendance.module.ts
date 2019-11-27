import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RfbloyaltySharedModule } from 'app/shared/shared.module';
import { RfbEventAttendanceComponent } from './rfb-event-attendance.component';
import { RfbEventAttendanceDetailComponent } from './rfb-event-attendance-detail.component';
import { RfbEventAttendanceUpdateComponent } from './rfb-event-attendance-update.component';
import {
  RfbEventAttendanceDeleteDialogComponent,
  RfbEventAttendanceDeletePopupComponent
} from './rfb-event-attendance-delete-dialog.component';
import { rfbEventAttendancePopupRoute, rfbEventAttendanceRoute } from './rfb-event-attendance.route';

const ENTITY_STATES = [...rfbEventAttendanceRoute, ...rfbEventAttendancePopupRoute];

@NgModule({
  imports: [RfbloyaltySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RfbEventAttendanceComponent,
    RfbEventAttendanceDetailComponent,
    RfbEventAttendanceUpdateComponent,
    RfbEventAttendanceDeleteDialogComponent,
    RfbEventAttendanceDeletePopupComponent
  ],
  entryComponents: [RfbEventAttendanceDeleteDialogComponent]
})
export class RfbloyaltyRfbEventAttendanceModule {}
