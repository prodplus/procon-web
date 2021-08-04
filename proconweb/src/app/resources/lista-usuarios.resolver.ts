import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve } from '@angular/router';
import { Observable } from 'rxjs';
import { Page } from '../models/auxiliares/page';
import { UsuarioDto } from '../models/dtos/usuario-dto';
import { UsuarioService } from '../services/usuario.service';

@Injectable({ providedIn: 'root' })
export class ListaUsuariosResolver
  implements Resolve<Observable<Page<UsuarioDto>>>
{
  constructor(private service: UsuarioService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<Page<UsuarioDto>> {
    return this.service.listar(true, 0, 20);
  }
}
