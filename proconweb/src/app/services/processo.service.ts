import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { FornecedorNro } from '../models/auxiliares/fornecedor-nro';
import { Page } from '../models/auxiliares/page';
import { ProcessoDto } from '../models/dtos/processo-dto';
import { ProcessoForm } from '../models/forms/processo-form';
import { Processo } from '../models/processo';

const URL = environment.url + '/processos';

@Injectable({ providedIn: 'root' })
export class ProcessoService {
  constructor(private http: HttpClient) {}

  salvar(processo: ProcessoForm): Observable<ProcessoDto> {
    return this.http.post<ProcessoDto>(`${URL}`, processo);
  }

  atualizar(id: number, processo: ProcessoForm): Observable<ProcessoDto> {
    return this.http.put<ProcessoDto>(`${URL}/${id}`, processo);
  }

  buscar(id: number): Observable<Processo> {
    return this.http.get<Processo>(`${URL}/${id}`);
  }

  listar(pagina: number, quant: number): Observable<Page<ProcessoDto>> {
    return this.http.get<Page<ProcessoDto>>(`${URL}/listar/${pagina}/${quant}`);
  }

  listarPorAutos(
    autos: string,
    pagina: number,
    quant: number
  ): Observable<Page<ProcessoDto>> {
    return this.http.get<Page<ProcessoDto>>(
      `${URL}/listar_autos/${pagina}/${quant}/${autos}`
    );
  }

  listarPorConsumidor(
    parametro: string,
    pagina: number,
    quant: number
  ): Observable<Page<ProcessoDto>> {
    return this.http.get<Page<ProcessoDto>>(
      `${URL}/listar_consumidor/${pagina}/${quant}/${parametro}`
    );
  }

  listarPorFornecedor(
    parametro: string,
    pagina: number,
    quant: number
  ): Observable<Page<ProcessoDto>> {
    return this.http.get<Page<ProcessoDto>>(
      `${URL}/listar_fornecedor/${pagina}/${quant}/${parametro}`
    );
  }

  listarPorSituacao(
    situacao: string,
    pagina: number,
    quant: number
  ): Observable<Page<ProcessoDto>> {
    return this.http.get<Page<ProcessoDto>>(
      `${URL}/listar_situacao/${pagina}/${quant}/${situacao}`
    );
  }

  excluir(id: number): Observable<any> {
    return this.http.delete(`${URL}/${id}`);
  }

  ranking(ano: number): Observable<FornecedorNro[]> {
    return this.http.get<FornecedorNro[]>(`${URL}/ranking/${ano}`);
  }
}
