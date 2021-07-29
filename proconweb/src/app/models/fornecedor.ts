import { Endereco } from './auxiliares/endereco';

export class Fornecedor {
  id: number;
  fantasia: string;
  razaoSocial: string;
  cnpj: string;
  email: string;
  endereco: Endereco;
  fones: string[];

  constructor(
    id: number,
    fantasia: string,
    razaoSocial: string,
    cnpj: string,
    email: string,
    cep: string,
    logradouro: string,
    numero: string,
    complemento: string,
    bairro: string,
    municipio: string,
    uf: string,
    fones: string[]
  ) {
    this.id = id;
    this.fantasia = fantasia;
    this.razaoSocial = razaoSocial;
    this.cnpj = cnpj;
    this.email = email;
    this.endereco = new Endereco(
      cep,
      logradouro,
      numero,
      complemento,
      bairro,
      municipio,
      uf
    );
    this.fones = fones;
  }
}
