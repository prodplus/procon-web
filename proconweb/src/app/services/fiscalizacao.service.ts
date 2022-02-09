import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Page } from '../models/auxiliares/page';
import { Fiscalizacao } from '../models/fiscalizacao';

const URL = environment.url + '/fiscalizacoes';

@Injectable({ providedIn: 'root' })
export class FiscalizacaoService {
  constructor(private http: HttpClient) {}

  salvar(fiscalizacao: Fiscalizacao): Observable<Fiscalizacao> {
    return this.http.post<Fiscalizacao>(`${URL}`, fiscalizacao);
  }

  atualizar(id: number, fiscalizacao: Fiscalizacao): Observable<Fiscalizacao> {
    return this.http.put<Fiscalizacao>(`${URL}/${id}`, fiscalizacao);
  }

  buscar(id: number): Observable<Fiscalizacao> {
    return this.http.get<Fiscalizacao>(`${URL}/${id}`);
  }

  listar(pagina: number, quant: number): Observable<Page<Fiscalizacao>> {
    return this.http.get<Page<Fiscalizacao>>(
      `${URL}/listar/${pagina}/${quant}`
    );
  }

  listarR(
    razao: string,
    pagina: number,
    quant: number
  ): Observable<Page<Fiscalizacao>> {
    return this.http.get<Page<Fiscalizacao>>(
      `${URL}/listar/${pagina}/${quant}/${razao}`
    );
  }

  excluir(id: number) {
    return this.http.delete(`${URL}/${id}`);
  }
}
