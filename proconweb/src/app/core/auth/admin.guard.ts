import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { Observable } from 'rxjs';
import { UsuarioDto } from 'src/app/models/dtos/usuario-dto';
import { UserService } from '../user.service';

@Injectable({ providedIn: 'root' })
export class AdminGuard implements CanActivate {
  user$: Observable<UsuarioDto>;
  role: string;

  constructor(private userService: UserService, private router: Router) {
    this.user$ = this.userService.getUser();
    this.user$.subscribe((u) =>
      u ? (this.role = u.perfil) : (this.role = '')
    );
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (this.userService.isLogged() && this.role == 'ROLE_ADMIN') return true;
    else {
      this.router.navigate(['']);
      return false;
    }
  }
}
