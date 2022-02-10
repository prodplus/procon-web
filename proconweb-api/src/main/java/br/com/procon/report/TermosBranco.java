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

import br.com.procon.models.Fornecedor;
import br.com.procon.models.SetorFiscalizacao;
import br.com.procon.utils.MascarasUtils;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
public class TermosBranco {

	public static ByteArrayInputStream gerar(Fornecedor fornecedor, SetorFiscalizacao setor) {
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

			Paragraph identificacao = new Paragraph("Fornecedor: " + fornecedor.getRazaoSocial()
					+ " (" + fornecedor.getFantasia() + ")", intFont);
			identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(identificacao);
			identificacao = new Paragraph(
					"Endereço: " + fornecedor.getEndereco().getLogradouro() + ", "
							+ fornecedor.getEndereco().getNumero() + ", "
							+ fornecedor.getEndereco().getComplemento() + ", "
							+ fornecedor.getEndereco().getBairro() + ", "
							+ fornecedor.getEndereco().getMunicipio() + ", "
							+ fornecedor.getEndereco().getUf() + ", CEP "
							+ MascarasUtils.format("#####-###", fornecedor.getEndereco().getCep()),
					intFont);
			identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(identificacao);

			identificacao = new Paragraph(
					"CNPJ: " + MascarasUtils.format("##.###.###/####-##", fornecedor.getCnpj()),
					intFont);
			identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(identificacao);

			identificacao = new Paragraph("Atividade: " + setor.getDescricao(), intFont);
			identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(identificacao);

			document.add(espaco);

			Paragraph titulo = new Paragraph("RELATÓRIO DA VISITA", intFont);
			titulo.setAlignment(Element.ALIGN_CENTER);
			document.add(titulo);

			document.add(espaco);

			Paragraph introducao = new Paragraph(
					"Às _____:_____ horas do dia _____ do mês de _______________, "
							+ "do ano de __________, no exercício"
							+ " de sua fiscalização de que trata a Lei 8.078/1990, "
							+ "regulamentada pelo Decreto Federal 2.181, de 21 de março de "
							+ "1997 e Decreto Municipal 5.192 de 21 de novembro de 2007, "
							+ "realizei a visita no estabelecimento acima.");
			introducao.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(introducao);

			document.add(espaco);

			int cont = 0;
			for (String item : setor.getItens()) {
				++cont;
				Paragraph itemPrint = new Paragraph(
						String.format("%02d) %s: %s", cont, item, "___________________________"));
				itemPrint.setAlignment(Element.ALIGN_JUSTIFIED);
				document.add(itemPrint);
			}

			document.add(espaco);

			Paragraph observacao = new Paragraph("Observações");
			observacao.setAlignment(Element.ALIGN_CENTER);
			document.add(observacao);

			for (int i = 0; i <= 5; i++)
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
