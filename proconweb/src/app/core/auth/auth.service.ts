import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap } from 'rxjs/operators';
import { TokenDto } from 'src/app/models/dtos/token-dto';
import { LoginForm } from 'src/app/models/forms/login-form';
import { environment } from 'src/environments/environment';
import { UserService } from '../user.service';

const URL = environment.url + '/auth';

@Injectable({ providedIn: 'root' })
export class AuthService {
  constructor(private http: HttpClient, private userService: UserService) {}

  authenticate(email: string, password: string) {
    return this.http
      .post<TokenDto>(`${URL}`, new LoginForm(email, password), {
        observe: 'response',
      })
      .pipe(
        tap((res) => {
          const authToken = res.body.tipo + ' ' + res.body.token;
          this.userService.setToken(authToken);
        })
      );
  }
}
