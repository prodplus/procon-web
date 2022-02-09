import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { SharedModule } from '../shared/shared.module';
import { CadFiscalizacaoComponent } from './cad-fiscalizacao/cad-fiscalizacao.component';
import { CadSetorComponent } from './cad-setor/cad-setor.component';
import { FiscalizacaoRoutingModule } from './fiscalizacao-routing.module';
import { ListaFiscalizacoesComponent } from './lista-fiscalizacoes/lista-fiscalizacoes.component';
import { ListaSetoresComponent } from './lista-setores/lista-setores.component';

@NgModule({
  declarations: [
    ListaSetoresComponent,
    CadSetorComponent,
    ListaFiscalizacoesComponent,
    CadFiscalizacaoComponent,
  ],
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
