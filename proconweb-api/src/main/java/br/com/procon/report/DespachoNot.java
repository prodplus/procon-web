package br.com.procon.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.procon.models.Consumidor;
import br.com.procon.models.Fornecedor;
import br.com.procon.models.Processo;
import br.com.procon.utils.LocalDateUtils;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
public class DespachoNot {

	public static ByteArrayInputStream gerar(Processo processo) {
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

			// data
			Paragraph data = new Paragraph(
					String.format("Pato Branco, %02d de %s de %d", LocalDate.now().getDayOfMonth(),
							LocalDateUtils.getMesExtenso(LocalDate.now().getMonthValue()),
							LocalDate.now().getYear()),
					intFont);
			data.setAlignment(Element.ALIGN_RIGHT);
			document.add(data);

			// identificação
			Paragraph identificacao = new Paragraph(String.format("AUTOS: %s", processo.getAutos()),
					titFont);
			document.add(identificacao);
			for (Consumidor cons : processo.getConsumidores()) {
				identificacao = new Paragraph(
						String.format("CONSUMIDOR: %s", cons.getDenominacao()), titFont);
				document.add(identificacao);
			}
			int cont = 0;
			for (Fornecedor forn : processo.getFornecedores()) {
				cont++;
				identificacao = new Paragraph(
						String.format("FORNECEDOR %02d: %s", cont, forn.getRazaoSocial()), titFont);
				document.add(identificacao);
			}

			for (int i = 0; i < 3; i++)
				document.add(espaco);

			Paragraph conteudo = new Paragraph(
					"I - Presente a relação de consumo, adote-se o procedimento "
							+ "estabelecido no Decreto Federal nº 2181/1997;",
					intFont);
			conteudo.setAlignment(Element.ALIGN_JUSTIFIED);
			conteudo.setLeading(25f);
			document.add(conteudo);
			document.add(espaco);
			conteudo = new Paragraph("II - A fim de buscar a solução da presente reclamação, "
					+ "expeça-se ofício para a(s) empresa(s) fornecedora(s) com prazo de 10 (dez) dias;",
					intFont);
			conteudo.setAlignment(Element.ALIGN_JUSTIFIED);
			conteudo.setLeading(25f);
			document.add(conteudo);
			document.add(espaco);
			conteudo = new Paragraph(
					"III - Sendo apresentada a defesa pelas empresas fornecedoras, "
							+ "notifiquem-se o(a) consumidor(a) para tomar ciência, bem como se "
							+ "manifestar no prazo de 48 horas;",
					intFont);
			conteudo.setAlignment(Element.ALIGN_JUSTIFIED);
			conteudo.setLeading(25f);
			document.add(conteudo);
			document.add(espaco);
			conteudo = new Paragraph("IV - Após, voltem-se os autos conclusos.", intFont);
			conteudo.setAlignment(Element.ALIGN_JUSTIFIED);
			conteudo.setLeading(25f);
			document.add(conteudo);

			for (int i = 0; i < 3; i++)
				document.add(espaco);

			for (int i = 0; i < 3; i++)
				document.add(espaco);

			Paragraph assinatura = new Paragraph("__________________________", titFont);
			assinatura.setAlignment(Element.ALIGN_CENTER);
			document.add(assinatura);
			assinatura = new Paragraph("Dra. Elaine Dias Menegola", titFont);
			assinatura.setAlignment(Element.ALIGN_CENTER);
			document.add(assinatura);
			assinatura = new Paragraph("Diretora - PROCON - Pato Branco, PR", titFont);
			assinatura.setAlignment(Element.ALIGN_CENTER);
			document.add(assinatura);

			document.close();

			return new ByteArrayInputStream(output.toByteArray());
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
