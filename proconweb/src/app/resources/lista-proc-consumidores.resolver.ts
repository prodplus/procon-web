import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve } from '@angular/router';
import { Observable } from 'rxjs';
import { Page } from '../models/auxiliares/page';
import { ProcessoDto } from '../models/dtos/processo-dto';
import { ProcessoService } from '../services/processo.service';

@Injectable({ providedIn: 'root' })
export class ListaProcConsumidoresResolver
  implements Resolve<Observable<Page<ProcessoDto>>>
{
  constructor(private service: ProcessoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<Page<ProcessoDto>> {
    return this.service.listarPorConsumidor(route.params['param'], 0, 20);
  }
}
