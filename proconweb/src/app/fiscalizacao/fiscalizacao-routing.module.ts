import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CadFiscalizacaoResolver } from '../resources/cad-fiscalizacao.resolver';
import { CadSetorResolver } from '../resources/cad-setor.resolver';
import { ListaFiscalizacaoResolver } from '../resources/lista-fiscalizacao.resolver';
import { SetorFiscalizacaoResolver } from '../resources/setor-fiscalizacao.resolver';
import { CadFiscalizacaoComponent } from './cad-fiscalizacao/cad-fiscalizacao.component';
import { CadSetorComponent } from './cad-setor/cad-setor.component';
import { ListaFiscalizacoesComponent } from './lista-fiscalizacoes/lista-fiscalizacoes.component';
import { ListaSetoresComponent } from './lista-setores/lista-setores.component';
import { TermoVisitaComponent } from './termo-visita/termo-visita.component';

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
  {
    path: 'visita',
    children: [
      {
        path: '',
        component: ListaFiscalizacoesComponent,
        resolve: { page: ListaFiscalizacaoResolver },
      },
      {
        path: 'novo',
        children: [
          { path: '', component: CadFiscalizacaoComponent },
          {
            path: ':id',
            component: CadFiscalizacaoComponent,
            resolve: { fiscalizacao: CadFiscalizacaoResolver },
          },
        ],
      },
    ],
  },
  {
    path: 'termo',
    component: TermoVisitaComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class FiscalizacaoRoutingModule {}
