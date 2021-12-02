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
import { faSearch, faWindowClose } from '@fortawesome/free-solid-svg-icons';
import { debounceTime } from 'rxjs/operators';
import { ModelRfb } from 'src/app/models/auxiliares/model-rfb';
import { ProcessoDto } from 'src/app/models/dtos/processo-dto';
import { Fornecedor } from 'src/app/models/fornecedor';
import { FornecedorService } from 'src/app/services/fornecedor.service';
import { ProcessoService } from 'src/app/services/processo.service';
import { ModalComponent } from 'src/app/shared/modal/modal.component';
import { mensagemPadrao } from 'src/app/utils/mensagem-utils';

@Component({
  selector: 'app-cad-fornecedores',
  templateUrl: './cad-fornecedores.component.html',
  styleUrls: ['./cad-fornecedores.component.css'],
})
export class CadFornecedoresComponent implements OnInit, AfterViewInit {
  isLoading = false;
  idFornecedor: number;
  fones: string[];
  processos: ProcessoDto[];
  iWindowClose = faWindowClose;
  @Input() idExterno: number;
  @Output() salvo = new EventEmitter<Fornecedor>();
  @ViewChild('modal')
  modal: ModalComponent;
  @ViewChild('input')
  input: ElementRef<HTMLInputElement>;
  @ViewChild('scrollInit')
  scrollInit: ElementRef<HTMLDivElement>;
  form = this.builder.group({
    fantasia: ['', [Validators.required]],
    razaoSocial: [''],
    cnpj: [''],
    email: ['', [Validators.email]],
    cep: [''],
    logradouro: [''],
    numero: [''],
    complemento: [''],
    bairro: [''],
    municipio: [''],
    uf: [null],
  });
  iSearch = faSearch;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private builder: FormBuilder,
    private fornecedorService: FornecedorService,
    private processoService: ProcessoService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((values) => {
      if (this.idExterno) {
        this.isLoading = true;
        this.fornecedorService.buscar(this.idExterno).subscribe(
          (c) => this.carregaForm(c),
          (err) => {
            this.isLoading = false;
            this.modal.openPadrao(err);
          },
          () => {
            this.processoService
              .listarPorFornecedorNaoResolvido(this.idExterno)
              .subscribe(
                (p) => (this.processos = p),
                (err) => {
                  this.isLoading = false;
                  this.modal.openPadrao(err);
                },
                () => {
                  this.isLoading = false;
                  this.idFornecedor = this.idExterno;
                }
              );
          }
        );
      } else if (values.get('id')) {
        const forn: Fornecedor = this.route.snapshot.data['fornecedor'];
        this.idFornecedor = forn.id;
        this.processoService
          .listarPorFornecedorNaoResolvido(this.idFornecedor)
          .subscribe(
            (p) => (this.processos = p),
            (err) => {
              this.isLoading = false;
              this.modal.openPadrao(err);
            },
            () => {
              this.isLoading = false;
            }
          );
        this.carregaForm(forn);
      } else if (this.idExterno == 0) {
        this.idExterno = 1;
        this.fones = [];
        this.idFornecedor = null;
      } else {
        this.fones = [];
        this.idFornecedor = null;
        this.idExterno = null;
      }
    });
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.input.nativeElement.focus();
      this.scrollInit.nativeElement.scrollTo();
    }, 100);

    if (!this.idExterno && !this.idFornecedor) {
      this.form.get('fantasia').setValidators([Validators.required]);
    }

    this.form
      .get('cnpj')
      .valueChanges.pipe(debounceTime(300))
      .subscribe((value) => {
        if (value?.length > 10 && !this.idFornecedor) {
          let b: boolean = false;
          this.fornecedorService.cnpjExiste(value).subscribe(
            (v) => (b = v),
            (err) => this.modal.openPadrao(err),
            () => {
              if (b) {
                this.form.get('cnpj').setErrors({ fornExiste: true });
              } else {
                this.form.get('cnpj').setErrors(null);
              }
            }
          );
        }
      });

    this.form
      .get('fantasia')
      .valueChanges.pipe(debounceTime(300))
      .subscribe((value) => {
        if (value && !this.idFornecedor) {
          let b: boolean = false;
          this.fornecedorService.fantasiaExiste(value).subscribe(
            (v) => (b = v),
            (err) => this.modal.openPadrao(err),
            () => {
              if (b) {
                this.form.get('fantasia').setErrors({ fornExiste: true });
              } else {
                this.form.get('fantasia').setErrors(null);
              }
            }
          );
        }
      });
  }

  private carregaForm(forn: Fornecedor) {
    this.fones = forn.fones;
    this.form.patchValue({
      fantasia: forn.fantasia,
      razaoSocial: forn.razaoSocial,
      cnpj: forn.cnpj,
      email: forn.email,
      cep: forn.endereco?.cep,
      logradouro: forn.endereco?.logradouro,
      numero: forn.endereco?.numero,
      complemento: forn.endereco?.complemento,
      bairro: forn.endereco?.bairro,
      municipio: forn.endereco?.municipio,
      uf: forn.endereco?.uf,
    });
  }

  private carregaForn(): Fornecedor {
    return new Fornecedor(
      this.idFornecedor ? this.idFornecedor : null,
      this.form.get('fantasia').value.toUpperCase(),
      this.form.get('razaoSocial').value.toUpperCase(),
      this.form.get('cnpj').value,
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

  procedimentoSalvar(forn: Fornecedor) {
    if (this.idExterno) {
      this.salvo.emit(forn);
    } else {
      this.router.navigate(['/cadastro/fornecedores']);
    }
  }

  salvar() {
    this.isLoading = true;
    let forn: Fornecedor;
    if (!this.idFornecedor) {
      this.fornecedorService.salvar(this.carregaForn()).subscribe(
        (f) => (forn = f),
        (err) => {
          this.isLoading = false;
          this.modal.openPadrao(err);
        },
        () => {
          this.isLoading = false;
          this.procedimentoSalvar(forn);
        }
      );
    } else {
      this.fornecedorService
        .atualizar(this.idFornecedor, this.carregaForn())
        .subscribe(
          (f) => (forn = f),
          (err) => {
            this.isLoading = false;
            this.modal.openPadrao(err);
          },
          () => {
            this.isLoading = false;
            this.procedimentoSalvar(forn);
          }
        );
    }
  }

  buscaCnpj() {
    const cnpj: string = this.form.get('cnpj').value;
    if (cnpj.length > 14 || cnpj.length < 13) return;
    let model: ModelRfb;
    this.fornecedorService.consultaCnpj(cnpj).subscribe(
      (c) => (model = c),
      (err) => {
        this.modal.open(
          mensagemPadrao(
            null,
            'danger',
            'CNPJ não localizado!',
            'CNPJ inválido ou não localizado!'
          ),
          '',
          false
        );
        console.log(err);
      },
      () => {
        this.form.get('razaoSocial').setValue(model.nome);
        this.form.get('email').setValue(model.email);
        this.form.get('cep').setValue(model.cep);
        this.form.get('logradouro').setValue(model.logradouro);
        this.form.get('numero').setValue(model.numero);
        this.form.get('complemento').setValue(model.complemento);
        this.form.get('bairro').setValue(model.bairro);
        this.form.get('municipio').setValue(model.municipio);
        this.form.get('uf').setValue(model.uf);
        this.form.get('fantasia').setValue(model.fantasia);
      }
    );
  }
}
