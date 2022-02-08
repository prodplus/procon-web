import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve } from '@angular/router';
import { Observable } from 'rxjs';
import { SetorFiscalizacao } from '../models/setor-fiscalizacao';
import { SetorFiscalizacaoService } from '../services/setor-fiscalizacao.service';

@Injectable({ providedIn: 'root' })
export class CadSetorResolver
  implements Resolve<Observable<SetorFiscalizacao>>
{
  constructor(private service: SetorFiscalizacaoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<SetorFiscalizacao> {
    return this.service.buscar(route.params['id']);
  }
}
