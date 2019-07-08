import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'autor',
        loadChildren: './autor/autor.module#DataStructureWebAutorModule'
      },
      {
        path: 'libro',
        loadChildren: './libro/libro.module#DataStructureWebLibroModule'
      },
      {
        path: 'cliente',
        loadChildren: './cliente/cliente.module#DataStructureWebClienteModule'
      },
      {
        path: 'venta',
        loadChildren: './venta/venta.module#DataStructureWebVentaModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DataStructureWebEntityModule {}
