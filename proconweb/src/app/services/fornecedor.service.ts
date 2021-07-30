import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Page } from '../models/auxiliares/page';
import { Fornecedor } from '../models/fornecedor';

const URL = environment.url + '/fornecedores';

@Injectable({ providedIn: 'root' })
export class FornecedorService {
  constructor(private http: HttpClient) {}

  salvar(fornecedor: Fornecedor): Observable<Fornecedor> {
    return this.http.post<Fornecedor>(`${URL}`, fornecedor);
  }

  atualizar(id: number, fornecedor: Fornecedor): Observable<Fornecedor> {
    return this.http.put<Fornecedor>(`${URL}/${id}`, fornecedor);
  }

  buscar(id: number): Observable<Fornecedor> {
    return this.http.get<Fornecedor>(`${URL}/${id}`);
  }

  listar(pagina: number, quant: number): Observable<Page<Fornecedor>> {
    return this.http.get<Page<Fornecedor>>(`${URL}/listar/${pagina}/${quant}`);
  }

  listarParametro(
    parametro: string,
    pagina: number,
    quant: number
  ): Observable<Page<Fornecedor>> {
    return this.http.get<Page<Fornecedor>>(
      `${URL}/listar/${pagina}/${quant}/${parametro}`
    );
  }

  excluir(id: number): Observable<any> {
    return this.http.delete(`${URL}/${id}`);
  }

  fantasiaExiste(fantasia: string): Observable<boolean> {
    return this.http.get<boolean>(`${URL}/fantasia/${fantasia}`);
  }

  cnpjExiste(cnpj: string): Observable<boolean> {
    return this.http.get<boolean>(`${URL}/cnpj/${cnpj}`);
  }
}
