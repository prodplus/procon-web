import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve } from '@angular/router';
import { Observable } from 'rxjs';
import { Page } from '../models/auxiliares/page';
import { Lei } from '../models/lei';
import { LeiService } from '../services/lei.service';

@Injectable({ providedIn: 'root' })
export class ListaLeisResolver implements Resolve<Observable<Page<Lei>>> {
  constructor(private service: LeiService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<Page<Lei>> {
    return this.service.listarP(0, 20);
  }
}
