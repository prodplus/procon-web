import {
  AfterViewInit,
  Component,
  EventEmitter,
  OnInit,
  Output,
} from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { faCheck } from '@fortawesome/free-solid-svg-icons';
import { debounceTime } from 'rxjs/operators';
import { Page } from 'src/app/models/auxiliares/page';
import { Consumidor } from 'src/app/models/consumidor';
import { ConsumidorService } from 'src/app/services/consumidor.service';
import { getMascaraCadastro } from 'src/app/utils/mask-utils';

@Component({
  selector: 'app-selec-cons',
  templateUrl: './selec-cons.component.html',
  styleUrls: ['./selec-cons.component.css'],
})
export class SelecConsComponent implements OnInit, AfterViewInit {
  searchForm: FormGroup;
  page: Page<Consumidor> = new Page<Consumidor>();
  iCheck = faCheck;
  @Output() selecao = new EventEmitter<Consumidor>();
  @Output() novo = new EventEmitter<boolean>();

  constructor(
    private builder: FormBuilder,
    private consumidorService: ConsumidorService
  ) {}

  ngOnInit(): void {
    this.searchForm = this.builder.group({
      input: [''],
    });
  }

  ngAfterViewInit(): void {
    this.searchForm
      .get('input')
      .valueChanges.pipe(debounceTime(300))
      .subscribe((value) => {
        if (value && value.length > 2) {
          this.consumidorService
            .listarParametro(value, 0, 5)
            .subscribe((p) => (this.page = p));
        } else {
          this.page = new Page<Consumidor>();
        }
      });
  }

  selecionarConsumidor(cons: Consumidor) {
    this.selecao.emit(cons);
  }

  cadastrarNovo() {
    this.novo.emit(true);
  }

  cancelar() {
    this.novo.emit(false);
  }

  mascaraCadastro(tipo: string): string {
    return getMascaraCadastro(tipo);
  }
}
