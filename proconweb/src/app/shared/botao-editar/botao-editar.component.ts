import { Component, Input, OnInit } from '@angular/core';
import { faEdit } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-botao-editar',
  templateUrl: './botao-editar.component.html',
  styleUrls: ['./botao-editar.component.css'],
})
export class BotaoEditarComponent implements OnInit {
  @Input() rota: string[];
  @Input() mensagem: string;
  @Input() disabled = false;
  iEdit = faEdit;

  constructor() {}

  ngOnInit(): void {}
}
