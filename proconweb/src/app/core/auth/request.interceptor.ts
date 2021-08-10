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
      }
    }
    return next.handle(req);
  }
}
