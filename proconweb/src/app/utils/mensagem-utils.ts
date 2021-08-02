import { ResponseStatusException } from '../models/auxiliares/response-status';

export interface Mensagem {
  tipo: string;
  titulo: string;
  mensagem: string;
}

export function mensagemPadrao(
  err: ResponseStatusException,
  tipo: string,
  titulo: string,
  mensagem: string
): Mensagem {
  if (err) {
    console.log(err);
  }

  const retorno: Mensagem = {
    tipo: 'modal-title text-' + tipo,
    titulo: titulo,
    mensagem:
      mensagem === '' ? err.message : mensagem === 'sem' ? '' : mensagem,
  };

  return retorno;
}

export function trataMensagem(tipo: string): string {
  return tipo === 'd'
    ? 'desativar'
    : tipo === 'a'
    ? 'ativar'
    : tipo === 'e'
    ? 'excluir DEFINITIVAMENTE'
    : tipo === 'c'
    ? 'CANCELAR'
    : '';
}
