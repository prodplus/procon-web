import {
  AfterViewInit,
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { faWindowClose } from '@fortawesome/free-solid-svg-icons';
import { debounceTime } from 'rxjs/operators';
import { Consumidor } from 'src/app/models/consumidor';
import { ConsumidorService } from 'src/app/services/consumidor.service';
import { EnumService } from 'src/app/services/enum.service';
import { ModalComponent } from 'src/app/shared/modal/modal.component';
import { getMascaraFone } from 'src/app/utils/fone-utils';
import { getMascaraCadastro } from 'src/app/utils/mask-utils';

@Component({
  selector: 'app-cad-consumidor',
  templateUrl: './cad-consumidor.component.html',
  styleUrls: ['./cad-consumidor.component.css'],
})
export class CadConsumidorComponent implements OnInit, AfterViewInit {
  isLoading = false;
  idConsumidor: number;
  tipos: string[];
  fones: string[];
  iWindowClose = faWindowClose;
  @Input() idExterno: number;
  @Output() salvo = new EventEmitter<Consumidor>();
  @ViewChild('modal')
  modal: ModalComponent;
  @ViewChild('input')
  input: ElementRef<HTMLSelectElement>;
  @ViewChild('scrollInit')
  scrollInit: ElementRef<HTMLDivElement>;
  form = this.builder.group({
    tipo: ['FISICA', [Validators.required]],
    denominacao: ['', [Validators.required]],
    cadastro: [
      '',
      [Validators.required, Validators.minLength(11), Validators.maxLength(14)],
    ],
    email: ['', [Validators.email]],
    cep: ['', [Validators.required]],
    logradouro: ['', [Validators.required]],
    numero: ['', [Validators.required]],
    complemento: [''],
    bairro: [''],
    municipio: ['', [Validators.required]],
    uf: [null, [Validators.required]],
  });

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private builder: FormBuilder,
    private enumService: EnumService,
    private consumidorService: ConsumidorService
  ) {}

  ngOnInit(): void {
    this.enumService.getTiposPessoa().subscribe((t) => (this.tipos = t));

    this.route.paramMap.subscribe((values) => {
      if (this.idExterno) {
        this.isLoading = true;
        this.consumidorService.buscar(this.idExterno).subscribe(
          (c) => this.carregaForm(c),
          (err) => {
            this.isLoading = false;
            this.modal.openPadrao(err);
          },
          () => {
            this.isLoading = false;
            this.idConsumidor = this.idExterno;
          }
        );
      } else if (values.get('id')) {
        const cons: Consumidor = this.route.snapshot.data['consumidor'];
        this.fones = cons.fones;
        this.idConsumidor = cons.id;
        this.carregaForm(cons);
      } else if (this.idExterno == 0) {
        this.idExterno = 1;
        this.fones = [];
        this.idConsumidor = null;
      } else {
        this.fones = [];
        this.idConsumidor = null;
        this.idExterno = null;
      }
    });
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.input.nativeElement.focus();
      this.scrollInit.nativeElement.scrollTo();
    }, 100);

    this.form
      .get('cadastro')
      .valueChanges.pipe(debounceTime(300))
      .subscribe((value) => {
        if (value?.length >= 10) {
          let b: boolean = false;
          this.consumidorService.consumidorExiste(value).subscribe(
            (v) => (b = v),
            (err) => this.modal.openPadrao(err),
            () => {
              if (b) {
                this.form.get('cadastro').setErrors({ consExiste: true });
              }
            }
          );
        }
      });
  }

  private carregaForm(cons: Consumidor) {
    this.fones = cons.fones;
    this.form.patchValue({
      tipo: cons.tipo,
      denominacao: cons.denominacao,
      cadastro: cons.cadastro,
      email: cons.email,
      cep: cons.endereco?.cep,
      logradouro: cons.endereco?.logradouro,
      numero: cons.endereco?.numero,
      complemento: cons.endereco?.complemento,
      bairro: cons.endereco?.bairro,
      municipio: cons.endereco?.municipio,
      uf: cons.endereco?.uf,
    });
  }

  private carregaCons(): Consumidor {
    return new Consumidor(
      this.idConsumidor ? this.idConsumidor : null,
      this.form.get('tipo').value,
      this.form.get('denominacao').value.toUpperCase(),
      this.form.get('cadastro').value,
      this.form.get('email').value,
      this.form.get('cep').value,
      this.form.get('logradouro').value,
      this.form.get('numero').value,
      this.form.get('complemento').value,
      this.form.get('bairro').value,
      this.form.get('municipio').value,
      this.form.get('uf').value,
      this.fones
    );
  }

  procedimentoSalvar(cons: Consumidor) {
    if (this.idExterno) {
      this.salvo.emit(cons);
    } else {
      this.router.navigate(['/cadastro/consumidores']);
    }
  }

  cancelarExterno() {
    this.salvo.emit(null);
  }

  mascaraCadastro(): string {
    return getMascaraCadastro(this.form.get('tipo').value);
  }

  salvar() {
    this.isLoading = true;
    let cons: Consumidor;
    if (!this.idConsumidor) {
      this.consumidorService.salvar(this.carregaCons()).subscribe(
        (c) => (cons = c),
        (err) => {
          this.isLoading = false;
          this.modal.openPadrao(err);
        },
        () => {
          this.isLoading = false;
          this.procedimentoSalvar(cons);
        }
      );
    } else {
      this.consumidorService
        .atualizar(this.idConsumidor, this.carregaCons())
        .subscribe(
          (c) => (cons = c),
          (err) => {
            this.isLoading = false;
            this.modal.openPadrao(err);
          },
          () => {
            this.isLoading = false;
            this.procedimentoSalvar(cons);
          }
        );
    }
  }

  getFone(fone: string): string {
    return getMascaraFone(fone);
  }
}
