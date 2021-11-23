import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { debounceTime } from 'rxjs/operators';
import { Page } from 'src/app/models/auxiliares/page';
import { RespModal } from 'src/app/models/auxiliares/resp-modal';
import { Lei } from 'src/app/models/lei';
import { LeiService } from 'src/app/services/lei.service';
import { ModalComponent } from 'src/app/shared/modal/modal.component';
import { mensagemPadrao } from 'src/app/utils/mensagem-utils';

@Component({
  selector: 'app-lista-leis',
  templateUrl: './lista-leis.component.html',
  styleUrls: ['./lista-leis.component.css'],
})
export class ListaLeisComponent implements OnInit, AfterViewInit {
  isLoading = false;
  page: Page<Lei>;
  idLei: number;
  searchForm = this.builder.group({
    input: [''],
  });
  value: string;
  pagina = 1;
  @ViewChild('modal')
  modal: ModalComponent;

  constructor(
    private builder: FormBuilder,
    private leiService: LeiService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.page = this.route.snapshot.data['page'];
  }

  ngAfterViewInit(): void {
    this.searchForm
      .get('input')
      .valueChanges.pipe(debounceTime(300))
      .subscribe((value) => {
        if (value && value.length > 0) {
          this.value = value;
          this.recarregar(this.pagina);
        } else {
          this.value = null;
          this.pagina = 1;
          this.recarregar(this.pagina);
        }
      });
  }

  private recarregar(pagina: number) {
    if (this.value) {
      this.leiService.listarDP(this.value, pagina - 1, 20).subscribe(
        (p) => (this.page = p),
        (err) => {
          this.isLoading = false;
          this.modal.openPadrao(err);
        },
        () => (this.isLoading = false)
      );
    } else {
      this.leiService.listarP(pagina - 1, 20).subscribe(
        (p) => (this.page = p),
        (err) => {
          this.isLoading = false;
          this.modal.openPadrao(err);
        },
        () => (this.isLoading = false)
      );
    }
  }

  private excluir(id: number) {
    this.isLoading = true;
    this.leiService.excluir(id).subscribe({
      error: (err) => {
        this.isLoading = false;
        this.modal.openPadrao(err);
      },
      complete: () => {
        this.isLoading = false;
        this.recarregar(this.pagina);
      },
    });
  }

  mudaPagina(pagina: number) {
    this.pagina = pagina;
    this.recarregar(this.pagina);
  }

  chamaModal(id: number) {
    this.idLei = id;
    this.modal.open(
      mensagemPadrao(
        null,
        'warning',
        'Atenção!!',
        'EXCLUIR definitivamente a lei??'
      ),
      'e',
      true
    );
  }

  concordaModal(resp: RespModal) {
    if (resp.confirmou) this.excluir(this.idLei);
  }
}
