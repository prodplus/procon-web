export interface Perfil {
  id: number;
  role: string;
  descricao: string;
}

export function getPerfil(role: string, perfis: Perfil[]) {
  for (let p of perfis) {
    if (p.role == role) {
      return p;
    }
  }
  return null;
}
