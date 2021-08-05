import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve } from '@angular/router';
import { Observable } from 'rxjs';
import { Page } from '../models/auxiliares/page';
import { AtendimentoDto } from '../models/dtos/atendimento-dto';
import { AtendimentoService } from '../services/atendimento.service';

@Injectable({ providedIn: 'root' })
export class ListaAtendimentosResolver
  implements Resolve<Observable<Page<AtendimentoDto>>>
{
  constructor(private service: AtendimentoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<Page<AtendimentoDto>> {
    return this.service.listar(0, 20);
  }
}
