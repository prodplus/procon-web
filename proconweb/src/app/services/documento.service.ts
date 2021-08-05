import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

const URL = environment.url + '/documentos';

@Injectable({ providedIn: 'root' })
export class DocumentoService {
  constructor(private http: HttpClient) {}

  atendimento(id: number): Observable<Blob> {
    return this.http.get(`${URL}/atendimento/${id}`, { responseType: 'blob' });
  }
}
