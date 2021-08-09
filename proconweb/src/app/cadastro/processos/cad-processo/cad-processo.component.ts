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
import { Movimento } from 'src/app/models/auxiliares/movimento';
import { Consumidor } from 'src/app/models/consumidor';
import { ProcessoForm } from 'src/app/models/forms/processo-form';
import { Fornecedor } from 'src/app/models/fornecedor';
import { Processo } from 'src/app/models/processo';
import { Usuario } from 'src/app/models/usuario';
import { EnumService } from 'src/app/services/enum.service';
import { ProcessoService } from 'src/app/services/processo.service';
import { ModalComponent } from 'src/app/shared/modal/modal.component';
import { toDateApi } from 'src/app/utils/date-utils';
import { getMascaraCadastro } from 'src/app/utils/mask-utils';

@Component({
  selector: 'app-cad-processo',
  templateUrl: './cad-processo.component.html',
  styleUrls: ['./cad-processo.component.css'],
})
export class CadProcessoComponent implements OnInit, AfterViewInit {
  isLoading = false;
  selecionandoConsumidor = false;
  selecionandoRepresentante = false;
  selecionandoFornecedor = false;
  lancandoMov = false;
  idConsumidor: number = null;
  idRepresentante: number = null;
  idFornecedor: number = null;
  editandoCons = false;
  editandoRep = false;
  editandoForn = false;
  processo: Processo;
  iMinus = faMinus;
  @ViewChild('scrollConsumidor')
  scrollConsumidor: ElementRef<HTMLDivElement>;
  @ViewChild('scrollRepresentante')
  scrollRepresentante: ElementRef<HTMLDivElement>;
  @ViewChild('scrollFornecedor')
  scrollFornecedor: ElementRef<HTMLDivElement>;
  @ViewChild('modal')
  modal: ModalComponent;
  @ViewChild('input')
  input: ElementRef<HTMLInputElement>;
  tipos: string[];
  form = this.builder.group({
    data: [toDateApi(new Date()), [Validators.required]],
    autos: [
      '',
      [
        Validators.minLength(8),
        Validators.pattern('^[0-9]{3,4}/20[0-9][0-9]$'),
      ],
    ],
    tipo: ['RECLAMACAO', [Validators.required]],
    relato: [''],
  });

  constructor(
    private processoService: ProcessoService,
    private builder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private enumService: EnumService
  ) {}

  ngOnInit(): void {
    this.enumService.getTiposProcesso().subscribe((t) => (this.tipos = t));

    this.route.paramMap.subscribe((params) => {
      if (params.get('id')) {
        this.processo = this.route.snapshot.data['processo'];
        this.carregaForm(this.processo);
      } else {
        this.processo = new Processo(
          null,
          'RECLAMACAO',
          null,
          [],
          [],
          [],
          toDateApi(new Date()),
          [],
          '',
          null,
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

  private carregaForm(proc: Processo) {
    this.form.patchValue({
      data: proc.data,
      autos: proc.autos,
      tipo: proc.tipo,
      relato: proc.relato,
    });
  }

  private carregaProcesso(): ProcessoForm {
    this.processo.data = toDateApi(new Date(this.form.get('data').value));
    this.processo.autos = this.form.get('autos').value;
    this.processo.tipo = this.form.get('tipo').value;
    this.processo.relato = this.form.get('relato').value;
    this.processo.atendente = new Usuario(1, null, null, null, null, true);
    return new ProcessoForm(this.processo);
  }

  salvar() {
    this.isLoading = true;
    if (!this.processo.id) {
      this.processoService.salvar(this.carregaProcesso()).subscribe({
        error: (err) => {
          this.isLoading = false;
          this.modal.openPadrao(err);
        },
        complete: () => {
          this.isLoading = false;
          this.router.navigate(['/cadastro/processos']);
        },
      });
    } else {
      this.processoService
        .atualizar(this.processo.id, this.carregaProcesso())
        .subscribe({
          error: (err) => {
            this.isLoading = false;
            this.modal.openPadrao(err);
          },
          complete: () => {
            this.isLoading = false;
            this.router.navigate(['/cadastro/processos']);
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
    this.processo.consumidores.splice(i, 1);
  }

  consumidorSalvo(cons: Consumidor) {
    this.editandoCons = false;
    if (cons) {
      if (this.processo.consumidores.length > 0) {
        let index = -1;
        for (let c of this.processo.consumidores) {
          if (c.id == cons.id) index = this.processo.consumidores.indexOf(c);
        }
        if (index > -1) this.processo.consumidores.splice(index, 1);
      }
      this.processo.consumidores.push(cons);
    }

    setTimeout(() => {
      this.scrollConsumidor.nativeElement.scrollIntoView({
        behavior: 'smooth',
        block: 'center',
        inline: 'center',
      });
    }, 100);
  }

  representanteSelecionado(rep: Consumidor): number {
    this.selecionandoRepresentante = false;
    this.idRepresentante = rep ? rep.id : 0;
    this.editandoRep = true;
    return this.idRepresentante;
  }

  novoRepresentante(e: boolean) {
    this.selecionandoRepresentante = false;
    if (e) {
      this.idRepresentante = 0;
      this.editandoRep = true;
    }
  }

  removerRepresentante(i: number) {
    this.processo.representantes.splice(i, 1);
  }

  representanteSalvo(rep: Consumidor) {
    this.editandoRep = false;
    if (rep) {
      if (this.processo.representantes.length > 0) {
        let index = -1;
        for (let r of this.processo.representantes) {
          if (r.id == rep.id) index = this.processo.representantes.indexOf(r);
        }
        if (index > -1) this.processo.representantes.splice(index, 1);
      }
      this.processo.representantes.push(rep);
    }

    setTimeout(() => {
      this.scrollRepresentante.nativeElement.scrollIntoView({
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
    this.processo.fornecedores.splice(i, 1);
  }

  fornecedorSalvo(forn: Fornecedor) {
    this.editandoForn = false;
    if (forn) {
      if (this.processo.fornecedores.length > 0) {
        let index = -1;
        for (let f of this.processo.fornecedores) {
          if (f.id == forn.id) index = this.processo.fornecedores.indexOf(f);
        }
        if (index > -1) this.processo.fornecedores.splice(index, 1);
      }
      this.processo.fornecedores.push(forn);
    }

    setTimeout(() => {
      this.scrollFornecedor.nativeElement.scrollIntoView({
        behavior: 'smooth',
        block: 'center',
        inline: 'center',
      });
    }, 100);
  }

  registrarMov(mov: Movimento) {
    if (mov) {
      this.processo.movimentacao.unshift(mov);
    }
    this.lancandoMov = false;
  }

  removerMov(index: number) {
    this.processo.movimentacao.splice(index, 1);
  }

  getRowClass(para: string): string {
    switch (para) {
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
}
