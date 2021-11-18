import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminGuard } from '../core/auth/admin.guard';
import { AuthGuard } from '../core/auth/auth.guard';
import { AudienciaResolver } from '../resources/audiencia.resolver';
import { AudienciaComponent } from './audiencia/audiencia.component';
import { MovprocComponent } from './movproc/movproc.component';
import { RankingAComponent } from './ranking-a/ranking-a.component';
import { RankingComponent } from './ranking/ranking.component';
import { RelatoriosComponent } from './relatorios/relatorios.component';

export const routes: Routes = [
  {
    path: 'audiencias',
    canActivate: [AuthGuard],
    component: AudienciaComponent,
    resolve: { audiencias: AudienciaResolver },
  },
  {
    path: 'ranking',
    canActivate: [AuthGuard],
    component: RankingComponent,
  },
  {
    path: 'relatorios',
    canActivate: [AuthGuard],
    component: RelatoriosComponent,
  },
  {
    path: 'movimentos',
    canActivate: [AdminGuard],
    component: MovprocComponent,
  },
  {
    path: 'rankinga',
    canActivate: [AuthGuard],
    component: RankingAComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class RelatoriosRoutingModule {}
