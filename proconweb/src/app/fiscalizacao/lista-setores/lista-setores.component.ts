import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { debounceTime } from 'rxjs/operators';
import { RespModal } from 'src/app/models/auxiliares/resp-modal';
import { SetorFiscalizacao } from 'src/app/models/setor-fiscalizacao';
import { SetorFiscalizacaoService } from 'src/app/services/setor-fiscalizacao.service';
import { ModalComponent } from 'src/app/shared/modal/modal.component';
import { mensagemPadrao } from 'src/app/utils/mensagem-utils';

@Component({
  selector: 'app-lista-setores',
  templateUrl: './lista-setores.component.html',
  styleUrls: ['./lista-setores.component.css'],
})
export class ListaSetoresComponent implements OnInit, AfterViewInit {
  isLoading = false;
  setores: SetorFiscalizacao[] = [];
  idSetor!: number;
  searchForm: FormGroup;
  value: string = '';
  @ViewChild('modal')
  modal!: ModalComponent;

  constructor(
    private builder: FormBuilder,
    private setorService: SetorFiscalizacaoService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.setores = this.route.snapshot.data['setores'];

    this.searchForm = this.builder.group({
      input: [''],
    });
  }

  ngAfterViewInit(): void {
    this.searchForm
      .get('input')
      .valueChanges.pipe(debounceTime(300))
      .subscribe((value) => {
        if (value && value.length > 0) {
          this.value = value;
          this.recarregar();
        } else {
          this.value = '';
          this.recarregar();
        }
      });
  }

  private recarregar() {
    if (this.value && this.value.length > 1) {
      this.setorService.listarD(this.value).subscribe({
        next: (p) => (this.setores = p),
        error: (err) => {
          this.isLoading = false;
          this.modal.openPadrao(err);
        },
        complete: () => (this.isLoading = false),
      });
    } else {
      this.setorService.listar().subscribe({
        next: (p) => (this.setores = p),
        error: (err) => {
          this.isLoading = false;
          this.modal.openPadrao(err);
        },
        complete: () => (this.isLoading = false),
      });
    }
  }

  private excluir(id: number) {
    this.isLoading = true;
    this.setorService.excluir(id).subscribe({
      error: (err) => {
        this.isLoading = false;
        this.modal.openPadrao(err);
      },
      complete: () => {
        this.isLoading = false;
        this.recarregar();
      },
    });
  }

  chamaModal(id: number) {
    this.idSetor = id;
    this.modal.open(
      mensagemPadrao(
        null,
        'warning',
        'Atenção!!',
        'EXCLUIR definitivamente o setor??'
      ),
      'e',
      true
    );
  }

  concordaModal(resp: RespModal) {
    if (resp.confirmou) this.excluir(this.idSetor);
  }
}
