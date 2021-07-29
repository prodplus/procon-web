import { Consumidor } from './consumidor';
import { Fornecedor } from './fornecedor';
import { Usuario } from './usuario';

export class Atendimento {
  id: number;
  consumidores: Consumidor[];
  fornecedores: Fornecedor[];
  data: string;
  relato: string;
  atendente: Usuario;

  constructor(
    id: number,
    consumidores: Consumidor[],
    fornecedores: Fornecedor[],
    data: string,
    relato: string,
    atendente: Usuario
  ) {
    this.id = id;
    this.consumidores = consumidores;
    this.fornecedores = fornecedores;
    this.data = data;
    this.relato = relato;
    this.atendente = atendente;
  }
}
