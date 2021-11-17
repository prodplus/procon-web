import {
  AfterViewInit,
  Component,
  ElementRef,
  OnInit,
  ViewChild,
} from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { faSearch } from '@fortawesome/free-solid-svg-icons';
import { ProcessoDto } from 'src/app/models/dtos/processo-dto';
import { EnumService } from 'src/app/services/enum.service';
import { ProcessoService } from 'src/app/services/processo.service';
import { ModalComponent } from 'src/app/shared/modal/modal.component';
import { toDateApi } from 'src/app/utils/date-utils';

@Component({
  selector: 'app-relatorios',
  templateUrl: './relatorios.component.html',
  styleUrls: ['./relatorios.component.css'],
})
export class RelatoriosComponent implements OnInit, AfterViewInit {
  isLoading = false;
  processos: ProcessoDto[];
  iSearch = faSearch;
  exibControl = false;
  situacoes: string[];
  @ViewChild('btn')
  btnSearch: ElementRef<HTMLButtonElement>;
  @ViewChild('modal')
  modal: ModalComponent;
  form = this.builder.group({
    inicio: [null],
    fim: [null],
    situacao: [null, [Validators.required]],
  });

  constructor(
    private processoService: ProcessoService,
    private enumService: EnumService,
    private builder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.enumService.getSituacoes().subscribe((s) => (this.situacoes = s));
  }

  ngAfterViewInit() {
    this.btnSearch.nativeElement.disabled = true;
  }

  buscarProcessos() {
    this.isLoading = true;
    if (this.exibControl) {
      this.processoService
        .listarPorSituacaoData(
          this.form.get('situacao').value,
          toDateApi(new Date(this.form.get('inicio').value)),
          toDateApi(new Date(this.form.get('fim').value))
        )
        .subscribe(
          (p) => (this.processos = p),
          (err) => {
            this.isLoading = false;
            this.modal.openPadrao(err);
          },
          () => (this.isLoading = false)
        );
    } else {
      this.form.get('inicio').setValue(null);
      this.form.get('fim').setValue(null);
      this.processoService
        .listarPorSituacaoPuro(this.form.get('situacao').value)
        .subscribe(
          (p) => (this.processos = p),
          (err) => {
            this.isLoading = false;
            this.modal.openPadrao(err);
          },
          () => (this.isLoading = false)
        );
    }
  }

  onSelectSituacao(): boolean {
    switch (this.form.get('situacao').value) {
      case 'AUTUADO':
      case 'ENCERRADO':
      case 'RESOLVIDO':
      case 'NAO_RESOLVIDO':
      case 'INSUBSISTENTE':
        return true;
      default:
        return false;
    }
  }
}
