import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Atendimento } from '../models/atendimento';
import { Page } from '../models/auxiliares/page';
import { AtendimentoDto } from '../models/dtos/atendimento-dto';
import { AtendimentoForm } from '../models/forms/atendimento-form';

const URL = environment.url + '/atendimentos';

@Injectable({ providedIn: 'root' })
export class AtendimentoService {
  constructor(private http: HttpClient) {}

  salvar(atendimento: AtendimentoForm): Observable<AtendimentoDto> {
    return this.http.post<AtendimentoDto>(`${URL}`, atendimento);
  }

  atualizar(
    id: number,
    atendimento: AtendimentoForm
  ): Observable<AtendimentoDto> {
    return this.http.put<AtendimentoDto>(`${URL}/${id}`, atendimento);
  }

  buscar(id: number): Observable<Atendimento> {
    return this.http.get<Atendimento>(`${URL}/${id}`);
  }

  listar(pagina: number, quant: number): Observable<Page<AtendimentoDto>> {
    return this.http.get<Page<AtendimentoDto>>(
      `${URL}/listar/${pagina}/${quant}`
    );
  }

  listarParametro(
    parametro: string,
    pagina: number,
    quant: number
  ): Observable<Page<AtendimentoDto>> {
    return this.http.get<Page<AtendimentoDto>>(
      `${URL}/listar/${pagina}/${quant}/${parametro}`
    );
  }

  excluir(id: number): Observable<any> {
    return this.http.delete(`${URL}/${id}`);
  }

  atendimentosAno(): Observable<number> {
    return this.http.get<number>(`${URL}/atendimentosano`);
  }
}
