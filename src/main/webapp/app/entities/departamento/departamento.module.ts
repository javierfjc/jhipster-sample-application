import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhispterOrclAppSharedModule } from 'app/shared';
import {
  DepartamentoComponent,
  DepartamentoDetailComponent,
  DepartamentoUpdateComponent,
  DepartamentoDeletePopupComponent,
  DepartamentoDeleteDialogComponent,
  departamentoRoute,
  departamentoPopupRoute
} from './';

const ENTITY_STATES = [...departamentoRoute, ...departamentoPopupRoute];

@NgModule({
  imports: [JhispterOrclAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DepartamentoComponent,
    DepartamentoDetailComponent,
    DepartamentoUpdateComponent,
    DepartamentoDeleteDialogComponent,
    DepartamentoDeletePopupComponent
  ],
  entryComponents: [
    DepartamentoComponent,
    DepartamentoUpdateComponent,
    DepartamentoDeleteDialogComponent,
    DepartamentoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhispterOrclAppDepartamentoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
