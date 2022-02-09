import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { faPrint } from '@fortawesome/free-solid-svg-icons';
import { debounceTime } from 'rxjs/operators';
import { Page } from 'src/app/models/auxiliares/page';
import { RespModal } from 'src/app/models/auxiliares/resp-modal';
import { Fiscalizacao } from 'src/app/models/fiscalizacao';
import { FiscalizacaoService } from 'src/app/services/fiscalizacao.service';
import { ModalComponent } from 'src/app/shared/modal/modal.component';
import { mensagemPadrao } from 'src/app/utils/mensagem-utils';

@Component({
  selector: 'app-lista-fiscalizacoes',
  templateUrl: './lista-fiscalizacoes.component.html',
  styleUrls: ['./lista-fiscalizacoes.component.css'],
})
export class ListaFiscalizacoesComponent implements OnInit, AfterViewInit {
  isLoading = false;
  page: Page<Fiscalizacao>;
  idFiscalizacao: number;
  searchForm: FormGroup;
  value: string;
  pagina = 1;
  iPrint = faPrint;
  @ViewChild('modal')
  modal: ModalComponent;

  constructor(
    private builder: FormBuilder,
    private fiscalizacaoService: FiscalizacaoService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.page = this.route.snapshot.data['page'];
    this.searchForm = this.builder.group({
      input: [''],
    });
  }

  ngAfterViewInit(): void {
    this.searchForm
      .get('input')
      .valueChanges.pipe(debounceTime(300))
      .subscribe((value) => {
        if (value) this.value = value;
        else this.value = null;
        this.pagina = 1;
        this.recarregar(this.pagina);
      });
  }

  private recarregar(pagina: number) {
    if (this.value) {
      this.fiscalizacaoService.listarR(this.value, pagina - 1, 20).subscribe({
        next: (p) => (this.page = p),
        error: (err) => {
          this.isLoading = false;
          this.modal.openPadrao(err);
        },
        complete: () => (this.isLoading = false),
      });
    } else {
      this.fiscalizacaoService.listar(pagina - 1, 20).subscribe({
        next: (p) => (this.page = p),
        error: (err) => {
          this.isLoading = false;
          this.modal.openPadrao(err);
        },
        complete: () => (this.isLoading = false),
      });
    }
  }

  mudaPagina(pagina: number) {
    this.pagina = pagina;
    this.recarregar(this.pagina);
  }

  private excluir(id: number) {
    this.isLoading = true;
    this.fiscalizacaoService.excluir(id).subscribe({
      error: (err) => {
        this.isLoading = false;
        this.modal.openPadrao(err);
      },
      complete: () => {
        this.isLoading = false;
        this.recarregar(this.pagina);
      },
    });
  }

  chamaModal(id: number) {
    this.idFiscalizacao = id;
    this.modal.open(
      mensagemPadrao(
        null,
        'warning',
        'Atenção!!',
        'EXCLUIR definitivamente a fiscalização??'
      ),
      'e',
      true
    );
  }

  concordaModal(resp: RespModal) {
    if (resp.confirmou) this.excluir(this.idFiscalizacao);
  }
}
