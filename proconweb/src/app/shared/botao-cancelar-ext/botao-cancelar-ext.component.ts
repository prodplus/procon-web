import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { faWindowClose } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-botao-cancelar-ext',
  templateUrl: './botao-cancelar-ext.component.html',
  styleUrls: ['./botao-cancelar-ext.component.css'],
})
export class BotaoCancelarExtComponent implements OnInit {
  @Output() clicou = new EventEmitter();
  iClose = faWindowClose;

  constructor() {}

  ngOnInit(): void {}

  onClick() {
    this.clicou.emit();
  }
}
