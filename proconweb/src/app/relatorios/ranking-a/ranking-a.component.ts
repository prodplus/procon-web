import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { debounceTime } from 'rxjs/operators';
import { FornecedorNro } from 'src/app/models/auxiliares/fornecedor-nro';
import { AtendimentoService } from 'src/app/services/atendimento.service';
import { ModalComponent } from 'src/app/shared/modal/modal.component';

@Component({
  selector: 'app-ranking-a',
  templateUrl: './ranking-a.component.html',
  styleUrls: ['./ranking-a.component.css'],
})
export class RankingAComponent implements OnInit, AfterViewInit {
  isLoading = false;
  ranking: FornecedorNro[];
  anos: number[] = [];
  anoForm = this.builder.group({
    input: [new Date().getUTCFullYear()],
  });
  @ViewChild('modal')
  modal: ModalComponent;

  constructor(
    private atendimentoService: AtendimentoService,
    private builder: FormBuilder
  ) {}

  ngOnInit(): void {
    const ano: number = new Date().getUTCFullYear();
    for (let i = 0; i <= 5; i++) this.anos.push(ano - i);
    this.isLoading = true;
    this.atendimentoService.ranking(ano).subscribe(
      (p) => (this.ranking = p),
      (err) => {
        this.isLoading = false;
        this.modal.openPadrao(err);
      },
      () => (this.isLoading = false)
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
        }
      });
  }

  private recarregar(ano: number) {
    this.atendimentoService.ranking(ano).subscribe(
      (p) => (this.ranking = p),
      (err) => {
        this.isLoading = false;
        this.modal.openPadrao(err);
      },
      () => (this.isLoading = false)
    );
  }
}
