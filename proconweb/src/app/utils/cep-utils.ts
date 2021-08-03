import { FormGroup } from '@angular/forms';
import { Endereco, ErroCep, NgxViacepService } from '@brunoc/ngx-viacep';

export function buscarPorCep(form: FormGroup, cepService: NgxViacepService) {
  const cep = form.get('cep').value;

  form.get('logradouro').setValue('...');
  form.get('numero').setValue('');
  form.get('complemento').setValue('');
  form.get('bairro').setValue('...');
  form.get('municipio').setValue('...');
  form.get('uf').setValue(null);

  cepService
    .buscarPorCep(cep)
    .then((e: Endereco) => {
      form.get('logradouro').setValue(e.logradouro);
      form.get('bairro').setValue(e.bairro);
      form.get('municipio').setValue(e.localidade);
      form.get('uf').setValue(e.uf);
    })
    .catch((err: ErroCep) => {
      form.get('logradouro').setValue('');
      form.get('numero').setValue('');
      form.get('complemento').setValue('');
      form.get('bairro').setValue('');
      form.get('municipio').setValue('');
      form.get('uf').setValue(null);
      alert(err.message);
    });
}

export function buscarPorEndereco(
  form: FormGroup,
  cepService: NgxViacepService
) {
  const logradouro = form.get('logradouro').value;
  const uf = form.get('uf').value;
  const municipio = form.get('municipio').value;
  cepService
    .buscarPorEndereco(uf, municipio, logradouro)
    .then((e) => form.get('cep').setValue(e[0].cep))
    .catch((err: ErroCep) => alert(err.message));
}
