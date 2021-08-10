import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../core/auth/auth.guard';
import { PorNotFornecedorComponent } from './fornecedor/por-not-fornecedor/por-not-fornecedor.component';

export const routes: Routes = [
  {
    path: 'not_fornecedor',
    canActivate: [AuthGuard],
    children: [{ path: '', component: PorNotFornecedorComponent }],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class OperacaoRoutingModule {}
