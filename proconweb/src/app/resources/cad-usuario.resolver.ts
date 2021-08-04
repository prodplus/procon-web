import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve } from '@angular/router';
import { Observable } from 'rxjs';
import { UsuarioDto } from '../models/dtos/usuario-dto';
import { UsuarioService } from '../services/usuario.service';

@Injectable({ providedIn: 'root' })
export class CadUsuarioResolver implements Resolve<Observable<UsuarioDto>> {
  constructor(private service: UsuarioService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<UsuarioDto> {
    return this.service.buscar(route.params['id']);
  }
}
