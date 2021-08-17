import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { faPrint } from '@fortawesome/free-solid-svg-icons';
import { Movimento } from 'src/app/models/auxiliares/movimento';
import { RespModal } from 'src/app/models/auxiliares/resp-modal';
import { Processo } from 'src/app/models/processo';
import { DocumentoService } from 'src/app/services/documento.service';
import { ModalComponent } from 'src/app/shared/modal/modal.component';
import { toDateApi } from 'src/app/utils/date-utils';
import { impressaoUtils } from 'src/app/utils/impressao-utils';
import { mensagemPadrao } from 'src/app/utils/mensagem-utils';

@Component({
  selector: 'app-marca-audiencia',
  templateUrl: './marca-audiencia.component.html',
  styleUrls: ['./marca-audiencia.component.css'],
})
export class MarcaAudienciaComponent implements OnInit {
  isLoading = false;
  processo: Processo;
  movimentoForm = this.builder.group({
    data: [null, [Validators.required]],
    hora: [null, [Validators.required]],
  });
  iPrint = faPrint;
  @ViewChild('modal')
  modal: ModalComponent;

  constructor(
    private route: ActivatedRoute,
    private builder: FormBuilder,
    private docService: DocumentoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.processo = this.route.snapshot.data['processo'];
  }

  private carregaMovimento(): Movimento {
    return new Movimento(
      toDateApi(new Date()),
      'DESPACHO',
      'AUDIENCIA',
      '',
      toDateApi(new Date(this.movimentoForm.get('data').value)),
      this.movimentoForm.get('hora').value
    );
  }

  private imprimir() {
    this.isLoading = true;
    this.docService
      .despachoAud(this.processo.id, this.carregaMovimento())
      .subscribe(
        (x) => impressaoUtils(x, this.processo.id, 'desp_aud'),
        (err) => {
          this.isLoading = false;
          this.modal.openPadrao(err);
        },
        () => {
          this.isLoading = false;
          this.router.navigate(['/operacao/despachos']);
        }
      );
  }

  preparaDespacho() {
    this.modal.open(
      mensagemPadrao(
        null,
        'warning',
        'Atenção!!',
        'Tem certeza que deseja marcar a Audiência?'
      ),
      'e',
      true
    );
  }

  concordou(resp: RespModal) {
    if (resp.confirmou) this.imprimir();
  }
}
