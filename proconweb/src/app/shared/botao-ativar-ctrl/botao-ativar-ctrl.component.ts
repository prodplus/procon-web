import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { faToggleOff, faToggleOn } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-botao-ativar-ctrl',
  templateUrl: './botao-ativar-ctrl.component.html',
  styleUrls: ['./botao-ativar-ctrl.component.css'],
})
export class BotaoAtivarCtrlComponent implements OnInit {
  @Input() ativos: boolean;
  iToggleOn = faToggleOn;
  iToggleOff = faToggleOff;
  @Output() clique = new EventEmitter<boolean>();

  constructor() {}

  ngOnInit(): void {}

  onClick() {
    this.ativos = !this.ativos;
    this.clique.emit(this.ativos);
  }
}
