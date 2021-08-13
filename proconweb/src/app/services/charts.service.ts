import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GoogleChartsConfig } from 'angular-google-charts';
import { Observable, ReplaySubject } from 'rxjs';
import { take } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class GoogleChartsConfigService {
  private configSubject = new ReplaySubject<GoogleChartsConfig>(1);
  readonly config$ = this.configSubject.asObservable();

  constructor(private http: HttpClient) {}

  loadLazyConfigValue(): void {
    this.http
      .post('https://special.config.api.com/getchartsconfig', {})
      .pipe(take(1))
      .subscribe((config) => this.configSubject.next(config));
  }
}

export function googleChartsConfigFactory(
  configService: GoogleChartsConfigService
): Observable<GoogleChartsConfig> {
  return configService.config$;
}
