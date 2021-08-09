import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { faPrint } from '@fortawesome/free-solid-svg-icons';
import { debounceTime } from 'rxjs/operators';
import { Page } from 'src/app/models/auxiliares/page';
import { RespModal } from 'src/app/models/auxiliares/resp-modal';
import { AtendimentoDto } from 'src/app/models/dtos/atendimento-dto';
import { AtendimentoService } from 'src/app/services/atendimento.service';
import { DocumentoService } from 'src/app/services/documento.service';
import { ModalComponent } from 'src/app/shared/modal/modal.component';
import { impressaoUtils } from 'src/app/utils/impressao-utils';
import { mensagemPadrao } from 'src/app/utils/mensagem-utils';

@Component({
  selector: 'app-lista-atendimentos',
  templateUrl: './lista-atendimentos.component.html',
  styleUrls: ['./lista-atendimentos.component.css'],
})
export class ListaAtendimentosComponent implements OnInit, AfterViewInit {
  isLoading = false;
  page: Page<AtendimentoDto>;
  idAtendimento: number;
  searchForm: FormGroup;
  value: string;
  pagina = 1;
  iPrint = faPrint;
  @ViewChild('modal')
  modal: ModalComponent;

  constructor(
    private builder: FormBuilder,
    private atendimentoService: AtendimentoService,
    private route: ActivatedRoute,
    private docService: DocumentoService
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
      this.atendimentoService
        .listarParametro(this.value, pagina - 1, 20)
        .subscribe(
          (p) => (this.page = p),
          (err) => {
            this.isLoading = false;
            this.modal.openPadrao(err);
          },
          () => (this.isLoading = false)
        );
    } else {
      this.atendimentoService.listar(pagina - 1, 20).subscribe(
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
    this.isLoading = false;
    this.atendimentoService.excluir(id).subscribe({
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
    this.idAtendimento = id;
    this.modal.open(
      mensagemPadrao(
        null,
        'warning',
        'Atenção!!',
        'EXCLUIR definitivamente o atendimento??'
      ),
      'e',
      true
    );
  }

  concordaModal(resp: RespModal) {
    if (resp.confirmou) {
      this.excluir(this.idAtendimento);
    }
  }

  imprimir(id: number) {
    this.isLoading = true;
    this.docService.atendimento(id).subscribe(
      (x) => impressaoUtils(x, id, 'atendimento'),
      (err) => {
        this.isLoading = false;
        this.modal.openPadrao(err);
      },
      () => (this.isLoading = false)
    );
  }
}
