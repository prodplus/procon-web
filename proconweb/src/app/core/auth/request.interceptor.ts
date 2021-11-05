import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ehExcecao } from 'src/app/utils/url-excecoes';
import { TokenService } from '../token/token.service';

@Injectable()
export class RequestInterceptor implements HttpInterceptor {
  constructor(private tokenService: TokenService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    if (this.tokenService.hasToken()) {
      const token = this.tokenService.getToken();
      if (!ehExcecao(req.url)) {
        req = req.clone({
          setHeaders: {
            Authorization: token,
          },
        });
      } else if (req.url.startsWith('https://www.receitaws.com.br')) {
        req = req.clone({
          setHeaders: {
            Authorization:
              '3c0ee2606911b798decc56ce9cd1a7030ef9a60a8ce1809e4723f90506166fe0',
          },
        });
      }
    }
    return next.handle(req);
  }
}
