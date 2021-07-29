import { Movimento } from './auxiliares/movimento';
import { Consumidor } from './consumidor';
import { Fornecedor } from './fornecedor';

export class Processo {
  id: number;
  tipo: string;
  autos: string;
  consumidores: Consumidor[];
  representantes: Consumidor[];
  fornecedores: Fornecedor[];
  data: string;
  movimentacao: Movimento[];
  relato: string;
  situacao: string;

  constructor(
    id: number,
    tipo: string,
    autos: string,
    consumidores: Consumidor[],
    representantes: Consumidor[],
    fornecedores: Fornecedor[],
    data: string,
    movimentacao: Movimento[],
    relato: string,
    situacao: string
  ) {
    this.id = id;
    this.tipo = tipo;
    this.autos = autos;
    this.consumidores = consumidores;
    this.representantes = representantes;
    this.fornecedores = fornecedores;
    this.data = data;
    this.movimentacao = movimentacao;
    this.relato = relato;
    this.situacao = situacao;
  }
}
