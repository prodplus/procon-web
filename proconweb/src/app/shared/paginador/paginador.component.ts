import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-paginador',
  templateUrl: './paginador.component.html',
  styleUrls: ['./paginador.component.css'],
})
export class PaginadorComponent implements OnInit {
  @Input() tamanhoTotal: number;
  @Input() tamanhoPagina: number;
  @Input() pagina: number;
  @Output() mudaPagina = new EventEmitter<number>();

  constructor() {}

  ngOnInit(): void {}

  alteraPagina() {
    this.mudaPagina.emit(this.pagina);
  }
}
