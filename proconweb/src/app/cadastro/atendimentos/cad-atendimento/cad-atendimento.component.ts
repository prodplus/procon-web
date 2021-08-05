import {
  AfterViewInit,
  Component,
  ElementRef,
  OnInit,
  ViewChild,
} from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { faMinus } from '@fortawesome/free-solid-svg-icons';
import { Atendimento } from 'src/app/models/atendimento';
import { Consumidor } from 'src/app/models/consumidor';
import { AtendimentoForm } from 'src/app/models/forms/atendimento-form';
import { Fornecedor } from 'src/app/models/fornecedor';
import { Usuario } from 'src/app/models/usuario';
import { AtendimentoService } from 'src/app/services/atendimento.service';
import { ModalComponent } from 'src/app/shared/modal/modal.component';
import { toDateApi } from 'src/app/utils/date-utils';
import { getMascaraCadastro } from 'src/app/utils/mask-utils';

@Component({
  selector: 'app-cad-atendimento',
  templateUrl: './cad-atendimento.component.html',
  styleUrls: ['./cad-atendimento.component.css'],
})
export class CadAtendimentoComponent implements OnInit, AfterViewInit {
  isLoading = false;
  selecionandoConsumidor = false;
  selecionandoFornecedor = false;
  idConsumidor: number = null;
  idFornecedor: number = null;
  editandoCons = false;
  editandoForn = false;
  atendimento: Atendimento;
  iMinus = faMinus;
  @ViewChild('scrollConsumidor')
  scrollConsumidor: ElementRef<HTMLDivElement>;
  @ViewChild('scrollFornecedor')
  scrollFornecedor: ElementRef<HTMLDivElement>;
  @ViewChild('modal')
  modal: ModalComponent;
  @ViewChild('input')
  input: ElementRef<HTMLInputElement>;
  form = this.builder.group({
    data: [toDateApi(new Date()), [Validators.required]],
    relato: [''],
  });

  constructor(
    private atendimentoService: AtendimentoService,
    private builder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      if (params.get('id')) {
        this.atendimento = this.route.snapshot.data['atendimento'];
        this.carregaForm(this.atendimento);
      } else {
        this.atendimento = new Atendimento(
          null,
          [],
          [],
          toDateApi(new Date()),
          '',
          null
        );
      }
    });
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.input.nativeElement.focus();
    }, 100);
  }

  private carregaForm(ate: Atendimento) {
    this.form.patchValue({
      data: ate.data,
      relato: ate.relato,
    });
  }

  private carregaAtendimento(): AtendimentoForm {
    this.atendimento.data = toDateApi(new Date(this.form.get('data').value));
    this.atendimento.relato = this.form.get('relato').value;
    this.atendimento.atendente = new Usuario(1, null, null, null, null, true);
    return new AtendimentoForm(this.atendimento);
  }

  salvar() {
    this.isLoading = true;
    if (!this.atendimento.id) {
      this.atendimentoService.salvar(this.carregaAtendimento()).subscribe({
        error: (err) => {
          this.isLoading = false;
          this.modal.openPadrao(err);
        },
        complete: () => {
          this.isLoading = false;
          this.router.navigate(['/cadastro/atendimentos']);
        },
      });
    } else {
      this.atendimentoService
        .atualizar(this.atendimento.id, this.carregaAtendimento())
        .subscribe({
          error: (err) => {
            this.isLoading = false;
            this.modal.openPadrao(err);
          },
          complete: () => {
            this.isLoading = false;
            this.router.navigate(['/cadastro/atendimentos']);
          },
        });
    }
  }

  consumidorSelecionado(cons: Consumidor): number {
    this.selecionandoConsumidor = false;
    this.idConsumidor = cons ? cons.id : 0;
    this.editandoCons = true;
    return this.idConsumidor;
  }

  novoConsumidor(e: boolean) {
    this.selecionandoConsumidor = false;
    if (e) {
      this.idConsumidor = 0;
      this.editandoCons = true;
    }
  }

  mascaraCadastro(tipo: string): string {
    return getMascaraCadastro(tipo);
  }

  removerConsumidor(i: number) {
    this.atendimento.consumidores.splice(i, 1);
  }

  consumidorSalvo(cons: Consumidor) {
    this.editandoCons = false;
    if (cons) {
      if (this.atendimento.consumidores.length > 0) {
        let index = -1;
        for (let c of this.atendimento.consumidores) {
          if (c.id == cons.id) {
            index = this.atendimento.consumidores.indexOf(c);
          }
        }
        if (index > -1) {
          this.atendimento.consumidores.splice(index, 1);
        }
      }
      this.atendimento.consumidores.push(cons);
    }

    setTimeout(() => {
      this.scrollConsumidor.nativeElement.scrollIntoView({
        behavior: 'smooth',
        block: 'center',
        inline: 'center',
      });
    }, 100);
  }

  fornecedorSelecionado(forn: Fornecedor): number {
    this.selecionandoFornecedor = false;
    this.idFornecedor = forn ? forn.id : 0;
    this.editandoForn = true;
    return this.idFornecedor;
  }

  novoFornecedor(e: boolean) {
    this.selecionandoFornecedor = false;
    if (e) {
      this.idFornecedor = 0;
      this.editandoForn = true;
    }
  }

  removerFornecedor(i: number) {
    this.atendimento.fornecedores.splice(i, 1);
  }

  fornecedorSalvo(forn: Fornecedor) {
    this.editandoForn = false;
    if (forn) {
      if (this.atendimento.fornecedores.length > 0) {
        let index = -1;
        for (let f of this.atendimento.fornecedores) {
          if (f.id == forn.id) {
            index = this.atendimento.fornecedores.indexOf(f);
          }
        }
        if (index > -1) {
          this.atendimento.fornecedores.splice(index, 1);
        }
      }
      this.atendimento.fornecedores.push(forn);
    }

    setTimeout(() => {
      this.scrollFornecedor.nativeElement.scrollIntoView({
        behavior: 'smooth',
        block: 'center',
        inline: 'center',
      });
    }, 100);
  }
}
