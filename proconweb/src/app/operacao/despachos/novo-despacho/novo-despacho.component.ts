import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { faFile, faPrint } from '@fortawesome/free-solid-svg-icons';
import { debounceTime } from 'rxjs/operators';
import { RespModal } from 'src/app/models/auxiliares/resp-modal';
import { ProcessoDto } from 'src/app/models/dtos/processo-dto';
import { DocumentoService } from 'src/app/services/documento.service';
import { OperacaoService } from 'src/app/services/operacao.service';
import { ModalComponent } from 'src/app/shared/modal/modal.component';
import { impressaoUtils } from 'src/app/utils/impressao-utils';
import { mensagemPadrao } from 'src/app/utils/mensagem-utils';

@Component({
  selector: 'app-novo-despacho',
  templateUrl: './novo-despacho.component.html',
  styleUrls: ['./novo-despacho.component.css'],
})
export class NovoDespachoComponent implements OnInit, AfterViewInit {
  isLoading = false;
  processos: ProcessoDto[];
  idProcesso: number;
  iPrint = faPrint;
  iFile = faFile;
  formSearch = this.builder.group({
    input: [''],
  });
  autos: string;
  @ViewChild('modal')
  modal: ModalComponent;

  constructor(
    private route: ActivatedRoute,
    private docService: DocumentoService,
    private builder: FormBuilder,
    private opService: OperacaoService
  ) {}

  ngOnInit(): void {
    this.processos = this.route.snapshot.data['processos'];
  }

  ngAfterViewInit(): void {
    this.formSearch
      .get('input')
      .valueChanges.pipe(debounceTime(300))
      .subscribe(
        (value) => {
          this.isLoading = true;
          if (value && value.length > 0) this.autos = value;
          else this.autos = null;
          this.recarregar();
        },
        (err) => {
          this.isLoading = false;
          this.modal.openPadrao(err);
        },
        () => (this.isLoading = false)
      );
  }

  private recarregar() {
    if (this.autos) {
      this.opService.porNovosAutos(this.autos).subscribe(
        (p) => (this.processos = p),
        (err) => {
          this.isLoading = false;
          this.modal.openPadrao(err);
        },
        () => (this.isLoading = false)
      );
    } else {
      this.opService.porNovos().subscribe(
        (p) => (this.processos = p),
        (err) => {
          this.isLoading = false;
          this.modal.openPadrao(err);
        },
        () => (this.isLoading = false)
      );
    }
  }

  private imprimir(id: number) {
    this.isLoading = true;
    this.docService.despachoNot(id).subscribe(
      (x) => impressaoUtils(x, id, 'desp_of'),
      (err) => {
        this.isLoading = false;
        this.modal.openPadrao(err);
      },
      () => this.recarregar()
    );
  }

  preparaOficio(id: number) {
    this.idProcesso = id;
    this.modal.open(
      mensagemPadrao(null, 'warning', 'Atenção!!', 'Confirmar Notificação??'),
      'e',
      true
    );
  }

  concordou(resp: RespModal) {
    if (resp.confirmou) this.imprimir(this.idProcesso);
  }
}
