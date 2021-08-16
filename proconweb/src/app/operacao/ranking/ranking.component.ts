import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { debounceTime } from 'rxjs/operators';
import { FornecedorNro } from 'src/app/models/auxiliares/fornecedor-nro';
import { ProcessoService } from 'src/app/services/processo.service';
import { ModalComponent } from 'src/app/shared/modal/modal.component';

@Component({
  selector: 'app-ranking',
  templateUrl: './ranking.component.html',
  styleUrls: ['./ranking.component.css'],
})
export class RankingComponent implements OnInit, AfterViewInit {
  isLoading = false;
  ranking: FornecedorNro[];
  anos: number[] = [];
  title = '';
  type = 'PieChart';
  data = [];
  columnNames = ['Fornecedor', 'Processos'];
  options = { is3D: true };
  width = 1100;
  height = 600;
  anoForm = this.builder.group({
    input: [new Date().getUTCFullYear()],
  });
  @ViewChild('modal')
  modal: ModalComponent;

  constructor(
    private processoService: ProcessoService,
    private builder: FormBuilder
  ) {}

  ngOnInit(): void {
    const ano: number = new Date().getUTCFullYear();
    for (let i = 0; i <= 5; i++) this.anos.push(ano - i);
    this.title = 'Ranking do Ano ' + ano;
    this.isLoading = true;
    this.processoService.ranking(new Date().getUTCFullYear()).subscribe(
      (p) => (this.ranking = p),
      (err) => {
        this.isLoading = false;
        this.modal.openPadrao(err);
      },
      () => {
        for (let proc of this.ranking) {
          this.data.push([proc.fornecedor.razaoSocial, proc.processos]);
        }
        this.isLoading = false;
      }
    );
  }

  ngAfterViewInit(): void {
    this.anoForm
      .get('input')
      .valueChanges.pipe(debounceTime(300))
      .subscribe((value) => {
        if (value) {
          this.recarregar(+value);
        } else {
          this.ranking = [];
          this.data = [];
        }
      });
  }

  private recarregar(ano: number) {
    this.title = 'Ranking do Ano ' + ano;
    this.data = [];
    this.ranking = [];
    this.isLoading = true;
    this.processoService.ranking(ano).subscribe(
      (p) => (this.ranking = p),
      (err) => {
        this.isLoading = false;
        this.modal.openPadrao(err);
      },
      () => {
        for (let proc of this.ranking) {
          this.data.push([proc.fornecedor.razaoSocial, proc.processos]);
        }
        this.isLoading = false;
      }
    );
  }
}
