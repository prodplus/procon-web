import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { faPrint } from '@fortawesome/free-solid-svg-icons';
import { Page } from 'src/app/models/auxiliares/page';
import { ProcessoDto } from 'src/app/models/dtos/processo-dto';
import { DocumentoService } from 'src/app/services/documento.service';
import { ModalComponent } from 'src/app/shared/modal/modal.component';
import { impressaoUtils } from 'src/app/utils/impressao-utils';

@Component({
  selector: 'app-lista-proc-consumidores',
  templateUrl: './lista-proc-consumidores.component.html',
  styleUrls: ['./lista-proc-consumidores.component.css'],
})
export class ListaProcConsumidoresComponent implements OnInit {
  isLoading = false;
  page: Page<ProcessoDto>;
  pagina = 1;
  iPrint = faPrint;
  @ViewChild('modal')
  modal: ModalComponent;

  constructor(
    private docService: DocumentoService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.page = this.route.snapshot.data['page'];
  }

  getRowClas(processo: ProcessoDto): string {
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
}
