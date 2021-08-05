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
import { Fornecedor } from 'src/app/models/fornecedor';
import { FornecedorService } from 'src/app/services/fornecedor.service';
import { ModalComponent } from 'src/app/shared/modal/modal.component';

@Component({
  selector: 'app-cad-fornecedores',
  templateUrl: './cad-fornecedores.component.html',
  styleUrls: ['./cad-fornecedores.component.css'],
})
export class CadFornecedoresComponent implements OnInit, AfterViewInit {
  isLoading = false;
  idFornecedor: number;
  fones: string[];
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
    fantasia: [''],
    razaoSocial: [''],
    cnpj: ['', [Validators.minLength(14)]],
    email: ['', [Validators.email]],
    cep: [''],
    logradouro: [''],
    numero: [''],
    complemento: [''],
    bairro: [''],
    municipio: [''],
    uf: [null],
  });

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private builder: FormBuilder,
    private fornecedorService: FornecedorService
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
            this.isLoading = false;
            this.idFornecedor = this.idExterno;
          }
        );
      } else if (values.get('id')) {
        const forn: Fornecedor = this.route.snapshot.data['fornecedor'];
        this.idFornecedor = forn.id;
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
        if (value?.length > 13 && !this.idFornecedor) {
          let b: boolean = false;
          this.fornecedorService.cnpjExiste(value).subscribe(
            (v) => (b = v),
            (err) => this.modal.openPadrao(err),
            () => {
              if (b) {
                this.form.get('cnpj').setErrors({ fornExiste: true });
              } else {
                this.form.get('cnpj').setErrors({ fornExiste: false });
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
                this.form.get('fantasia').setErrors({ fornExiste: false });
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
}
