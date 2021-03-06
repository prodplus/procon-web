package br.com.procon.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.procon.models.Consumidor;
import br.com.procon.models.Fornecedor;
import br.com.procon.models.Processo;
import br.com.procon.models.enums.TipoPessoa;
import br.com.procon.utils.LocalDateUtils;
import br.com.procon.utils.MascarasUtils;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
public class ProcIni {

	public static ByteArrayInputStream gerar(Processo processo) {
		try {
			Document document = new Document();
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, output);
			document.setMargins(65, 30, 10, 40);
			document.open();

			// cria fontes e espaços
			Font titFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
			Font intFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
			Font negFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
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
			Paragraph data = new Paragraph(String.format("Pato Branco, %02d de %s de %d",
					processo.getData().getDayOfMonth(),
					LocalDateUtils.getMesExtenso(processo.getData().getMonthValue()),
					processo.getData().getYear()), intFont);
			data.setAlignment(Element.ALIGN_RIGHT);
			document.add(data);

			// identificação
			Paragraph identificacao = new Paragraph("Autos nº ", negFont);
			identificacao.add(new Chunk(processo.getAutos(), intFont));
			identificacao.setAlignment(Element.ALIGN_LEFT);
			document.add(identificacao);

			for (Consumidor c : processo.getConsumidores()) {
				identificacao = new Paragraph("Consumidor: ", negFont);
				identificacao.add(new Chunk(c.getDenominacao(), intFont));
				identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
				document.add(identificacao);
				identificacao = new Paragraph("Endereço: ", negFont);
				identificacao
						.add(new Chunk(
								c.getEndereco().getLogradouro() + ", " + c.getEndereco().getNumero()
										+ ", " + c.getEndereco().getComplemento() + ", "
										+ c.getEndereco().getBairro() + ", "
										+ c.getEndereco().getMunicipio() + ", "
										+ c.getEndereco().getUf() + ", CEP " + MascarasUtils
												.format("#####-###", c.getEndereco().getCep()),
								intFont));
				identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
				document.add(identificacao);
				identificacao = new Paragraph("Fones: ", negFont);
				List<String> fones = new ArrayList<>();
				c.getFones().forEach(f -> fones.add(MascarasUtils.foneFormat(f)));
				identificacao.add(new Chunk(String.join(", ", fones), intFont));
				identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
				document.add(identificacao);
			}

			for (Consumidor c : processo.getRepresentantes()) {
				identificacao = new Paragraph("Representante: ", negFont);
				identificacao.add(new Chunk(c.getDenominacao(), intFont));
				identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
				document.add(identificacao);
				identificacao = new Paragraph("Endereço: ", negFont);
				identificacao
						.add(new Chunk(
								c.getEndereco().getLogradouro() + ", " + c.getEndereco().getNumero()
										+ ", " + c.getEndereco().getComplemento() + ", "
										+ c.getEndereco().getBairro() + ", "
										+ c.getEndereco().getMunicipio() + ", "
										+ c.getEndereco().getUf() + ", CEP " + MascarasUtils
												.format("#####-###", c.getEndereco().getCep()),
								intFont));
				identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
				document.add(identificacao);
				identificacao = new Paragraph("Fones: ", negFont);
				List<String> fones = new ArrayList<>();
				c.getFones().forEach(f -> fones.add(MascarasUtils.foneFormat(f)));
				identificacao.add(new Chunk(String.join(", ", fones), intFont));
				identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
				document.add(identificacao);
			}

			int cont = 0;
			for (Fornecedor f : processo.getFornecedores()) {
				cont++;
				identificacao = new Paragraph(String.format("Fornecedor %02d: ", cont), negFont);
				identificacao
						.add(new Chunk(f.getFantasia() + " (" + f.getRazaoSocial() + ")", intFont));
				identificacao.setAlignment(Element.ALIGN_JUSTIFIED);
				document.add(identificacao);
			}

			document.add(espaco);

			Paragraph titulo = new Paragraph("HISTÓRICO DA RECLAMAÇÃO", intFont);
			titulo.setAlignment(Element.ALIGN_CENTER);
			document.add(titulo);

			document.add(espaco);

			StringBuilder builder1 = new StringBuilder();
			builder1.append("O(a) consumidor(a) ");
			builder1.append(processo.getConsumidores().get(0).getDenominacao());
			builder1.append(String.format(", %s %s",
					processo.getConsumidores().get(0).getTipo().equals(TipoPessoa.FISICA)
							? "inscrito(a) no CPF nº "
							: " inscrito(a) no CNPJ nº ",
					processo.getConsumidores().get(0).getTipo().equals(TipoPessoa.FISICA)
							? MascarasUtils.format("###.###.###-##",
									processo.getConsumidores().get(0).getCadastro())
							: MascarasUtils.format("##.###.###/####-##",
									processo.getConsumidores().get(0).getCadastro())));
			if (processo.getRepresentantes() != null && !processo.getRepresentantes().isEmpty()) {
				builder1.append(", representado(a) por ");
				builder1.append(processo.getRepresentantes().get(0).getDenominacao());
				builder1.append(String.format(", %s %s",
						processo.getRepresentantes().get(0).getTipo().equals(TipoPessoa.FISICA)
								? "inscrito(a) no CPF nº "
								: " inscrito(a) no CNPJ nº ",
						processo.getRepresentantes().get(0).getTipo().equals(TipoPessoa.FISICA)
								? MascarasUtils.format("###.###.###-##",
										processo.getRepresentantes().get(0).getCadastro())
								: MascarasUtils.format("##.###.###/####-##",
										processo.getConsumidores().get(0).getCadastro())));
			}
			builder1.append(", formalizou reclamação em desfavor da(s) empresa(s) ");
			String[] fornString = new String[processo.getFornecedores().size()];
			for (int i = 0; i < processo.getFornecedores().size(); i++)
				fornString[i] = processo.getFornecedores().get(i).getRazaoSocial() + " (CNPJ: "
						+ MascarasUtils.format("##.###.###/####-##",
								processo.getFornecedores().get(i).getCnpj())
						+ ")";
			builder1.append(String.join(" e ", fornString));
			builder1.append(", alegando em síntese o que segue:");

			Paragraph conteudo = new Paragraph(builder1.toString(), intFont);
			conteudo.setAlignment(Element.ALIGN_JUSTIFIED);
			conteudo.setFirstLineIndent(30f);
			document.add(conteudo);
			document.add(espaco);

			conteudo = new Paragraph(processo.getRelato(), intFont);
			conteudo.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(conteudo);

			for (int i = 0; i < 2; i++)
				document.add(espaco);

			conteudo = new Paragraph("Consumidor                                     PROCON",
					intFont);
			conteudo.setAlignment(Element.ALIGN_CENTER);
			document.add(conteudo);

			if (processo.getAtendente() != null) {
				conteudo = new Paragraph("Atendente: " + processo.getAtendente().getNome(),
						minFont);
				conteudo.setAlignment(Element.ALIGN_RIGHT);
				document.add(conteudo);
			}

			document.close();

			return new ByteArrayInputStream(output.toByteArray());
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
