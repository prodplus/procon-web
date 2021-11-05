export interface ModelRfb {
  atividade_principal: Atividade[];
  data_situacao: string; // tipo: "dd/MM/yyyy"
  complemento: string;
  tipo: string; // matriz ou filial
  nome: string; // razão social
  uf: string;
  telefone: string;
  email: string;
  atividades_secundarias: Atividade[];
  situacao: string; // ativa, inativa, baixada...
  bairro: string;
  logradouro: string;
  numero: string;
  cep: string;
  municipio: string;
  porte: string;
  abertura: string; // tipo: "dd/MM/yyyy"
  natureza_juridica: string;
  cnpj: string;
  ultima_atualizacao: string;
  status: string;
  fantasia: string;
  efr: string;
  motivo_situacao: string;
  situacao_especial: string;
  data_situacao_especial: string;
  capital_social: string;
  qsa: [];
  extra: {};
  billing: {
    free: boolean;
    database: boolean;
  };
}

interface Atividade {
  text: string;
  code: string;
}
