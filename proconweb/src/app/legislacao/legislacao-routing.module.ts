import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CadLeiResolver } from '../resources/cad-lei.resolver';
import { ListaLeisResolver } from '../resources/lista-leis.resolver';
import { CadLegislaComponent } from './cad-legisla/cad-legisla.component';
import { ListaLeisComponent } from './lista-leis/lista-leis.component';

export const routes: Routes = [
  {
    path: 'cadlei',
    children: [
      { path: '', component: CadLegislaComponent },
      {
        path: ':id',
        component: CadLegislaComponent,
        resolve: { lei: CadLeiResolver },
      },
    ],
  },
  {
    path: '',
    component: ListaLeisComponent,
    resolve: { page: ListaLeisResolver },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class LegislacaoRoutingModule {}
