import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { faSearch } from '@fortawesome/free-solid-svg-icons';
import { ProcessoMovimentacao } from 'src/app/models/auxiliares/processo-movimentacao';
import { ProcessoService } from 'src/app/services/processo.service';
import { ModalComponent } from 'src/app/shared/modal/modal.component';
import { toDateApi } from 'src/app/utils/date-utils';

@Component({
  selector: 'app-movproc',
  templateUrl: './movproc.component.html',
  styleUrls: ['./movproc.component.css'],
})
export class MovprocComponent implements OnInit {
  isLoading = false;
  formData: FormGroup;
  movimentos: ProcessoMovimentacao[];
  @ViewChild('modal')
  modal: ModalComponent;
  iSearch = faSearch;
  rowClass: string;

  constructor(
    private processoService: ProcessoService,
    private builder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.formData = this.builder.group({
      data: [null, Validators.required],
    });
  }

  buscarMovimentos() {
    this.processoService
      .movimentacaoDiaria(toDateApi(new Date(this.formData.get('data').value)))
      .subscribe((m) => (this.movimentos = m));
  }

  getRowClass(i: number): string {
    if (i == 0) {
      this.rowClass = 'table-success';
      return this.rowClass;
    } else {
      const data1 = this.movimentos[i - 1].processo.autos;
      const data2 = this.movimentos[i].processo.autos;
      if (data1 !== data2) {
        if (this.rowClass === 'table-success') this.rowClass = 'table-warning';
        else this.rowClass = 'table-success';
      }
      return this.rowClass;
    }
  }
}
