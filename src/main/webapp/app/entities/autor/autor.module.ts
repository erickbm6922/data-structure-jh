import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DataStructureWebSharedModule } from 'app/shared';
import {
  AutorComponent,
  AutorDetailComponent,
  AutorUpdateComponent,
  AutorDeletePopupComponent,
  AutorDeleteDialogComponent,
  autorRoute,
  autorPopupRoute
} from './';

const ENTITY_STATES = [...autorRoute, ...autorPopupRoute];

@NgModule({
  imports: [DataStructureWebSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [AutorComponent, AutorDetailComponent, AutorUpdateComponent, AutorDeleteDialogComponent, AutorDeletePopupComponent],
  entryComponents: [AutorComponent, AutorUpdateComponent, AutorDeleteDialogComponent, AutorDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DataStructureWebAutorModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
