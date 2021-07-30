import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Page } from '../models/auxiliares/page';
import { Consumidor } from '../models/consumidor';

const URL = environment.url + '/consumidores';

@Injectable({ providedIn: 'root' })
export class ConsumidorService {
  constructor(private http: HttpClient) {}

  salvar(consumidor: Consumidor): Observable<Consumidor> {
    return this.http.post<Consumidor>(`${URL}`, consumidor);
  }

  atualizar(id: number, consumidor: Consumidor): Observable<Consumidor> {
    return this.http.put<Consumidor>(`${URL}/${id}`, consumidor);
  }

  buscar(id: number): Observable<Consumidor> {
    return this.http.get<Consumidor>(`${URL}/${id}`);
  }

  listar(pagina: number, quant: number): Observable<Page<Consumidor>> {
    return this.http.get<Page<Consumidor>>(`${URL}/listar/${pagina}/${quant}`);
  }

  listarParametro(
    parametro: string,
    pagina: number,
    quant: number
  ): Observable<Page<Consumidor>> {
    return this.http.get<Page<Consumidor>>(
      `${URL}/listar/${pagina}/${quant}/${parametro}`
    );
  }

  excluir(id: number): Observable<any> {
    return this.http.delete(`${URL}/${id}`);
  }

  consumidorExiste(cadastro: string): Observable<boolean> {
    return this.http.get<boolean>(`${URL}/existe/${cadastro}`);
  }
}
