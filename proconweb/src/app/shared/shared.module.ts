import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgxViacepModule } from '@brunoc/ngx-viacep';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { GoogleChartsModule } from 'angular-google-charts';
import { NgxMaskModule } from 'ngx-mask';
import { BotaoAtivarCtrlComponent } from './botao-ativar-ctrl/botao-ativar-ctrl.component';
import { BotaoAtivarComponent } from './botao-ativar/botao-ativar.component';
import { BotaoCancelarExtComponent } from './botao-cancelar-ext/botao-cancelar-ext.component';
import { BotaoCancelarComponent } from './botao-cancelar/botao-cancelar.component';
import { BotaoEditarComponent } from './botao-editar/botao-editar.component';
import { BotaoExcluirComponent } from './botao-excluir/botao-excluir.component';
import { BotaoHomeComponent } from './botao-home/botao-home.component';
import { BotaoNovoComponent } from './botao-novo/botao-novo.component';
import { CadEnderecoComponent } from './cad-endereco/cad-endereco.component';
import { CadFoneComponent } from './cad-fone/cad-fone.component';
import { ControleAtivosComponent } from './controle-ativos/controle-ativos.component';
import { ControleComponent } from './controle/controle.component';
import { ModalComponent } from './modal/modal.component';
import { PaginaComponent } from './pagina/pagina.component';
import { PaginadorComponent } from './paginador/paginador.component';
import { SearchComboComponent } from './search-combo/search-combo.component';
import { SearchInputComponent } from './search-input/search-input.component';
import { SpinnerComponent } from './spinner/spinner.component';
import { TabelaComponent } from './tabela/tabela.component';
import { TituloComponent } from './titulo/titulo.component';

@NgModule({
  declarations: [
    CadEnderecoComponent,
    BotaoAtivarCtrlComponent,
    BotaoAtivarComponent,
    BotaoCancelarExtComponent,
    BotaoEditarComponent,
    BotaoExcluirComponent,
    BotaoHomeComponent,
    BotaoNovoComponent,
    CadFoneComponent,
    ControleAtivosComponent,
    ControleComponent,
    ModalComponent,
    PaginaComponent,
    SpinnerComponent,
    PaginadorComponent,
    SearchComboComponent,
    SearchInputComponent,
    TabelaComponent,
    TituloComponent,
    BotaoCancelarComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    FontAwesomeModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule,
    NgxViacepModule,
    NgxMaskModule,
    GoogleChartsModule,
  ],
  exports: [
    CadEnderecoComponent,
    BotaoAtivarCtrlComponent,
    BotaoAtivarComponent,
    BotaoCancelarExtComponent,
    BotaoEditarComponent,
    BotaoExcluirComponent,
    BotaoHomeComponent,
    BotaoNovoComponent,
    CadFoneComponent,
    ControleAtivosComponent,
    ControleComponent,
    ModalComponent,
    PaginaComponent,
    SpinnerComponent,
    PaginadorComponent,
    SearchComboComponent,
    SearchInputComponent,
    TabelaComponent,
    TituloComponent,
    BotaoCancelarComponent,
  ],
})
export class SharedModule {}
