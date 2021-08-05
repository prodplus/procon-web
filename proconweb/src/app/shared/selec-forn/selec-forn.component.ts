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
import { Fornecedor } from 'src/app/models/fornecedor';
import { FornecedorService } from 'src/app/services/fornecedor.service';

@Component({
  selector: 'app-selec-forn',
  templateUrl: './selec-forn.component.html',
  styleUrls: ['./selec-forn.component.css'],
})
export class SelecFornComponent implements OnInit, AfterViewInit {
  searchForm: FormGroup;
  page: Page<Fornecedor> = new Page<Fornecedor>();
  iCheck = faCheck;
  @Output() selecao = new EventEmitter<Fornecedor>();
  @Output() novo = new EventEmitter<boolean>();

  constructor(
    private builder: FormBuilder,
    private fornecedorService: FornecedorService
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
          this.fornecedorService
            .listarParametro(value, 0, 5)
            .subscribe((p) => (this.page = p));
        }
      });
  }

  selecionarFornecedor(forn: Fornecedor) {
    this.selecao.emit(forn);
  }

  cadastroNovo() {
    this.novo.emit(true);
  }

  cancelar() {
    this.novo.emit(false);
  }
}
