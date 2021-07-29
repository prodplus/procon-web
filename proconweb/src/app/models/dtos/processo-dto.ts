export interface ProcessoDto {
  id: number;
  tipo: string;
  autos: string;
  ordem: number;
  consumidores: string[];
  fornecedores: string[];
  data: string;
  situacao: string;
}
