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
  situacao: string;

  constructor(processo: Processo) {
    this.id = processo.id;
    this.tipo = processo.tipo;
    this.autos = processo.autos;
    processo.consumidores.forEach((c) => this.consumidores.push(c.id));
    processo.representantes.forEach((r) => this.representantes.push(r.id));
    processo.fornecedores.forEach((f) => this.fornecedores.push(f.id));
    this.data = processo.data;
    this.relato = processo.relato;
    this.situacao = processo.situacao;
  }
}
