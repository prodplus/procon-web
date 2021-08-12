import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProcDesc } from 'src/app/models/dtos/proc-desc';
import { ModalComponent } from 'src/app/shared/modal/modal.component';

@Component({
  selector: 'app-por-prazo',
  templateUrl: './por-prazo.component.html',
  styleUrls: ['./por-prazo.component.css'],
})
export class PorPrazoComponent implements OnInit {
  isLoading = false;
  processos: ProcDesc[];
  @ViewChild('modal')
  modal: ModalComponent;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.processos = this.route.snapshot.data['processos'];
  }

  getRowClass(proc: ProcDesc): string {
    if (proc.descricao.startsWith('Vencido')) return 'table-danger';
    else if (proc.descricao.startsWith('Sem')) return 'table-warning';
    else return 'table-primary';
  }
}
