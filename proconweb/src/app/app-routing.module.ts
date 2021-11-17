import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  {
    path: 'cadastro',
    loadChildren: () =>
      import('./cadastro/cadastro.module').then((m) => m.CadastroModule),
  },
  {
    path: 'operacao',
    loadChildren: () =>
      import('./operacao/operacao.module').then((m) => m.OperacaoModule),
  },
  {
    path: 'relatorio',
    loadChildren: () =>
      import('./relatorios/relatorios.module').then((m) => m.RelatoriosModule),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
