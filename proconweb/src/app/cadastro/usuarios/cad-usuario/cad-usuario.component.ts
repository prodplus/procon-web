import {
  AfterViewInit,
  Component,
  ElementRef,
  OnInit,
  ViewChild,
} from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { debounceTime } from 'rxjs/operators';
import { UsuarioDto } from 'src/app/models/dtos/usuario-dto';
import { UsuarioForm } from 'src/app/models/forms/usuario-form';
import { getPerfil, Perfil } from 'src/app/models/perfil';
import { Usuario } from 'src/app/models/usuario';
import { UsuarioService } from 'src/app/services/usuario.service';
import { ModalComponent } from 'src/app/shared/modal/modal.component';
import { MustMatch } from 'src/app/utils/must-match';

@Component({
  selector: 'app-cad-usuario',
  templateUrl: './cad-usuario.component.html',
  styleUrls: ['./cad-usuario.component.css'],
})
export class CadUsuarioComponent implements OnInit, AfterViewInit {
  isLoading = false;
  idUsuario: number;
  perfis: Perfil[];
  @ViewChild('modal')
  modal: ModalComponent;
  @ViewChild('input')
  input: ElementRef<HTMLInputElement>;
  @ViewChild('scrollInit')
  scrollInit: ElementRef<HTMLDivElement>;
  form = this.builder.group(
    {
      nome: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: [
        '',
        [
          Validators.required,
          Validators.minLength(4),
          Validators.maxLength(10),
        ],
      ],
      confirma: ['', [Validators.required]],
      perfil: ['ROLE_USER', [Validators.required]],
    },
    { validators: MustMatch('password', 'confirma') }
  );

  constructor(
    private builder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private usuarioService: UsuarioService
  ) {}

  ngOnInit(): void {
    this.usuarioService.getPerfis().subscribe(
      (p) => (this.perfis = p),
      (err) => this.modal.openPadrao(err)
    );

    this.route.paramMap.subscribe((params) => {
      if (params.get('id')) {
        this.carregaForm(this.route.snapshot.data['usuario']);
      }
    });
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.input.nativeElement.focus();
      this.scrollInit.nativeElement.scrollTo();
    }, 100);

    this.form
      .get('email')
      .valueChanges.pipe(debounceTime(300))
      .subscribe((value) => {
        if (value && value.length > 5) {
          let b: boolean = false;
          this.usuarioService.loginExiste(value).subscribe(
            (v) => (b = v),
            (err) => this.modal.openPadrao(err),
            () => {
              if (b) {
                this.form.get('email').setErrors({ loginExiste: true });
              } else {
                this.form.get('email').setErrors({ loginExiste: false });
              }
            }
          );
        }
      });
  }

  private carregaForm(u: UsuarioDto) {
    this.idUsuario = u.id;
    this.form.patchValue({
      nome: u.nome,
      email: u.email,
      perfil: u.perfil,
    });
  }

  private carregaUsuario(): UsuarioForm {
    return new UsuarioForm(
      new Usuario(
        this.idUsuario ? this.idUsuario : null,
        this.form.get('nome').value.toUpperCase(),
        this.form.get('email').value,
        this.form.get('password').value,
        getPerfil(this.form.get('perfil').value, this.perfis),
        true
      )
    );
  }

  salvar() {
    this.isLoading = false;
    if (!this.idUsuario) {
      this.usuarioService.salvar(this.carregaUsuario()).subscribe({
        error: (err) => {
          this.isLoading = false;
          this.modal.openPadrao(err);
        },
        complete: () => {
          this.isLoading = false;
          this.router.navigate(['/cadastro/usuarios']);
        },
      });
    } else {
      this.usuarioService
        .atualizar(this.idUsuario, this.carregaUsuario())
        .subscribe({
          error: (err) => {
            this.isLoading = false;
            this.modal.openPadrao(err);
          },
          complete: () => {
            this.isLoading = false;
            this.router.navigate(['/cadastro/usuarios']);
          },
        });
    }
  }
}
