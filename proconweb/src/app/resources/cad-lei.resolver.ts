import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve } from '@angular/router';
import { Observable } from 'rxjs';
import { Lei } from '../models/lei';
import { LeiService } from '../services/lei.service';

@Injectable({ providedIn: 'root' })
export class CadLeiResolver implements Resolve<Observable<Lei>> {
  constructor(private leiService: LeiService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<Lei> {
    return this.leiService.buscar(route.params['id']);
  }
}
