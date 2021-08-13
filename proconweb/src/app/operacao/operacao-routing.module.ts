import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../core/auth/auth.guard';
import { CadProcessoResolver } from '../resources/cad-processo.resolver';
import { NotConsumidorResolver } from '../resources/not-consumidor.resolver';
import { NotFornecedorResolver } from '../resources/not-fornecedor.resolver';
import { PrazoDescResolver } from '../resources/prazo-desc.resolver';
import { NotFornecedorComponent } from './fornecedor/not-fornecedor/not-fornecedor.component';
import { PorNotFornecedorComponent } from './fornecedor/por-not-fornecedor/por-not-fornecedor.component';
import { NotConsumidorComponent } from './not-consumidor/not-consumidor.component';
import { PorPrazoComponent } from './por-prazo/por-prazo.component';
import { RankingComponent } from './ranking/ranking.component';

export const routes: Routes = [
  {
    path: 'not_fornecedor',
    canActivate: [AuthGuard],
    children: [
      {
        path: '',
        component: PorNotFornecedorComponent,
        resolve: { processos: NotFornecedorResolver },
      },
      {
        path: ':id',
        component: NotFornecedorComponent,
        resolve: { processo: CadProcessoResolver },
      },
    ],
  },
  {
    path: 'not_consumidor',
    canActivate: [AuthGuard],
    children: [
      {
        path: '',
        component: NotConsumidorComponent,
        resolve: { processos: NotConsumidorResolver },
      },
    ],
  },
  {
    path: 'prazo',
    canActivate: [AuthGuard],
    component: PorPrazoComponent,
    resolve: { processos: PrazoDescResolver },
  },
  {
    path: 'ranking',
    canActivate: [AuthGuard],
    component: RankingComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class OperacaoRoutingModule {}
