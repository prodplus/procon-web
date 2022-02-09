import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve } from '@angular/router';
import { Observable } from 'rxjs';
import { Page } from '../models/auxiliares/page';
import { Fiscalizacao } from '../models/fiscalizacao';
import { FiscalizacaoService } from '../services/fiscalizacao.service';

@Injectable({ providedIn: 'root' })
export class ListaFiscalizacaoResolver
  implements Resolve<Observable<Page<Fiscalizacao>>>
{
  constructor(private service: FiscalizacaoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<Page<Fiscalizacao>> {
    return this.service.listar(0, 20);
  }
}
