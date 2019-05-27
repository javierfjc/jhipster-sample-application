import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Tarea } from 'app/shared/model/tarea.model';
import { TareaService } from './tarea.service';
import { TareaComponent } from './tarea.component';
import { TareaDetailComponent } from './tarea-detail.component';
import { TareaUpdateComponent } from './tarea-update.component';
import { TareaDeletePopupComponent } from './tarea-delete-dialog.component';
import { ITarea } from 'app/shared/model/tarea.model';

@Injectable({ providedIn: 'root' })
export class TareaResolve implements Resolve<ITarea> {
  constructor(private service: TareaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITarea> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Tarea>) => response.ok),
        map((tarea: HttpResponse<Tarea>) => tarea.body)
      );
    }
    return of(new Tarea());
  }
}

export const tareaRoute: Routes = [
  {
    path: '',
    component: TareaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhispterOrclApp.tarea.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TareaDetailComponent,
    resolve: {
      tarea: TareaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhispterOrclApp.tarea.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TareaUpdateComponent,
    resolve: {
      tarea: TareaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhispterOrclApp.tarea.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TareaUpdateComponent,
    resolve: {
      tarea: TareaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhispterOrclApp.tarea.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const tareaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TareaDeletePopupComponent,
    resolve: {
      tarea: TareaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhispterOrclApp.tarea.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
