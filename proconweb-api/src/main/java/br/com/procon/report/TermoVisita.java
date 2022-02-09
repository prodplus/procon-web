package br.com.procon.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.procon.models.Fiscalizacao;
import br.com.procon.models.auxiliares.ItemFiscalizacao;
import br.com.procon.utils.LocalDateUtils;
import br.com.procon.utils.MascarasUtils;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
public class TermoVisita {

	public static ByteArrayInputStream gerar(Fiscalizacao fiscalizacao) {
		try {
			Document document = new Document(PageSize.A4);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, output);
			document.setMargins(65, 30, 10, 40);
			document.open();

			// cria fontes e espaços
			Font titFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
			Font intFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
			Font minFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
			Paragraph espaco = new Paragraph(new Phrase(" ", intFont));

			// cabeçalho
			Paragraph cabecalho = new Paragraph("DIRETORIA DE PROTEÇÃO E DEFESA DO CONSUMIDOR",
					titFont);
			cabecalho.setAlignment(Element.ALIGN_CENTER);
			document.add(cabecalho);
			cabecalho = new Paragraph("PROCON - PATO BRANCO", titFont);
			cabecalho.setAlignment(Element.ALIGN_CENTER);
			document.add(cabecalho);
			cabecalho = new Paragraph(
					"Rua Araribóia, nº 185, Centro, Pato Branco, PR, CEP: 85501-260 - fone (46) 3902-1289 / (46) 3902-1325 / Whatsapp: (46) 99107-6394",
					minFont);
			cabecalho.setAlignment(Element.ALIGN_CENTER);
			document.add(cabecalho);
			cabecalho = new Paragraph("Email: procon@patobranco.pr.gov.br", minFont);
			cabecalho.setAlignment(Element.ALIGN_CENTER);
			document.add(cabecalho);

			for (int i = 0; i < 2; i++)
				document.add(espaco);

			if (fiscalizacao.getId() != null) {
				Paragraph numero = new Paragraph(
						String.format("Termo de Fiscalização nº %04d", fiscalizacao.getId()),
						intFont);
				numero.setAlignment(Element.ALIGN_LEFT);
				document.add(numero);
			}
			Paragraph identificacao = new Paragraph(
					"Fornecedor: " + fiscalizacao.getFornecedor().getRazaoSocial() + " ("
							+ fiscalizacao.getFornecedor().getFantasia() + ")",
					intFont);
			identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(identificacao);
			identificacao = new Paragraph(
					"Endereço: " + fiscalizacao.getFornecedor().getEndereco().getLogradouro() + ", "
							+ fiscalizacao.getFornecedor().getEndereco().getNumero() + ", "
							+ fiscalizacao.getFornecedor().getEndereco().getComplemento() + ", "
							+ fiscalizacao.getFornecedor().getEndereco().getBairro() + ", "
							+ fiscalizacao.getFornecedor().getEndereco().getMunicipio() + ", "
							+ fiscalizacao.getFornecedor().getEndereco().getUf() + ", CEP "
							+ MascarasUtils.format("#####-###",
									fiscalizacao.getFornecedor().getEndereco().getCep()),
					intFont);
			identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(identificacao);

			identificacao = new Paragraph("CNPJ: " + MascarasUtils.format("##.###.###/####-##",
					fiscalizacao.getFornecedor().getCnpj()), intFont);
			identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(identificacao);

			identificacao = new Paragraph("Atividade: " + fiscalizacao.getSetor().getDescricao(),
					intFont);
			identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(identificacao);

			document.add(espaco);

			Paragraph titulo = new Paragraph("RELATÓRIO DA VISITA", intFont);
			titulo.setAlignment(Element.ALIGN_CENTER);
			document.add(titulo);

			document.add(espaco);

			Paragraph introducao = new Paragraph(String.format(
					"Às %02d:%02d horas do dia %02d do mês de %s, do ano de %04d, no exercício"
							+ " de sua fiscalização de que trata a Lei 8.078/1990, "
							+ "regulamentada pelo Decreto Federal 2.181, de 21 de março de "
							+ "1997 e Decreto Municipal 5.192 de 21 de novembro de 2007, "
							+ "realizei a visita no estabelecimento acima.",
					fiscalizacao.getData().getHour(), fiscalizacao.getData().getMinute(),
					fiscalizacao.getData().getDayOfMonth(),
					LocalDateUtils.getMesExtenso(fiscalizacao.getData().getMonthValue()),
					fiscalizacao.getData().getYear()));
			introducao.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(introducao);

			document.add(espaco);

			int cont = 0;
			for (ItemFiscalizacao item : fiscalizacao.getItens()) {
				++cont;
				Paragraph itemPrint = new Paragraph(
						String.format("%02d) %s: %s", cont, item.getItem(), item.getObservacao()));
				itemPrint.setAlignment(Element.ALIGN_JUSTIFIED);
				document.add(itemPrint);
			}

			document.add(espaco);

			Paragraph observacao = new Paragraph(fiscalizacao.getObservacoes());
			observacao.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(observacao);

			document.add(espaco);

			Paragraph notificacao = new Paragraph(
					"O Fornecedor fica, neste ato, notificado para regularizar as eventuais "
							+ "irregularidades acima descritas, num prazo de 5 (cinco) dias, "
							+ "a contar da assinatura deste Termo.");
			notificacao.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(notificacao);

			for (int i = 0; i < 2; i++)
				document.add(espaco);

			Paragraph conteudo = new Paragraph(
					"Representante Fornecedor                                            PROCON",
					intFont);
			conteudo.setAlignment(Element.ALIGN_CENTER);
			document.add(conteudo);

			conteudo = new Paragraph("MARLON FERNANDO GARCIA - Fiscal", minFont);
			conteudo.setAlignment(Element.ALIGN_RIGHT);
			document.add(conteudo);

			document.close();

			return new ByteArrayInputStream(output.toByteArray());
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
