import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../core/auth/auth.guard';
import { CadProcessoResolver } from '../resources/cad-processo.resolver';
import { NotFornecedorResolver } from '../resources/not-fornecedor.resolver';
import { NotFornecedorComponent } from './fornecedor/not-fornecedor/not-fornecedor.component';
import { PorNotFornecedorComponent } from './fornecedor/por-not-fornecedor/por-not-fornecedor.component';

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
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class OperacaoRoutingModule {}
