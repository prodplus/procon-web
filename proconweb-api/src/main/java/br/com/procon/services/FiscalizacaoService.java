package br.com.procon.services;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.procon.models.Fiscalizacao;
import br.com.procon.repositories.FiscalizacaoRepository;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Service
public class FiscalizacaoService {

	@Autowired
	private FiscalizacaoRepository fiscalizacaoRepository;

	public Fiscalizacao salvar(@Valid Fiscalizacao fiscalizacao) {
		try {
			return this.fiscalizacaoRepository.save(fiscalizacao);
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Fiscalizacao atualizar(Integer id, Fiscalizacao fiscalizacao) {
		try {
			return this.fiscalizacaoRepository.findById(id).map(f -> {
				f.setData(fiscalizacao.getData());
				f.setFornecedor(fiscalizacao.getFornecedor());
				f.setItens(fiscalizacao.getItens());
				f.setSetor(fiscalizacao.getSetor());
				f.setObservacoes(fiscalizacao.getObservacoes());
				return this.fiscalizacaoRepository.save(f);
			}).orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "fiscalizacao não localizada!",
					e.getCause());
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Fiscalizacao buscar(Integer id) {
		try {
			return this.fiscalizacaoRepository.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "fiscalizacao não localizada!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<Fiscalizacao> listar(int pagina, int quant) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.DESC, "data");
			return this.fiscalizacaoRepository.findAll(pageable);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<Fiscalizacao> listar(String fornecedor, int pagina, int quant) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.DESC, "data");
			return this.fiscalizacaoRepository
					.findAllByFornecedorRazaoSocialContainingIgnoreCase(fornecedor, pageable);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public void excluir(Integer id) {
		try {
			this.fiscalizacaoRepository.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

}
