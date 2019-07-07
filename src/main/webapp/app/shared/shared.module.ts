import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DataStructureWebSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [DataStructureWebSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [DataStructureWebSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DataStructureWebSharedModule {
  static forRoot() {
    return {
      ngModule: DataStructureWebSharedModule
    };
  }
}
