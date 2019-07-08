import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DataStructureWebSharedModule } from 'app/shared';
import {
  VentaComponent,
  VentaDetailComponent,
  VentaUpdateComponent,
  VentaDeletePopupComponent,
  VentaDeleteDialogComponent,
  ventaRoute,
  ventaPopupRoute
} from './';

const ENTITY_STATES = [...ventaRoute, ...ventaPopupRoute];

@NgModule({
  imports: [DataStructureWebSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [VentaComponent, VentaDetailComponent, VentaUpdateComponent, VentaDeleteDialogComponent, VentaDeletePopupComponent],
  entryComponents: [VentaComponent, VentaUpdateComponent, VentaDeleteDialogComponent, VentaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DataStructureWebVentaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
