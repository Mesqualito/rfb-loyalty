import { Component, OnDestroy, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { RfbLocation } from 'app/shared/model/rfb-location.model';
import { RfbEvent } from 'app/shared/model/rfb-event.model';
import { User } from 'app/core/user/user.model';
import { RfbEventAttendance } from 'app/shared/model/rfb-event-attendance.model';
import { RfbLocationService } from 'app/entities/rfb-location/rfb-location.service';
import { RfbEventService } from 'app/entities/rfb-event/rfb-event.service';
import { RfbEventAttendanceService } from 'app/entities/rfb-event-attendance/rfb-event-attendance.service';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account;
  modalRef: NgbModalRef;
  isSaving: boolean;
  locations: RfbLocation[];
  todaysEvent: RfbEvent;
  currentUser: User;
  // SimpleModel created with the EventCode and EventLocation:
  model: any;
  rfbEventAttendance: RfbEventAttendance;
  errors: any = { invalidEventCode: '' };
  checkedIn = false;

  constructor(
    private _principal: Principal,
    private _loginModalService: LoginModalService,
    private _eventManager: JhiEventManager,
    private _locationService: RfbLocationService,
    private _eventService: RfbEventService,
    private _accountService: AccountService,
    private _rfbEventAttendanceService: RfbEventAttendanceService
  ) {}

  ngOnInit() {
    this._accountService.identity().subscribe((account: Account) => {
      this.account = account;
    });
    this.registerAuthenticationSuccess();
    this.loadLocations();
    this.model = { location: 0, eventCode: '' };
    this.rfbEventAttendance = new RfbEventAttendance(null, new Date(), new RfbEvent(), new User());

    // get current user and if it has a role of ORGANIZER show todays event for the users home location
    this._accountService.get().subscribe((user: User) => {
      this.currentUser = user;
      this.rfbEventAttendance.userDTO = user;
      // we can set todays event for anyone who has a homeLocation. If they don't we should setTodays event
      // when they change the location drop down || or just grab the event and then compare their event code to
      // the events --- we use two-if-construct, because logged-in User can be both!
      if (this.currentUser.authorities.indexOf('ROLE_ORGANIZER') !== -1) {
        this.setTodaysEvent(this.currentUser.homeLocation);
      }
      if (this.currentUser.authorities.indexOf('ROLE_RUNNER') !== -1) {
        // set home location
        this.model.location = this.currentUser.homeLocation;
      }
    });
  }

  registerAuthenticationSuccess() {
    this._eventManager.subscribe('authenticationSuccess', message => {
      this._principal.identity().then(account => {
        this.account = account;
      });
    });
    this.account = account;
  }

  isAuthenticated() {
    return this._accountService.isAuthenticated();
  }

  login() {
    this.modalRef = this.loginModalService.open();
  }

  setTodaysEvent(locationID: number) {
    // reach out to our event service and find an event with todays date & this location id: homeLocationId;
    this._eventService.findByLocation(locationID).subscribe((rfbEvent: RfbEvent) => {
      this.todaysEvent = rfbEvent;
    });
  }

  loadLocations() {
    this._locationService
      .query({
        page: 0,
        size: 100,
        sort: ['locationName', 'ASC']
      })
      .subscribe(
        (res: ResponseWrapper) => {
          this.locations = res.json;
        },
        (res: ResponseWrapper) => {
          console.log(res);
        }
      );
  }

  ngOnDestroy() {
    if (this.authSubscription) {
      this.eventManager.destroy(this.authSubscription);
    }
  }
}
