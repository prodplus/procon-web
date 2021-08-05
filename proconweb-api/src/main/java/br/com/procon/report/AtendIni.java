package br.com.procon.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.procon.models.Atendimento;
import br.com.procon.models.Consumidor;
import br.com.procon.models.Fornecedor;
import br.com.procon.utils.LocalDateUtils;
import br.com.procon.utils.MascarasUtils;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
public class AtendIni {

	/**
	 * Gera o atendimento pdf.
	 * 
	 * @param atendimento
	 * @return
	 */
	public static ByteArrayInputStream gerar(Atendimento atendimento) {
		try {
			Document document = new Document(PageSize.A4);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, output);
			document.setMargins(65, 30, 10, 40);
			document.open();

			// cria fontes e espaços
			Font titFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
			Font intFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
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
					"Rua Araribóia, nº 185 - fone (46) 3902-1289 / (46) 3902-1325 / Whatsapp: (46) 99107-6394",
					intFont);
			cabecalho.setAlignment(Element.ALIGN_CENTER);
			document.add(cabecalho);
			cabecalho = new Paragraph("Email: procon@patobranco.pr.gov.br", intFont);
			cabecalho.setAlignment(Element.ALIGN_CENTER);
			document.add(cabecalho);

			for (int i = 0; i < 2; i++)
				document.add(espaco);

			// data
			Paragraph data = new Paragraph(String.format("Pato Branco, %02d de %s de %d",
					atendimento.getData().getDayOfMonth(),
					LocalDateUtils.getMesExtenso(atendimento.getData().getMonthValue()),
					atendimento.getData().getYear()), intFont);
			data.setAlignment(Element.ALIGN_RIGHT);
			document.add(data);

			Paragraph identificacao = new Paragraph(
					String.format("Atendimento nº %04d", atendimento.getId()), intFont);
			identificacao.setAlignment(Element.ALIGN_LEFT);
			document.add(identificacao);
			for (Consumidor c : atendimento.getConsumidores()) {
				identificacao = new Paragraph("Consumidor: " + c.getDenominacao(), intFont);
				identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
				document.add(identificacao);
				identificacao = new Paragraph("Endereço: " + c.getEndereco().getLogradouro() + ", "
						+ c.getEndereco().getNumero() + ", " + c.getEndereco().getComplemento()
						+ ", " + c.getEndereco().getBairro() + ", " + c.getEndereco().getMunicipio()
						+ ", " + c.getEndereco().getUf() + ", CEP "
						+ MascarasUtils.format("#####-###", c.getEndereco().getCep()), intFont);
				identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
				document.add(identificacao);
				List<String> fones = new ArrayList<>();
				c.getFones().forEach(f -> fones.add(MascarasUtils.foneFormat(f)));
				identificacao = new Paragraph("Fone(s): " + String.join(", ", fones), intFont);
				identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
				document.add(identificacao);
			}
			for (Fornecedor f : atendimento.getFornecedores()) {
				identificacao = new Paragraph(
						"Fornecedor: " + f.getFantasia() + " (" + f.getRazaoSocial() + ")",
						intFont);
				identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
				document.add(identificacao);
			}

			for (int i = 0; i < 2; i++)
				document.add(espaco);

			Paragraph titulo = new Paragraph("ATENDIMENTO", intFont);
			titulo.setAlignment(Element.ALIGN_CENTER);
			document.add(titulo);

			for (int i = 0; i < 2; i++)
				document.add(espaco);

			Paragraph conteudo = new Paragraph(atendimento.getRelato(), intFont);
			conteudo.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(conteudo);

			for (int i = 0; i < 2; i++)
				document.add(espaco);

			conteudo = new Paragraph("Consumidor                                     PROCON",
					intFont);
			conteudo.setAlignment(Element.ALIGN_CENTER);
			document.add(conteudo);

			conteudo = new Paragraph(atendimento.getAtendente().getNome(), intFont);
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
