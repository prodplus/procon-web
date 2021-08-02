import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { faMinus, faPlus } from '@fortawesome/free-solid-svg-icons';
import { getMascaraFone } from 'src/app/utils/fone-utils';

@Component({
  selector: 'app-cad-fone',
  templateUrl: './cad-fone.component.html',
  styleUrls: ['./cad-fone.component.css'],
})
export class CadFoneComponent implements OnInit {
  @Input() fones: string[];
  form: FormGroup;
  iPlus = faPlus;
  iMinus = faMinus;

  constructor(private builder: FormBuilder) {}

  ngOnInit(): void {
    this.form = this.builder.group({
      fone: ['', [Validators.required, Validators.minLength(8)]],
    });
  }

  adicionaFone() {
    this.fones.push(this.form.get('fone').value);
    this.form.get('fone').reset();
  }

  removerFone(index: number) {
    this.fones.splice(index, 1);
  }

  mascaraFone(fone: string): string {
    return getMascaraFone(fone);
  }
}
