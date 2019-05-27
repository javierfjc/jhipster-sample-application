import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Departamento } from 'app/shared/model/departamento.model';
import { DepartamentoService } from './departamento.service';
import { DepartamentoComponent } from './departamento.component';
import { DepartamentoDetailComponent } from './departamento-detail.component';
import { DepartamentoUpdateComponent } from './departamento-update.component';
import { DepartamentoDeletePopupComponent } from './departamento-delete-dialog.component';
import { IDepartamento } from 'app/shared/model/departamento.model';

@Injectable({ providedIn: 'root' })
export class DepartamentoResolve implements Resolve<IDepartamento> {
  constructor(private service: DepartamentoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDepartamento> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Departamento>) => response.ok),
        map((departamento: HttpResponse<Departamento>) => departamento.body)
      );
    }
    return of(new Departamento());
  }
}

export const departamentoRoute: Routes = [
  {
    path: '',
    component: DepartamentoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhispterOrclApp.departamento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DepartamentoDetailComponent,
    resolve: {
      departamento: DepartamentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhispterOrclApp.departamento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DepartamentoUpdateComponent,
    resolve: {
      departamento: DepartamentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhispterOrclApp.departamento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DepartamentoUpdateComponent,
    resolve: {
      departamento: DepartamentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhispterOrclApp.departamento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const departamentoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DepartamentoDeletePopupComponent,
    resolve: {
      departamento: DepartamentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhispterOrclApp.departamento.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
