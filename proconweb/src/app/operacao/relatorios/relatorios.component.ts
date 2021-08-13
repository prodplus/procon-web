import { Component, OnInit, ViewChild } from '@angular/core';
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
export class RelatoriosComponent implements OnInit {
  isLoading = false;
  processos: ProcessoDto[];
  iSearch = faSearch;
  situacoes: string[];
  @ViewChild('modal')
  modal: ModalComponent;
  form = this.builder.group({
    inicio: [null, [Validators.required]],
    fim: [null, [Validators.required]],
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

  buscarProcessos() {
    this.isLoading = true;
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
        () => {
          this.form.get('situacao').disable();
          this.form.get('inicio').disable();
          this.form.get('fim').disable();
          this.isLoading = false;
        }
      );
  }
}
