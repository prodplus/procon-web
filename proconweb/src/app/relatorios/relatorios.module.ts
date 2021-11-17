import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { GoogleChartsModule } from 'angular-google-charts';
import { NgxMaskModule } from 'ngx-mask';
import { SharedModule } from '../shared/shared.module';
import { AudienciaComponent } from './audiencia/audiencia.component';
import { MovprocComponent } from './movproc/movproc.component';
import { RankingComponent } from './ranking/ranking.component';
import { RelatoriosRoutingModule } from './relatorios-routing.module';
import { RelatoriosComponent } from './relatorios/relatorios.component';

@NgModule({
  declarations: [
    AudienciaComponent,
    RankingComponent,
    RelatoriosComponent,
    MovprocComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    FontAwesomeModule,
    NgxMaskModule,
    GoogleChartsModule,
    FormsModule,
    ReactiveFormsModule,
    RelatoriosRoutingModule,
  ],
})
export class RelatoriosModule {}
