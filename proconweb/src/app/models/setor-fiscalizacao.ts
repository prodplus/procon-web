export class SetorFiscalizacao {
  id: number;
  descricao: string;
  itens: string[];

  constructor(id: number, descricao: string, itens: string[]) {
    this.id = id;
    this.descricao = descricao;
    this.itens = itens;
  }
}
