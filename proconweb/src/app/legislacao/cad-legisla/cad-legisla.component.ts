import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Dispositivo } from 'src/app/models/dispositivo';
import { Lei } from 'src/app/models/lei';
import { LeiService } from 'src/app/services/lei.service';
import { ModalComponent } from 'src/app/shared/modal/modal.component';

@Component({
  selector: 'app-cad-legisla',
  templateUrl: './cad-legisla.component.html',
  styleUrls: ['./cad-legisla.component.css'],
})
export class CadLegislaComponent implements OnInit {
  isLoading = false;
  form = this.builder.group({
    descricao: ['', [Validators.required]],
    sumula: ['', [Validators.required]],
  });
  itemForm = this.builder.group({
    descricao: ['', [Validators.required]],
  });
  lei: Lei;
  dispositivos: Dispositivo[];
  dispoEditando: Dispositivo;
  @ViewChild('modal')
  modal: ModalComponent;

  constructor(
    private leiService: LeiService,
    private builder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((values) => {
      if (values && values.get('id')) {
        this.lei = this.route.snapshot.data['lei'];
        this.carregaFormulario(this.lei);
        this.carregaDispositivos(this.lei.id);
      }
    });
  }

  private carregaFormulario(l: Lei) {
    this.form.patchValue({
      descricao: l.descricao,
      sumula: l.sumula,
    });
  }

  private carregaLei(): Lei {
    return new Lei(
      this.lei?.id ? this.lei.id : null,
      this.form.get('descricao').value,
      this.form.get('sumula').value
    );
  }

  carregaDispositivo(): Dispositivo {
    return new Dispositivo(
      this.dispoEditando?.id ? this.dispoEditando.id : null,
      this.itemForm.get('descricao').value,
      this.lei
    );
  }

  carregaDispositivos(id: number) {
    this.leiService
      .listarDispositivos(id)
      .subscribe((d) => (this.dispositivos = d));
  }

  salvar() {
    if (this.lei?.id) {
      this.leiService.atualizar(this.lei.id, this.carregaLei()).subscribe({
        error: (err) => {
          this.isLoading = false;
          this.modal.openPadrao(err);
        },
        complete: () => {
          this.isLoading = false;
          this.router.navigate(['/legislacao']);
        },
      });
    } else {
      this.leiService.salvar(this.carregaLei()).subscribe({
        error: (err) => {
          this.isLoading = false;
          this.modal.openPadrao(err);
        },
        complete: () => {
          this.isLoading = false;
          this.router.navigate(['/legislacao']);
        },
      });
    }
  }

  inserirDispositivo() {
    if (this.lei?.id) {
      this.leiService.salvarDispo(this.carregaDispositivo()).subscribe({
        error: (err) => this.modal.openPadrao(err),
        complete: () => {
          this.itemForm.reset();
          this.carregaDispositivos(this.lei.id);
        },
      });
    }
  }
}
