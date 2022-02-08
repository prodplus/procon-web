import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { SharedModule } from '../shared/shared.module';
import { CadSetorComponent } from './cad-setor/cad-setor.component';
import { FiscalizacaoRoutingModule } from './fiscalizacao-routing.module';
import { ListaSetoresComponent } from './lista-setores/lista-setores.component';

@NgModule({
  declarations: [ListaSetoresComponent, CadSetorComponent],
  imports: [
    CommonModule,
    FiscalizacaoRoutingModule,
    SharedModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    FontAwesomeModule,
  ],
})
export class FiscalizacaoModule {}
