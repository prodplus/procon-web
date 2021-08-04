import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgxViacepModule } from '@brunoc/ngx-viacep';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxMaskModule } from 'ngx-mask';
import { SharedModule } from '../shared/shared.module';
import { CadastroRoutingModule } from './cadastro-routing.module';
import { CadConsumidorComponent } from './consumidores/cad-consumidor/cad-consumidor.component';
import { ListaConsumidoresComponent } from './consumidores/lista-consumidores/lista-consumidores.component';
import { ListaFornecedoresComponent } from './fornecedores/lista-fornecedores/lista-fornecedores.component';
import { CadFornecedoresComponent } from './fornecedores/cad-fornecedores/cad-fornecedores.component';

@NgModule({
  declarations: [ListaConsumidoresComponent, CadConsumidorComponent, ListaFornecedoresComponent, CadFornecedoresComponent],
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
