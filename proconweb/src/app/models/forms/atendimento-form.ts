import { Atendimento } from '../atendimento';

export class AtendimentoForm {
  id: number;
  data: string;
  consumidores: number[];
  fornecedores: number[];
  relato: string;
  atendente: number;

  constructor(atendimento: Atendimento) {
    this.id = atendimento.id;
    this.data = atendimento.data;
    this.consumidores = [];
    atendimento.consumidores.forEach((c) => this.consumidores.push(c.id));
    this.fornecedores = [];
    atendimento.fornecedores.forEach((f) => this.fornecedores.push(f.id));
    this.relato = atendimento.relato;
    this.atendente = atendimento.atendente.id;
  }
}
