package br.com.procon.utils;

import java.util.List;

import br.com.procon.models.auxiliares.Movimento;
import br.com.procon.models.enums.Situacao;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
public class ProcessoUtils {

	/**
	 * Obtém a última movimentação para alimentar a nova.
	 * 
	 * @param movimentacao
	 * @return
	 */
	public static Situacao handleSituacao(List<Movimento> movimentacao) {
		if (movimentacao.isEmpty())
			return Situacao.AUTUADO;
		Situacao para = movimentacao.get(0).getPara();
		if (para.equals(Situacao.DESPACHO) || para.equals(Situacao.AUTUADO))
			return Situacao.AUTUADO;
		return para;
	}

}
