import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { faToggleOff, faToggleOn } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-botao-ativar',
  templateUrl: './botao-ativar.component.html',
  styleUrls: ['./botao-ativar.component.css'],
})
export class BotaoAtivarComponent implements OnInit {
  @Input() ativos: boolean;
  @Input() id: number;
  @Output() emissao = new EventEmitter<{ id: number; tipo: string }>();
  iToggleOn = faToggleOn;
  iToggleOff = faToggleOff;

  constructor() {}

  ngOnInit(): void {}

  emitir() {
    this.emissao.emit({ id: this.id, tipo: this.ativos ? 'd' : 'a' });
  }
}
