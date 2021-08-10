import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { faPrint } from '@fortawesome/free-solid-svg-icons';
import { Processo } from 'src/app/models/processo';
import { DocumentoService } from 'src/app/services/documento.service';
import { ModalComponent } from 'src/app/shared/modal/modal.component';

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
  }
}
