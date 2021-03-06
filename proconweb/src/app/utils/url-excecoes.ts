const URLExcecoes = [
  'https://viacep.com.br',
  'https://www.gstatic',
  'https://special.config',
  'https://www.receitaws.com.br',
];

export function ehExcecao(url: string): boolean {
  for (let u of URLExcecoes) {
    if (url.startsWith(u, 0)) {
      return true;
    }
  }
  return false;
}
