import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDepartamento } from 'app/shared/model/departamento.model';
import { AccountService } from 'app/core';
import { DepartamentoService } from './departamento.service';

@Component({
  selector: 'jhi-departamento',
  templateUrl: './departamento.component.html'
})
export class DepartamentoComponent implements OnInit, OnDestroy {
  departamentos: IDepartamento[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected departamentoService: DepartamentoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.departamentoService
      .query()
      .pipe(
        filter((res: HttpResponse<IDepartamento[]>) => res.ok),
        map((res: HttpResponse<IDepartamento[]>) => res.body)
      )
      .subscribe(
        (res: IDepartamento[]) => {
          this.departamentos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDepartamentos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDepartamento) {
    return item.id;
  }

  registerChangeInDepartamentos() {
    this.eventSubscriber = this.eventManager.subscribe('departamentoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
