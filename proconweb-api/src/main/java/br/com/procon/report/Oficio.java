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
public class Oficio {

	public static ByteArrayInputStream gerar(Processo processo) {
		try {
			Document document = new Document(PageSize.A4);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, output);
			document.setMargins(65, 30, 10, 40);
			document.open();

			// cria fontes e espaço
			Font negFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
			Font titFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
			Font intFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
			Font minFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
			Paragraph espaco = new Paragraph(" ", intFont);

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

			document.add(espaco);

			Paragraph subTit = new Paragraph(String.format("Autos nº: %s", processo.getAutos()),
					negFont);
			subTit.setAlignment(Element.ALIGN_LEFT);
			document.add(subTit);

			// identificação dos consumidores
			int indice = 0;
			for (Consumidor c : processo.getConsumidores()) {
				++indice;
				subTit = new Paragraph(String.format("CONSUMIDOR %02d", indice), negFont);
				subTit.setAlignment(Element.ALIGN_LEFT);
				document.add(subTit);
				List<String> fonesArray = new ArrayList<>();
				c.getFones().forEach(f -> fonesArray.add(MascarasUtils.foneFormat(f)));
				String fones = String.join(", ", fonesArray);
				String cadastro = c.getTipo().equals(TipoPessoa.FISICA)
						? MascarasUtils.format("###.###.###-##", c.getCadastro())
						: MascarasUtils.format("##.###.###/####-##", c.getCadastro());
				List<String> enderecoArray = new ArrayList<>();
				enderecoArray.add(c.getEndereco().getLogradouro());
				enderecoArray.add(c.getEndereco().getNumero());
				if (c.getEndereco().getComplemento() != null)
					enderecoArray.add(c.getEndereco().getComplemento());
				enderecoArray.add(c.getEndereco().getBairro());
				enderecoArray.add(c.getEndereco().getMunicipio());
				enderecoArray.add(c.getEndereco().getUf().toString());
				String endereco = String.join(", ", enderecoArray);
				Paragraph cons = new Paragraph(
						String.format("Nome: %s, CPF/CNPJ: %s, Endereço: %s, Fone: %s, email: %s",
								c.getDenominacao(), cadastro, endereco, fones, c.getEmail()),
						intFont);
				cons.setAlignment(Element.ALIGN_JUSTIFIED);
				document.add(cons);
			}

			document.add(espaco);

			// identificação dos fornecedores
			indice = 0;
			for (Fornecedor f : processo.getFornecedores()) {
				++indice;
				subTit = new Paragraph(String.format("FORNECEDOR %02d", indice), negFont);
				subTit.setAlignment(Element.ALIGN_LEFT);
				document.add(subTit);
				List<String> fonesArray = new ArrayList<>();
				f.getFones().forEach(c -> fonesArray.add(MascarasUtils.foneFormat(c)));
				String fones = String.join(", ", fonesArray);
				String cadastro = MascarasUtils.format("##.###.###/####-##", f.getCnpj());
				List<String> enderecoArray = new ArrayList<>();
				enderecoArray.add(f.getEndereco().getLogradouro());
				enderecoArray.add(f.getEndereco().getNumero());
				if (f.getEndereco().getComplemento() != null)
					enderecoArray.add(f.getEndereco().getComplemento());
				enderecoArray.add(f.getEndereco().getBairro());
				enderecoArray.add(f.getEndereco().getMunicipio());
				enderecoArray.add(f.getEndereco().getUf().toString());
				String endereco = String.join(", ", enderecoArray);
				Paragraph cons = new Paragraph(
						String.format(
								"Razão Social: %s, CNPJ: %s, Endereço: %s, Fone: %s, email: %s",
								f.getRazaoSocial(), cadastro, endereco, fones, f.getEmail()),
						intFont);
				cons.setAlignment(Element.ALIGN_JUSTIFIED);
				document.add(cons);
			}

			document.add(espaco);

			String primeiroParagrafo = "O consumidor acima qualificado compareceu a este PROCON e, "
					+ "apresentou documentação pertinente a reclamação ora formulada, relata os "
					+ "seguintes fatos:";
			Paragraph fixos = new Paragraph(primeiroParagrafo, intFont);
			fixos.setAlignment(Element.ALIGN_JUSTIFIED);
			fixos.setFirstLineIndent(30f);
			document.add(fixos);

			document.add(espaco);

			// relato dos fatos
			if (processo.getRelato() != null && !processo.getRelato().isBlank()) {
				Paragraph conteudo = new Paragraph(processo.getRelato(), intFont);
				conteudo.setAlignment(Element.ALIGN_JUSTIFIED);
				document.add(conteudo);
			} else {
				Paragraph conteudo = new Paragraph("(RELATO INICIAL EM ANEXO)", intFont);
				conteudo.setAlignment(Element.ALIGN_CENTER);
				for (int i = 0; i < 6; i++)
					document.add(espaco);
				document.add(conteudo);
				for (int i = 0; i < 6; i++)
					document.add(espaco);
			}

			document.add(espaco);

			String segundoParagrafo = "Diante do exposto, com base no § 1º do Art. 33 do Decreto "
					+ "Federal nº2187/97, encaminhamos o consumidor citado acima para a "
					+ "resolução do problema relatado, já tendo sido verificada a presença "
					+ "de indícios de procedência, no prazo de 10 (dez) dias.";
			fixos = new Paragraph(segundoParagrafo, intFont);
			fixos.setAlignment(Element.ALIGN_JUSTIFIED);
			fixos.setFirstLineIndent(30f);
			document.add(fixos);

			document.add(espaco);

			Paragraph aviso = new Paragraph(
					"AS RESPOSTAS DEVEM SER DIRIGIDAS A ESTE PROCON COM PROPOSIÇÃO RESOLUTIVA PARA O CASO.",
					negFont);
			aviso.setAlignment(Element.ALIGN_JUSTIFIED);
			aviso.setFirstLineIndent(30f);
			document.add(aviso);

			document.add(espaco);

			String terceiroParagrafo = "Decorrido o prazo, e não havendo solução da reclamação apresentada, "
					+ "este órgão irá instaurar processo administrativo para apurar eventual "
					+ "infração à Lei 8.078/90, bem como para apreciar a fundamentação da "
					+ "reclamação do consumidor, para efeito de sua inclusão nos Cadastros Estadual "
					+ "e Nacional de Reclamação Fundamentada, nos termos do Art. 44 do Código "
					+ "de Defesa do Consumidor.";
			fixos = new Paragraph(terceiroParagrafo, intFont);
			fixos.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(fixos);

			document.add(espaco);

			// data
			Paragraph data = new Paragraph(String.format("Pato Branco, %02d de %s de %d",
					processo.getData().getDayOfMonth(),
					LocalDateUtils.getMesExtenso(processo.getData().getMonthValue()),
					processo.getData().getYear()), intFont);
			data.setAlignment(Element.ALIGN_RIGHT);
			document.add(data);

			for (int i = 0; i < 2; i++)
				document.add(espaco);

			Paragraph notificacao = new Paragraph("Procon Municipal de Pato Branco - PR", intFont);
			notificacao.setAlignment(Element.ALIGN_CENTER);
			document.add(notificacao);

			document.close();

			return new ByteArrayInputStream(output.toByteArray());
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
