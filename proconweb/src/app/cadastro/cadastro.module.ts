import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgxViacepModule } from '@brunoc/ngx-viacep';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxMaskModule } from 'ngx-mask';
import { SharedModule } from '../shared/shared.module';
import { CadAtendimentoComponent } from './atendimentos/cad-atendimento/cad-atendimento.component';
import { ListaAtendimentosComponent } from './atendimentos/lista-atendimentos/lista-atendimentos.component';
import { CadastroRoutingModule } from './cadastro-routing.module';
import { CadConsumidorComponent } from './consumidores/cad-consumidor/cad-consumidor.component';
import { ListaConsumidoresComponent } from './consumidores/lista-consumidores/lista-consumidores.component';
import { ListaProcConsumidoresComponent } from './consumidores/lista-proc-consumidores/lista-proc-consumidores.component';
import { CadFornecedoresComponent } from './fornecedores/cad-fornecedores/cad-fornecedores.component';
import { ListaFornecedoresComponent } from './fornecedores/lista-fornecedores/lista-fornecedores.component';
import { CadProcessoComponent } from './processos/cad-processo/cad-processo.component';
import { ListaProcessosComponent } from './processos/lista-processos/lista-processos.component';
import { CadUsuarioComponent } from './usuarios/cad-usuario/cad-usuario.component';
import { ListaUsuariosComponent } from './usuarios/lista-usuarios/lista-usuarios.component';

@NgModule({
  declarations: [
    ListaConsumidoresComponent,
    CadConsumidorComponent,
    ListaFornecedoresComponent,
    CadFornecedoresComponent,
    ListaUsuariosComponent,
    CadUsuarioComponent,
    ListaAtendimentosComponent,
    CadAtendimentoComponent,
    CadProcessoComponent,
    ListaProcessosComponent,
    ListaProcConsumidoresComponent,
  ],
  imports: [
    CommonModule,
    CadastroRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    FontAwesomeModule,
    RouterModule,
    NgxViacepModule,
    NgxMaskModule,
    NgbModule,
  ],
})
export class CadastroModule {}
