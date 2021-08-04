import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve } from '@angular/router';
import { Observable } from 'rxjs';
import { Page } from '../models/auxiliares/page';
import { Fornecedor } from '../models/fornecedor';
import { FornecedorService } from '../services/fornecedor.service';

@Injectable({ providedIn: 'root' })
export class ListaFornecedoresResolver
  implements Resolve<Observable<Page<Fornecedor>>>
{
  constructor(private service: FornecedorService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<Page<Fornecedor>> {
    return this.service.listar(0, 20);
  }
}
