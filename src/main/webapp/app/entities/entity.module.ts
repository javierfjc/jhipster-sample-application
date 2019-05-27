import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'proyecto',
        loadChildren: './proyecto/proyecto.module#JhispterOrclAppProyectoModule'
      },
      {
        path: 'cliente',
        loadChildren: './cliente/cliente.module#JhispterOrclAppClienteModule'
      },
      {
        path: 'contacto',
        loadChildren: './contacto/contacto.module#JhispterOrclAppContactoModule'
      },
      {
        path: 'tarea',
        loadChildren: './tarea/tarea.module#JhispterOrclAppTareaModule'
      },
      {
        path: 'departamento',
        loadChildren: './departamento/departamento.module#JhispterOrclAppDepartamentoModule'
      },
      {
        path: 'perfil',
        loadChildren: './perfil/perfil.module#JhispterOrclAppPerfilModule'
      },
      {
        path: 'empleado',
        loadChildren: './empleado/empleado.module#JhispterOrclAppEmpleadoModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhispterOrclAppEntityModule {}
