import { Perfil } from "./perfil";

export interface Usuario {
  id: number;
  nome: string;
  email: string;
  password: string;
  perfil: Perfil;
  ativo: boolean;
}
