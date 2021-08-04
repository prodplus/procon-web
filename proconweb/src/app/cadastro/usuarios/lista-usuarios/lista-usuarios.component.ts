import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Page } from 'src/app/models/auxiliares/page';
import { RespModal } from 'src/app/models/auxiliares/resp-modal';
import { UsuarioDto } from 'src/app/models/dtos/usuario-dto';
import { UsuarioService } from 'src/app/services/usuario.service';
import { ModalComponent } from 'src/app/shared/modal/modal.component';
import { mensagemPadrao, trataMensagem } from 'src/app/utils/mensagem-utils';

@Component({
  selector: 'app-lista-usuarios',
  templateUrl: './lista-usuarios.component.html',
  styleUrls: ['./lista-usuarios.component.css'],
})
export class ListaUsuariosComponent implements OnInit {
  isLoading = false;
  page: Page<UsuarioDto>;
  idUsuario: number;
  pagina = 1;
  ativos = true;
  @ViewChild('modal')
  modal: ModalComponent;

  constructor(
    private usuarioService: UsuarioService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.page = this.route.snapshot.data['page'];
  }

  private recarregar(ativos: boolean, pagina: number) {
    this.usuarioService.listar(ativos, pagina - 1, 20).subscribe(
      (p) => (this.page = p),
      (err) => {
        this.isLoading = false;
        this.modal.openPadrao(err);
      },
      () => (this.isLoading = false)
    );
  }

  alteraAtivos() {
    this.isLoading = true;
    this.ativos = !this.ativos;
    this.pagina = 1;
    this.recarregar(this.ativos, this.pagina);
  }

  mudaPagina(pagina: number) {
    this.pagina = pagina;
    this.recarregar(this.ativos, this.pagina);
  }

  chamaModal(resp: { id: number; tipo: string }) {
    this.idUsuario = resp.id;
    const mensagem = trataMensagem(resp.tipo);
    this.modal.open(
      mensagemPadrao(
        null,
        'warning',
        'Atenção!!',
        'Tem certeza que deseja ' + mensagem + ' o usuário?'
      ),
      resp.tipo,
      true
    );
  }

  concordou(resp: RespModal) {
    if (resp.confirmou) {
      resp.tipo === 'd'
        ? this.ativar(this.idUsuario, false)
        : resp.tipo === 'a'
        ? this.ativar(this.idUsuario, true)
        : resp.tipo === 'e'
        ? this.excluir(this.idUsuario)
        : this.modal.open(
            mensagemPadrao(null, 'danger', 'Erro!!', 'Opção inválida!'),
            '',
            false
          );
    }
  }

  private ativar(id: number, ativar: boolean) {
    this.isLoading = true;
    this.usuarioService.ativar(id, ativar).subscribe({
      error: (err) => {
        this.isLoading = false;
        this.modal.openPadrao(err);
      },
      complete: () => {
        this.pagina = 1;
        this.mudaPagina(this.pagina);
      },
    });
  }

  private excluir(id: number) {
    this.isLoading = false;
    this.usuarioService.excluir(id).subscribe({
      error: (err) => {
        this.isLoading = false;
        this.modal.openPadrao(err);
      },
      complete: () => {
        this.pagina = 1;
        this.mudaPagina(this.pagina);
      },
    });
  }
}
