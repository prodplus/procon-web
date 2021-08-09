import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { faPrint } from '@fortawesome/free-solid-svg-icons';
import { debounceTime } from 'rxjs/operators';
import { Page } from 'src/app/models/auxiliares/page';
import { RespModal } from 'src/app/models/auxiliares/resp-modal';
import { ProcessoDto } from 'src/app/models/dtos/processo-dto';
import { DocumentoService } from 'src/app/services/documento.service';
import { ProcessoService } from 'src/app/services/processo.service';
import { ModalComponent } from 'src/app/shared/modal/modal.component';
import { impressaoUtils } from 'src/app/utils/impressao-utils';
import { mensagemPadrao } from 'src/app/utils/mensagem-utils';

@Component({
  selector: 'app-lista-processos',
  templateUrl: './lista-processos.component.html',
  styleUrls: ['./lista-processos.component.css'],
})
export class ListaProcessosComponent implements OnInit, AfterViewInit {
  isLoading = false;
  page: Page<ProcessoDto>;
  idProcesso: number;
  searchAutos: FormGroup;
  searchCons: FormGroup;
  searchForn: FormGroup;
  valueAutos: string;
  valueCons: string;
  valueForn: string;
  pagina = 1;
  iPrint = faPrint;
  @ViewChild('modal')
  modal: ModalComponent;

  constructor(
    private builder: FormBuilder,
    private processoService: ProcessoService,
    private route: ActivatedRoute,
    private docService: DocumentoService
  ) {}

  ngOnInit(): void {
    this.page = this.route.snapshot.data['page'];

    this.searchAutos = this.builder.group({
      input: [''],
    });
    this.searchCons = this.builder.group({
      input: [''],
    });
    this.searchForn = this.builder.group({
      input: [''],
    });
  }

  ngAfterViewInit(): void {
    this.searchAutos
      .get('input')
      .valueChanges.pipe(debounceTime(300))
      .subscribe((value) => {
        if (value && value.length > 2) this.valueAutos = value;
        else this.valueAutos = null;
        this.pagina = 1;
        this.recarregar(this.pagina);
      });
    this.searchCons
      .get('input')
      .valueChanges.pipe(debounceTime(300))
      .subscribe((value) => {
        if (value && value.length > 2) this.valueCons = value;
        else this.valueCons = null;
        this.pagina = 1;
        this.recarregar(this.pagina);
      });
    this.searchForn
      .get('input')
      .valueChanges.pipe(debounceTime(300))
      .subscribe((value) => {
        if (value && value.length > 2) this.valueForn = value;
        else this.valueForn = null;
        this.pagina = 1;
        this.recarregar(this.pagina);
      });
  }

  private recarregar(pagina: number) {
    if (this.valueAutos) {
      this.processoService
        .listarPorAutos(this.valueAutos, pagina - 1, 20)
        .subscribe(
          (p) => (this.page = p),
          (err) => {
            this.isLoading = false;
            this.modal.openPadrao(err);
          },
          () => (this.isLoading = false)
        );
    } else if (this.valueCons) {
      this.processoService
        .listarPorConsumidor(this.valueCons, pagina - 1, 20)
        .subscribe(
          (p) => (this.page = p),
          (err) => {
            this.isLoading = false;
            this.modal.openPadrao(err);
          },
          () => (this.isLoading = false)
        );
    } else if (this.valueForn) {
      this.processoService
        .listarPorFornecedor(this.valueForn, pagina - 1, 20)
        .subscribe(
          (p) => (this.page = p),
          (err) => {
            this.isLoading = false;
            this.modal.openPadrao(err);
          },
          () => (this.isLoading = false)
        );
    } else {
      this.processoService.listar(pagina - 1, 20).subscribe(
        (p) => (this.page = p),
        (err) => {
          this.isLoading = false;
          this.modal.openPadrao(err);
        },
        () => (this.isLoading = false)
      );
    }
  }

  mudaPagina(pagina: number) {
    this.pagina = pagina;
    this.recarregar(this.pagina);
  }

  private excluir(id: number) {
    this.isLoading = true;
    this.processoService.excluir(id).subscribe({
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
    this.idProcesso = id;
    this.modal.open(
      mensagemPadrao(
        null,
        'warning',
        'Atenção!!',
        'EXCLUIR definitivamente o processo??'
      ),
      'e',
      true
    );
  }

  concordaModal(resp: RespModal) {
    if (resp.confirmou) {
      this.excluir(this.idProcesso);
    }
  }

  imprimir(id: number) {
    this.isLoading = true;
    this.docService.inicial(id).subscribe(
      (x) => impressaoUtils(x, id, 'atendimento'),
      (err) => {
        this.isLoading = false;
        this.modal.openPadrao(err);
      },
      () => (this.isLoading = false)
    );
  }

  getRowClass(processo: ProcessoDto): string {
    switch (processo.situacao) {
      case 'AUTUADO':
        return 'table-info';
      case 'CONCLUSO':
        return 'table-success';
      case 'AUDIENCIA':
        return 'table-warning';
      case 'NOTIFICAR_FORNECEDOR':
      case 'NOTIFICAR_CONSUMIDOR':
      case 'PRAZO':
      case 'PRAZO_CONSUMIDOR':
      case 'PRAZO_FORNECEDOR':
        return 'table-danger';
      case 'ENCERRADO':
      case 'RESOLVIDO':
      case 'NAO_RESOLVIDO':
      case 'INSUBSISTENTE':
        return 'table-secondary';
      default:
        return 'table-light';
    }
  }
}
