import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-tabela',
  templateUrl: './tabela.component.html',
  styleUrls: ['./tabela.component.css'],
})
export class TabelaComponent implements OnInit {
  @Input() tamanhoTotal: number;
  @Input() tamanhoPagina: number;
  @Input() pagina: number;
  @Output() mudaPagina = new EventEmitter<number>();

  constructor() {}

  ngOnInit(): void {}

  alteraPagina(pagina: number) {
    this.pagina = pagina;
    this.mudaPagina.emit(this.pagina);
  }
}
