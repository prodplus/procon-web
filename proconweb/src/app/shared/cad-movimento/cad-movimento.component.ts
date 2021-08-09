import {
  AfterViewInit,
  Component,
  ElementRef,
  EventEmitter,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { faWindowClose } from '@fortawesome/free-solid-svg-icons';
import { Movimento } from 'src/app/models/auxiliares/movimento';
import { EnumService } from 'src/app/services/enum.service';
import { toDateApi } from 'src/app/utils/date-utils';

@Component({
  selector: 'app-cad-movimento',
  templateUrl: './cad-movimento.component.html',
  styleUrls: ['./cad-movimento.component.css'],
})
export class CadMovimentoComponent implements OnInit, AfterViewInit {
  @Output() cadMovimento = new EventEmitter<Movimento>();
  iWindowClose = faWindowClose;
  situacoes: string[];
  @ViewChild('inputInicio')
  inputInicio: ElementRef<HTMLInputElement>;
  movimentoForm = this.builder.group({
    data: ['', [Validators.required]],
    de: [null, [Validators.required]],
    para: [null, [Validators.required]],
    averbacao: [''],
    auxD: [null],
    auxT: [null],
  });

  constructor(private builder: FormBuilder, private enumService: EnumService) {}

  ngOnInit(): void {
    this.enumService.getSituacoes().subscribe((s) => (this.situacoes = s));
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.inputInicio.nativeElement.focus();
    }, 100);
  }

  cancelar() {
    this.cadMovimento.emit(null);
  }

  private carregaMovimento(): Movimento {
    return new Movimento(
      toDateApi(new Date(this.movimentoForm.get('data').value)),
      this.movimentoForm.get('de').value,
      this.movimentoForm.get('para').value,
      this.movimentoForm.get('averbacao').value,
      this.movimentoForm.get('auxD').value
        ? toDateApi(new Date(this.movimentoForm.get('auxD').value))
        : null,
      this.movimentoForm.get('auxT').value
    );
  }

  salvar() {
    this.cadMovimento.emit(this.carregaMovimento());
  }
}
