export function getMascaraFone(value: string): string {
  if (value && value.charAt(2) && value.charAt(2) == '9') {
    return '(00) 00000-0000';
  } else {
    return '(00) 0000-0000';
  }
}
