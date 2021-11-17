import { ProcessoDto } from '../dtos/processo-dto';
import { Movimento } from './movimento';

export interface ProcessoMovimentacao {
  processo: ProcessoDto;
  movimento: Movimento;
}
