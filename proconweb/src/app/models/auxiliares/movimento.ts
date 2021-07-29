export class Movimento {
  data: string;
  de: string;
  para: string;
  averbacao: string;
  auxD: string;
  auxT: string;

  constructor(
    data: string,
    de: string,
    para: string,
    averbacao: string,
    auxD: string,
    auxT: string
  ) {
    this.data = data;
    this.de = de;
    this.para = para;
    this.averbacao = averbacao;
    this.auxD = auxD;
    this.auxT = auxT;
  }
}
