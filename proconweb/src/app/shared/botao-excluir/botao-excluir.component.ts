import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { faTrash } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-botao-excluir',
  templateUrl: './botao-excluir.component.html',
  styleUrls: ['./botao-excluir.component.css'],
})
export class BotaoExcluirComponent implements OnInit {
  @Input() id: number;
  @Input() disabled: false;
  @Output() emissao = new EventEmitter<{ id: number; tipo: string }>();
  iTrash = faTrash;

  constructor() {}

  ngOnInit(): void {}

  emitir() {
    this.emissao.emit({ id: this.id, tipo: 'e' });
  }
}
