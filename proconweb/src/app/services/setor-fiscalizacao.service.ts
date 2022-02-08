import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { SetorFiscalizacao } from '../models/setor-fiscalizacao';

const URL = environment.url + '/setor_fiscalizacao';

@Injectable({ providedIn: 'root' })
export class SetorFiscalizacaoService {
  constructor(private http: HttpClient) {}

  salvar(setor: SetorFiscalizacao): Observable<SetorFiscalizacao> {
    return this.http.post<SetorFiscalizacao>(`${URL}`, setor);
  }

  atualizar(
    id: number,
    setor: SetorFiscalizacao
  ): Observable<SetorFiscalizacao> {
    return this.http.put<SetorFiscalizacao>(`${URL}/${id}`, setor);
  }

  buscar(id: number): Observable<SetorFiscalizacao> {
    return this.http.get<SetorFiscalizacao>(`${URL}/${id}`);
  }

  listar(): Observable<SetorFiscalizacao[]> {
    return this.http.get<SetorFiscalizacao[]>(`${URL}/listar`);
  }

  listarD(parametro: string): Observable<SetorFiscalizacao[]> {
    return this.http.get<SetorFiscalizacao[]>(`${URL}/listar/${parametro}`);
  }

  excluir(id: number) {
    return this.http.delete(`${URL}/${id}`);
  }
}
