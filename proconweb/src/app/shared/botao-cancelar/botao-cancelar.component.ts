import { Component, Input, OnInit } from '@angular/core';
import { faWindowClose } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-botao-cancelar',
  templateUrl: './botao-cancelar.component.html',
  styleUrls: ['./botao-cancelar.component.css'],
})
export class BotaoCancelarComponent implements OnInit {
  @Input() rota: string[];
  mensagem: 'cancelar';
  icon = faWindowClose;

  constructor() {}

  ngOnInit(): void {}
}
