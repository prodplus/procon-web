import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve } from '@angular/router';
import { Observable } from 'rxjs';
import { ProcDesc } from '../models/dtos/proc-desc';
import { OperacaoService } from '../services/operacao.service';

@Injectable({ providedIn: 'root' })
export class AudienciaResolver implements Resolve<Observable<ProcDesc[]>> {
  constructor(private service: OperacaoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ProcDesc[]> {
    return this.service.porAudienciaDesc();
  }
}
