import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { GoogleChartsModule } from 'angular-google-charts';
import { NgxMaskModule } from 'ngx-mask';
import { SharedModule } from '../shared/shared.module';
import { NotFornecedorComponent } from './fornecedor/not-fornecedor/not-fornecedor.component';
import { PorNotFornecedorComponent } from './fornecedor/por-not-fornecedor/por-not-fornecedor.component';
import { NotConsumidorComponent } from './not-consumidor/not-consumidor.component';
import { OperacaoRoutingModule } from './operacao-routing.module';

@NgModule({
  declarations: [
    PorNotFornecedorComponent,
    NotFornecedorComponent,
    NotConsumidorComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    FontAwesomeModule,
    NgxMaskModule,
    GoogleChartsModule,
    FormsModule,
    ReactiveFormsModule,
    OperacaoRoutingModule,
  ],
})
export class OperacaoModule {}
