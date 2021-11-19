import { Lei } from './lei';

export class Dispositivo {
  id: number;
  descricao: string;
  lei: Lei;

  constructor(id: number, descricao: string, lei: Lei) {
    this.id = id;
    this.descricao = descricao;
    this.lei = lei;
  }
}
