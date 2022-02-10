import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { faCheck } from '@fortawesome/free-solid-svg-icons';
import { debounceTime } from 'rxjs/operators';
import { Fornecedor } from 'src/app/models/fornecedor';
import { SetorFiscalizacao } from 'src/app/models/setor-fiscalizacao';
import { DocumentoService } from 'src/app/services/documento.service';
import { FornecedorService } from 'src/app/services/fornecedor.service';
import { SetorFiscalizacaoService } from 'src/app/services/setor-fiscalizacao.service';
import { ModalComponent } from 'src/app/shared/modal/modal.component';
import { impressaoUtils } from 'src/app/utils/impressao-utils';

@Component({
  selector: 'app-termo-visita',
  templateUrl: './termo-visita.component.html',
  styleUrls: ['./termo-visita.component.css'],
})
export class TermoVisitaComponent implements OnInit, AfterViewInit {
  isLoading = false;
  fornecedorForm: FormGroup;
  setorForm: FormGroup;
  setores: SetorFiscalizacao[] = [];
  fornecedores: Fornecedor[] = [];
  fornecedor: Fornecedor;
  iCheck = faCheck;
  @ViewChild('modal')
  modal: ModalComponent;

  constructor(
    private fornecedorService: FornecedorService,
    private setorService: SetorFiscalizacaoService,
    private builder: FormBuilder,
    private docService: DocumentoService
  ) {}

  ngOnInit(): void {
    this.setorService.listar().subscribe((s) => (this.setores = s));

    this.setorForm = this.builder.group({
      setor: [null, [Validators.required]],
    });

    this.fornecedorForm = this.builder.group({
      fornecedor: [''],
    });
  }

  ngAfterViewInit(): void {
    this.fornecedorForm
      .get('fornecedor')
      .valueChanges.pipe(debounceTime(300))
      .subscribe((value) => {
        if (value && value.length > 1) {
          this.fornecedorService
            .listarParametro(value, 0, 5)
            .subscribe((f) => (this.fornecedores = f.content));
        }
      });
  }

  limpar() {
    this.fornecedorForm.reset();
    this.setorForm.reset();
    this.fornecedores = [];
  }

  selectFornecedor(i: number) {
    this.fornecedor = this.fornecedores[i];
    this.fornecedores = [];
    this.fornecedorForm.reset();
  }

  imprimir() {
    this.isLoading = true;
    const idSetor = this.setorForm.get('setor').value;
    const idFornecedor = this.fornecedor.id;
    this.docService.termoBranco(idFornecedor, idSetor).subscribe({
      next: (x) => impressaoUtils(x, idFornecedor, 'termo'),
      error: (err) => {
        this.isLoading = false;
        this.modal.openPadrao(err);
      },
      complete: () => (this.isLoading = false),
    });
  }
}
