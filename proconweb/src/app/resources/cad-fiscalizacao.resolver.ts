import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve } from '@angular/router';
import { Observable } from 'rxjs';
import { Fiscalizacao } from '../models/fiscalizacao';
import { FiscalizacaoService } from '../services/fiscalizacao.service';

@Injectable({ providedIn: 'root' })
export class CadFiscalizacaoResolver
  implements Resolve<Observable<Fiscalizacao>>
{
  constructor(private service: FiscalizacaoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<Fiscalizacao> {
    return this.service.buscar(route.params['id']);
  }
}
