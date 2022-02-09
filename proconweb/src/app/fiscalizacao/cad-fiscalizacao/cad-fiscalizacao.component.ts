import {
  AfterViewInit,
  Component,
  ElementRef,
  OnInit,
  ViewChild,
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { faCheck } from '@fortawesome/free-solid-svg-icons';
import { debounceTime } from 'rxjs/operators';
import { ItemFiscalizacao } from 'src/app/models/auxiliares/item-fiscalizacao';
import { Page } from 'src/app/models/auxiliares/page';
import { Fiscalizacao } from 'src/app/models/fiscalizacao';
import { Fornecedor } from 'src/app/models/fornecedor';
import { SetorFiscalizacao } from 'src/app/models/setor-fiscalizacao';
import { FiscalizacaoService } from 'src/app/services/fiscalizacao.service';
import { FornecedorService } from 'src/app/services/fornecedor.service';
import { SetorFiscalizacaoService } from 'src/app/services/setor-fiscalizacao.service';
import { ModalComponent } from 'src/app/shared/modal/modal.component';
import { toDateTimeApi } from 'src/app/utils/date-utils';

@Component({
  selector: 'app-cad-fiscalizacao',
  templateUrl: './cad-fiscalizacao.component.html',
  styleUrls: ['./cad-fiscalizacao.component.css'],
})
export class CadFiscalizacaoComponent implements OnInit, AfterViewInit {
  isLoading = false;
  idFornecedor: number = null;
  fiscalizacao: Fiscalizacao;
  setores: SetorFiscalizacao[] = [];
  fornecedores: Page<Fornecedor>;
  searchValue: string = '';
  searchForm: FormGroup;
  iCheck = faCheck;
  @ViewChild('modal')
  modal: ModalComponent;
  @ViewChild('input')
  input: ElementRef<HTMLInputElement>;
  form: FormGroup;

  constructor(
    private service: FiscalizacaoService,
    private setorService: SetorFiscalizacaoService,
    private fornecedorService: FornecedorService,
    private builder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.setorService.listar().subscribe((s) => (this.setores = s));

    this.form = this.builder.group({
      data: ['', [Validators.required]],
      fornecedor: [null],
      setor: [null, [Validators.required]],
      observacoes: [''],
    });

    this.searchForm = this.builder.group({
      input: [''],
    });

    this.route.paramMap.subscribe((params) => {
      if (params.get('id')) {
        this.fiscalizacao = this.route.snapshot.data['fiscalizacao'];
        this.carregaForm(this.fiscalizacao);
      } else {
        this.fiscalizacao = new Fiscalizacao(
          null,
          toDateTimeApi(new Date()),
          null,
          '',
          null,
          []
        );
      }
    });
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.input.nativeElement.focus();
    }, 100);

    this.searchForm
      .get('input')
      .valueChanges.pipe(debounceTime(300))
      .subscribe((value) => {
        if (value && value.length > 0) {
          this.fornecedorService
            .listarParametro(value, 0, 5)
            .subscribe((f) => (this.fornecedores = f));
        } else {
          this.fornecedores = new Page();
        }
      });
  }

  private carregaForm(fisc: Fiscalizacao) {
    this.form.patchValue({
      data: fisc.data,
      fornecedor: fisc.fornecedor.id,
      setor: fisc.setor.id,
      observacoes: fisc.observacoes,
    });
    this.createFormControls(fisc.setor.itens);
  }

  onSelectSetor() {
    const id = this.form.get('setor').value;
    for (let i of this.setores) {
      if (i.id == id) {
        this.fiscalizacao.setor = i;
        this.fiscalizacao.itens = [];
        for (let a of i.itens) {
          this.fiscalizacao.itens.push(new ItemFiscalizacao(a, ''));
        }
        this.createFormControls(i.itens);
      }
    }
  }

  private createFormControls(itens: string[]) {
    for (let i = 0; i <= itens.length; i++) {
      if (this.fiscalizacao?.itens?.length > 0)
        this.form.addControl(
          'input' + i,
          this.builder.control(this.fiscalizacao.itens[i].observacao)
        );
      else this.form.addControl('input' + i, this.builder.control(''));
    }
  }

  private carregaFiscalizacao(): Fiscalizacao {
    return new Fiscalizacao(
      this.fiscalizacao.id ? this.fiscalizacao.id : null,
      toDateTimeApi(new Date(this.form.get('data').value)),
      this.fiscalizacao.fornecedor,
      this.form.get('observacoes').value,
      this.fiscalizacao.setor,
      this.carregaItens()
    );
  }

  private carregaItens(): ItemFiscalizacao[] {
    let itensR: ItemFiscalizacao[] = [];
    const itensP: string[] = this.fiscalizacao.setor.itens;
    for (let i = 0; i < itensP.length; i++) {
      itensR.push(
        new ItemFiscalizacao(itensP[i], this.form.get('input' + i).value)
      );
    }
    return itensR;
  }

  onSelectForn(id: number) {
    this.fornecedorService.buscar(id).subscribe({
      next: (f) => (this.fiscalizacao.fornecedor = f),
      error: (err) => this.modal.openPadrao(err),
      complete: () => {
        this.searchForm.reset();
      },
    });
  }

  salvar() {
    if (this.fiscalizacao?.id) {
      this.service
        .atualizar(this.fiscalizacao.id, this.carregaFiscalizacao())
        .subscribe({
          error: (err) => {
            this.isLoading = false;
            this.modal.openPadrao(err);
          },
          complete: () => {
            this.isLoading = false;
            this.router.navigate(['/fiscalizacao/visita']);
          },
        });
    } else {
      this.service.salvar(this.carregaFiscalizacao()).subscribe({
        error: (err) => {
          this.isLoading = false;
          this.modal.openPadrao(err);
        },
        complete: () => {
          this.isLoading = false;
          this.router.navigate(['/fiscalizacao/visita']);
        },
      });
    }
  }
}
