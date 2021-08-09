import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve } from '@angular/router';
import { Observable } from 'rxjs';
import { Processo } from '../models/processo';
import { ProcessoService } from '../services/processo.service';

@Injectable({ providedIn: 'root' })
export class CadProcessoResolver implements Resolve<Observable<Processo>> {
  constructor(private service: ProcessoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<Processo> {
    return this.service.buscar(route.params['id']);
  }
}
