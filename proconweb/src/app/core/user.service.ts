import { Injectable } from '@angular/core';
import jwt_decode from 'jwt-decode';
import { BehaviorSubject } from 'rxjs';
import { UsuarioDto } from '../models/dtos/usuario-dto';
import { TokenService } from './token/token.service';

@Injectable({ providedIn: 'root' })
export class UserService {
  private userSubject = new BehaviorSubject<UsuarioDto>(null);
  private userName: string;
  private idUsuario: number;

  constructor(private tokenService: TokenService) {
    if (this.tokenService.hasToken()) {
      this.decodeAndNotify();
    }
  }

  setToken(token: string) {
    this.tokenService.setToken(token);
    this.decodeAndNotify();
  }

  getUserName(): string {
    return this.userName;
  }

  getIdUsuario(): number {
    return this.idUsuario;
  }

  getUser() {
    return this.userSubject.asObservable();
  }

  logout() {
    this.tokenService.removeToken();
    this.userSubject.next(null);
  }

  isLogged() {
    return !!this.userName;
  }

  private decodeAndNotify() {
    const token = this.tokenService.getToken();
    const user = jwt_decode(token) as UsuarioDto;
    this.userName = user.email;
    this.idUsuario = user.id;
    this.userSubject.next(user);
  }
}
