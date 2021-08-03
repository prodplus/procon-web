import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve } from '@angular/router';
import { Observable } from 'rxjs';
import { Page } from '../models/auxiliares/page';
import { Consumidor } from '../models/consumidor';
import { ConsumidorService } from '../services/consumidor.service';

@Injectable({ providedIn: 'root' })
export class ListaConsumidoresResolver
  implements Resolve<Observable<Page<Consumidor>>>
{
  constructor(private service: ConsumidorService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<Page<Consumidor>> {
    return this.service.listar(0, 20);
  }
}
