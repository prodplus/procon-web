import { Movimento } from '../auxiliares/movimento';
import { Processo } from '../processo';

export class ProcessoForm {
  id: number;
  tipo: string;
  autos: string;
  consumidores: number[];
  representantes: number[];
  fornecedores: number[];
  data: string;
  relato: string;
  movimentacao: Movimento[];
  situacao: string;
  atendente: number;

  constructor(processo: Processo) {
    this.id = processo.id;
    this.tipo = processo.tipo;
    this.autos = processo.autos;
    this.consumidores = [];
    processo.consumidores.forEach((c) => this.consumidores.push(c.id));
    this.representantes = [];
    processo.representantes.forEach((r) => this.representantes.push(r.id));
    this.fornecedores = [];
    processo.fornecedores.forEach((f) => this.fornecedores.push(f.id));
    this.data = processo.data;
    this.relato = processo.relato;
    this.movimentacao = [];
    processo.movimentacao.forEach((m) => this.movimentacao.push(m));
    this.situacao = processo.situacao;
  }
}
