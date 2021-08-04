import { Perfil } from './perfil';

export class Usuario {
  id: number;
  nome: string;
  email: string;
  password: string;
  perfil: Perfil;
  ativo: boolean;

  constructor(
    id: number,
    nome: string,
    email: string,
    password: string,
    perfil: Perfil,
    ativo: boolean
  ) {
    this.id = id;
    this.nome = nome;
    this.email = email;
    this.password = password;
    this.perfil = perfil;
    this.ativo = ativo;
  }
}
