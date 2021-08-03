export function getMascaraCadastro(tipo: string): string {
  if (tipo === 'FISICA') {
    return '000.000.000-00';
  } else {
    return '00.000.000/0000-00';
  }
}
