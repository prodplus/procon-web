import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { faPrint } from '@fortawesome/free-solid-svg-icons';
import { Processo } from 'src/app/models/processo';
import { DocumentoService } from 'src/app/services/documento.service';
import { ModalComponent } from 'src/app/shared/modal/modal.component';
import { impressaoUtils } from 'src/app/utils/impressao-utils';
import { getMascaraCadastro } from 'src/app/utils/mask-utils';

@Component({
  selector: 'app-not-fornecedor',
  templateUrl: './not-fornecedor.component.html',
  styleUrls: ['./not-fornecedor.component.css'],
})
export class NotFornecedorComponent implements OnInit {
  isLoading = false;
  processo: Processo;
  iPrint = faPrint;
  @ViewChild('modal')
  modal: ModalComponent;

  constructor(
    private route: ActivatedRoute,
    private docService: DocumentoService
  ) {}

  ngOnInit(): void {
    this.processo = this.route.snapshot.data['processo'];
  }

  notDezDias(idForn: number) {
    this.isLoading = true;
    this.docService.notDezDias(this.processo.id, idForn).subscribe(
      (x) => impressaoUtils(x, this.processo.id, 'not_'),
      (err) => {
        this.isLoading = false;
        this.modal.openPadrao(err);
      },
      () => (this.isLoading = false)
    );
  }

  notCincoDias(idForn: number) {
    this.isLoading = true;
    this.docService.notCincoDias(this.processo.id, idForn).subscribe(
      (x) => impressaoUtils(x, this.processo.id, 'not_'),
      (err) => {
        this.isLoading = false;
        this.modal.openPadrao(err);
      },
      () => (this.isLoading = false)
    );
  }

  notImpugnacao(idForn: number) {
    this.isLoading = true;
    this.docService.notImpugnacao(this.processo.id, idForn).subscribe(
      (x) => impressaoUtils(x, this.processo.id, 'not_'),
      (err) => {
        this.isLoading = false;
        this.modal.openPadrao(err);
      },
      () => (this.isLoading = false)
    );
  }

  notMulta(idForn: number) {
    this.isLoading = true;
    this.docService.notMulta(this.processo.id, idForn).subscribe(
      (x) => impressaoUtils(x, this.processo.id, 'not_'),
      (err) => {
        this.isLoading = false;
        this.modal.openPadrao(err);
      },
      () => (this.isLoading = false)
    );
  }

  inicial() {
    this.isLoading = true;
    this.docService.inicial(this.processo.id).subscribe(
      (x) => impressaoUtils(x, this.processo.id, 'inicial_'),
      (err) => {
        this.isLoading = false;
        this.modal.openPadrao(err);
      },
      () => (this.isLoading = false)
    );
  }

  oficio() {
    this.isLoading = true;
    this.docService.oficio(this.processo.id).subscribe(
      (x) => impressaoUtils(x, this.processo.id, 'of_'),
      (err) => {
        this.isLoading = false;
        this.modal.openPadrao(err);
      },
      () => (this.isLoading = false)
    );
  }

  mascaraCadastro(tipo: string): string {
    return getMascaraCadastro(tipo);
  }
}
