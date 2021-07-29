import { Usuario } from '../usuario';

export class UsuarioForm {
  id: number;
  nome: string;
  email: string;
  password: string;
  perfil: string;

  constructor(usuario: Usuario) {
    this.id = usuario.id;
    this.nome = usuario.nome;
    this.email = usuario.email;
    this.password = usuario.password;
    this.perfil = usuario.perfil.role;
  }
}
