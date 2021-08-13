import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Movimento } from '../models/auxiliares/movimento';

const URL = environment.url + '/documentos';

@Injectable({ providedIn: 'root' })
export class DocumentoService {
  constructor(private http: HttpClient) {}

  atendimento(id: number): Observable<Blob> {
    return this.http.get(`${URL}/atendimento/${id}`, { responseType: 'blob' });
  }

  inicial(id: number): Observable<Blob> {
    return this.http.get(`${URL}/inicial/${id}`, { responseType: 'blob' });
  }

  notDezDias(idProcesso: number, idFornecedor: number): Observable<Blob> {
    return this.http.get(`${URL}/not_dez_dias/${idProcesso}/${idFornecedor}`, {
      responseType: 'blob',
    });
  }

  notCincoDias(idProcesso: number, idFornecedor: number): Observable<Blob> {
    return this.http.get(
      `${URL}/not_cinco_dias/${idProcesso}/${idFornecedor}`,
      {
        responseType: 'blob',
      }
    );
  }

  notImpugnacao(idProcesso: number, idFornecedor: number): Observable<Blob> {
    return this.http.get(
      `${URL}/not_impugnacao/${idProcesso}/${idFornecedor}`,
      {
        responseType: 'blob',
      }
    );
  }

  notMulta(idProcesso: number, idFornecedor: number): Observable<Blob> {
    return this.http.get(`${URL}/not_multa/${idProcesso}/${idFornecedor}`, {
      responseType: 'blob',
    });
  }

  oficio(id: number): Observable<Blob> {
    return this.http.get(`${URL}/oficio/${id}`, { responseType: 'blob' });
  }

  notConsumidor(id: number): Observable<Blob> {
    return this.http.get(`${URL}/not_consumidor/${id}`, {
      responseType: 'blob',
    });
  }

  convAudCons(id: number, movimento: Movimento): Observable<Blob> {
    return this.http.put(`${URL}/conv_aud_cons/${id}`, movimento, {
      responseType: 'blob',
    });
  }

  convAudForn(id: number, movimento: Movimento): Observable<Blob> {
    return this.http.put(`${URL}/conv_aud_forn/${id}`, movimento, {
      responseType: 'blob',
    });
  }
}
