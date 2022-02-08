import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CadSetorResolver } from '../resources/cad-setor.resolver';
import { SetorFiscalizacaoResolver } from '../resources/setor-fiscalizacao.resolver';
import { CadSetorComponent } from './cad-setor/cad-setor.component';
import { ListaSetoresComponent } from './lista-setores/lista-setores.component';

export const routes: Routes = [
  {
    path: 'setor',
    children: [
      {
        path: '',
        component: ListaSetoresComponent,
        resolve: { setores: SetorFiscalizacaoResolver },
      },
      {
        path: 'novo',
        children: [
          { path: '', component: CadSetorComponent },
          {
            path: ':id',
            component: CadSetorComponent,
            resolve: { setor: CadSetorResolver },
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
export class FiscalizacaoRoutingModule {}
