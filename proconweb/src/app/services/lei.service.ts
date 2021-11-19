import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Page } from '../models/auxiliares/page';
import { Dispositivo } from '../models/dispositivo';
import { Lei } from '../models/lei';

const URL = environment.url + '/leis';

@Injectable({ providedIn: 'root' })
export class LeiService {
  constructor(private http: HttpClient) {}

  salvar(lei: Lei): Observable<Lei> {
    return this.http.post<Lei>(`${URL}`, lei);
  }

  atualizar(id: number, lei: Lei): Observable<Lei> {
    return this.http.put<Lei>(`${URL}/${id}`, lei);
  }

  buscar(id: number): Observable<Lei> {
    return this.http.get<Lei>(`${URL}/${id}`);
  }

  listar(): Observable<Lei[]> {
    return this.http.get<Lei[]>(`${URL}/listar`);
  }

  listarP(pagina: number, quant: number): Observable<Page<Lei>> {
    return this.http.get<Page<Lei>>(`${URL}/listar/${pagina}/${quant}`);
  }

  listarD(descricao: string): Observable<Lei[]> {
    return this.http.get<Lei[]>(`${URL}/listar/${descricao}`);
  }

  listarDP(
    descricao: string,
    pagina: number,
    quant: number
  ): Observable<Page<Lei>> {
    return this.http.get<Page<Lei>>(
      `${URL}/listar/${pagina}/${quant}/${descricao}`
    );
  }

  excluir(id: number) {
    return this.http.delete(`${URL}/${id}`);
  }

  salvarDispo(dispositivo: Dispositivo) {
    return this.http.post<Dispositivo>(`${URL}/dispositivo`, dispositivo);
  }

  excluirDispo(id: number) {
    return this.http.delete(`${URL}/dispositivo/${id}`);
  }

  listarDispositivos(idLei: number): Observable<Dispositivo[]> {
    return this.http.get<Dispositivo[]>(`${URL}/dispositivos/${idLei}`);
  }
}
