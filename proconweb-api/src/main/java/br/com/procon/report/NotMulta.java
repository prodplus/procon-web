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
public class NotMulta {

	public static ByteArrayInputStream gerar(Processo processo, Fornecedor fornecedor) {
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
			identificacao = new Paragraph(
					String.format("FORNECEDOR: %s", fornecedor.getRazaoSocial()), titFont);
			document.add(identificacao);

			for (int i = 0; i < 2; i++)
				document.add(espaco);

			Paragraph titulo = new Paragraph("NOTIFICAÇÃO", titFont);
			titulo.setAlignment(Element.ALIGN_CENTER);
			document.add(titulo);

			for (int i = 0; i < 2; i++)
				document.add(espaco);

			Paragraph conteudo = new Paragraph("Prezado(a) Senhor(a)", intFont);
			conteudo.setAlignment(Element.ALIGN_LEFT);
			conteudo.setFirstLineIndent(30f);
			document.add(conteudo);
			document.add(espaco);

			conteudo = new Paragraph("Nos termos do Art. 56, § único da Lei 8078/90, "
					+ "combinado com o Art. 46, §2º do Decreto Federal 2187/97 e "
					+ "da Lei Municipal 2120/2001, NOTIFICAMOS Vossa Senhoria para "
					+ "efetuar o recolhimento da multa fixada na decisão administrativa "
					+ "em anexo, ou requerendo, apresentar recurso, no prazo máximo "
					+ "de 10 (dez) dias contados a partir do recebimento desta.", intFont);
			conteudo.setAlignment(Element.ALIGN_JUSTIFIED);
			conteudo.setLeading(25f);
			conteudo.setFirstLineIndent(30f);
			document.add(conteudo);
			document.add(espaco);

			conteudo = new Paragraph("Notificamos ainda que, caso não haja apresentação "
					+ "de recurso no prazo acima, nem o recolhimento do valor "
					+ "da multa arbitrada, será o débito inscrito em dívida ativa, "
					+ "para subsequente cobrança executiva.", intFont);
			conteudo.setFirstLineIndent(30f);
			conteudo.setLeading(25f);
			conteudo.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(conteudo);
			document.add(espaco);

			for (int i = 0; i < 2; i++)
				document.add(espaco);

			conteudo = new Paragraph("Atenciosamente", intFont);
			conteudo.setAlignment(Element.ALIGN_JUSTIFIED);
			conteudo.setFirstLineIndent(30f);
			document.add(conteudo);

			for (int i = 0; i < 3; i++)
				document.add(espaco);

			Paragraph assinatura = new Paragraph("__________________________", titFont);
			assinatura.setAlignment(Element.ALIGN_CENTER);
			document.add(assinatura);
			assinatura = new Paragraph("PROCON - Pato Branco, PR", titFont);
			assinatura.setAlignment(Element.ALIGN_CENTER);
			document.add(assinatura);
			
			document.newPage();

			Paragraph certidaoTit = new Paragraph("CERTIDÃO", titFont);
			certidaoTit.setAlignment(Element.ALIGN_CENTER);
			document.add(certidaoTit);

			document.add(espaco);

			Paragraph certidaoCont = new Paragraph(
					String.format("Certifico que expedi, via AR, em %02d/%02d/%02d, à %s",
							LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(),
							LocalDate.now().getYear(), fornecedor.getRazaoSocial()),
					intFont);
			certidaoCont.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(certidaoCont);

			document.add(espaco);
			document.add(data);

			document.close();

			return new ByteArrayInputStream(output.toByteArray());
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
