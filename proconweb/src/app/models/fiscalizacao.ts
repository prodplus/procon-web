import { ItemFiscalizacao } from './auxiliares/item-fiscalizacao';
import { Fornecedor } from './fornecedor';
import { SetorFiscalizacao } from './setor-fiscalizacao';

export class Fiscalizacao {
  id: number;
  data: string;
  fornecedor: Fornecedor;
  observacoes: string;
  setor: SetorFiscalizacao;
  itens: ItemFiscalizacao[];

  constructor(
    id: number,
    data: string,
    fornecedor: Fornecedor,
    observacoes: string,
    setor: SetorFiscalizacao,
    itens: ItemFiscalizacao[]
  ) {
    this.id = id;
    this.data = data;
    this.fornecedor = fornecedor;
    this.observacoes = observacoes;
    this.setor = setor;
    this.itens = itens;
  }
}
