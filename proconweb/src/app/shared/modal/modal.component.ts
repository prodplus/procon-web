import {
  Component,
  ElementRef,
  EventEmitter,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { faWindowClose } from '@fortawesome/free-solid-svg-icons';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { RespModal } from 'src/app/models/auxiliares/resp-modal';
import { Mensagem, mensagemPadrao } from 'src/app/utils/mensagem-utils';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css'],
})
export class ModalComponent implements OnInit {
  mensagem: Mensagem;
  confirmar: boolean;
  resp: RespModal;
  iClose = faWindowClose;
  @Output() sim = new EventEmitter<RespModal>();
  @ViewChild('modal')
  modal: ElementRef;

  constructor(private modalService: NgbModal) {}

  ngOnInit(): void {}

  fechaModal() {
    this.modalService.dismissAll();
  }

  open(mensagem: Mensagem, tipo: string, confirmar: boolean) {
    this.mensagem = mensagem;
    this.resp = new RespModal(false, tipo);
    this.confirmar = confirmar;
    this.modalService.open(this.modal);
  }

  openPadrao(err) {
    this.mensagem = mensagemPadrao(err, 'danger', 'Erro!', '');
    this.confirmar = false;
    this.modalService.open(this.modal);
  }

  concordar() {
    this.resp.confirmou = true;
    this.sim.emit(this.resp);
    this.fechaModal();
  }
}
