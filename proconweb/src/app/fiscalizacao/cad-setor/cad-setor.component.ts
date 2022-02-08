import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { faTrash } from '@fortawesome/free-solid-svg-icons';
import { SetorFiscalizacao } from 'src/app/models/setor-fiscalizacao';
import { SetorFiscalizacaoService } from 'src/app/services/setor-fiscalizacao.service';
import { ModalComponent } from 'src/app/shared/modal/modal.component';

@Component({
  selector: 'app-cad-setor',
  templateUrl: './cad-setor.component.html',
  styleUrls: ['./cad-setor.component.css'],
})
export class CadSetorComponent implements OnInit {
  isLoading = false;
  form!: FormGroup;
  itemForm!: FormGroup;
  setor: SetorFiscalizacao;
  itens: string[] = [];
  iTrash = faTrash;
  @ViewChild('modal')
  modal!: ModalComponent;

  constructor(
    private setorService: SetorFiscalizacaoService,
    private builder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.builder.group({
      descricao: ['', [Validators.required]],
    });

    this.itemForm = this.builder.group({
      descricao: ['', [Validators.required]],
    });

    this.route.paramMap.subscribe((values) => {
      if (values && values.get('id')) {
        this.setor = this.route.snapshot.data['setor'];
        this.itens = this.setor.itens;
        this.carregaFormulario(this.setor);
      }
    });
  }

  private carregaFormulario(s: SetorFiscalizacao) {
    this.form.patchValue({
      descricao: s.descricao,
    });
  }

  private carregaSetor(): SetorFiscalizacao {
    return new SetorFiscalizacao(
      this.setor?.id ? this.setor.id : null,
      this.form.get('descricao').value,
      this.itens
    );
  }

  salvar() {
    if (this.setor?.id) {
      this.setorService
        .atualizar(this.setor.id, this.carregaSetor())
        .subscribe({
          error: (err) => {
            this.isLoading = false;
            this.modal.openPadrao(err);
          },
          complete: () => {
            this.isLoading = false;
            this.router.navigate(['/fiscalizacao/setor']);
          },
        });
    } else {
      this.setorService.salvar(this.carregaSetor()).subscribe({
        error: (err) => {
          this.isLoading = false;
          this.modal.openPadrao(err);
        },
        complete: () => {
          this.isLoading = false;
          this.router.navigate(['/fiscalizacao/setor']);
        },
      });
    }
  }

  inserirItem() {
    const item = this.itemForm.get('descricao').value;
    this.itens.push(item);
    this.itemForm.reset();
  }

  excluirItem(i: number) {
    this.itens.splice(i, 1);
  }
}
