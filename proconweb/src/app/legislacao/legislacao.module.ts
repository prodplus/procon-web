import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxMaskModule } from 'ngx-mask';
import { SharedModule } from '../shared/shared.module';
import { CadLegislaComponent } from './cad-legisla/cad-legisla.component';
import { LegislacaoRoutingModule } from './legislacao-routing.module';
import { ListaLeisComponent } from './lista-leis/lista-leis.component';

@NgModule({
  declarations: [CadLegislaComponent, ListaLeisComponent],
  imports: [
    CommonModule,
    LegislacaoRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    FontAwesomeModule,
    RouterModule,
    NgxMaskModule,
    NgbModule,
  ],
})
export class LegislacaoModule {}
