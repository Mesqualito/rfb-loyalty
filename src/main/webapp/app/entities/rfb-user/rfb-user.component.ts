import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IRfbUser } from 'app/shared/model/rfb-user.model';
import { AccountService } from 'app/core/auth/account.service';
import { RfbUserService } from './rfb-user.service';

@Component({
  selector: 'jhi-rfb-user',
  templateUrl: './rfb-user.component.html'
})
export class RfbUserComponent implements OnInit, OnDestroy {
  rfbUsers: IRfbUser[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected rfbUserService: RfbUserService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.rfbUserService
      .query()
      .pipe(
        filter((res: HttpResponse<IRfbUser[]>) => res.ok),
        map((res: HttpResponse<IRfbUser[]>) => res.body)
      )
      .subscribe((res: IRfbUser[]) => {
        this.rfbUsers = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInRfbUsers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRfbUser) {
    return item.id;
  }

  registerChangeInRfbUsers() {
    this.eventSubscriber = this.eventManager.subscribe('rfbUserListModification', response => this.loadAll());
  }
}
