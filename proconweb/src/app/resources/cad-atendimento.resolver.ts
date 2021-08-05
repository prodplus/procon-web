import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve } from '@angular/router';
import { Observable } from 'rxjs';
import { Atendimento } from '../models/atendimento';
import { AtendimentoService } from '../services/atendimento.service';

@Injectable({ providedIn: 'root' })
export class CadAtendimentoResolver
  implements Resolve<Observable<Atendimento>>
{
  constructor(private service: AtendimentoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<Atendimento> {
    return this.service.buscar(route.params['id']);
  }
}
