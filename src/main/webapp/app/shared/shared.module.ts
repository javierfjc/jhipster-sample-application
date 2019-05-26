import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { JhispterOrclAppSharedLibsModule, JhispterOrclAppSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [JhispterOrclAppSharedLibsModule, JhispterOrclAppSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [JhispterOrclAppSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhispterOrclAppSharedModule {
  static forRoot() {
    return {
      ngModule: JhispterOrclAppSharedModule
    };
  }
}
