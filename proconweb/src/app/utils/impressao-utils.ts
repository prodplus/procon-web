export function impressaoUtils(x: Blob, codigo: number, tipo: string) {
  const newBlob = new Blob([x], { type: 'application/pdf' });

  if (window.navigator && window.navigator.msSaveOrOpenBlob) {
    window.navigator.msSaveOrOpenBlob(newBlob);
    return;
  }

  const data = window.URL.createObjectURL(newBlob);
  const link = document.createElement('a');
  link.href = data;
  link.download = tipo + codigo + '.pdf';
  link.dispatchEvent(
    new MouseEvent('click', { bubbles: true, cancelable: true, view: window })
  );

  setTimeout(() => {
    window.URL.revokeObjectURL(data);
    link.remove();
  }, 100);
}
