import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DataStructureWebSharedModule } from 'app/shared';
import {
  LibroComponent,
  LibroDetailComponent,
  LibroUpdateComponent,
  LibroDeletePopupComponent,
  LibroDeleteDialogComponent,
  libroRoute,
  libroPopupRoute
} from './';

const ENTITY_STATES = [...libroRoute, ...libroPopupRoute];

@NgModule({
  imports: [DataStructureWebSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [LibroComponent, LibroDetailComponent, LibroUpdateComponent, LibroDeleteDialogComponent, LibroDeletePopupComponent],
  entryComponents: [LibroComponent, LibroUpdateComponent, LibroDeleteDialogComponent, LibroDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DataStructureWebLibroModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
