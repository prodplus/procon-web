import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CadConsumidorResolver } from '../resources/cad-consumidor.resolver';
import { ListaConsumidoresResolver } from '../resources/lista-consumidores.resolver';
import { CadConsumidorComponent } from './consumidores/cad-consumidor/cad-consumidor.component';
import { ListaConsumidoresComponent } from './consumidores/lista-consumidores/lista-consumidores.component';

export const routes: Routes = [
  {
    path: 'consumidores',
    children: [
      {
        path: '',
        component: ListaConsumidoresComponent,
        resolve: { page: ListaConsumidoresResolver },
      },
      {
        path: 'novo',
        children: [
          { path: '', component: CadConsumidorComponent },
          {
            path: ':id',
            component: CadConsumidorComponent,
            resolve: { consumidor: CadConsumidorResolver },
          },
        ],
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CadastroRoutingModule {}
