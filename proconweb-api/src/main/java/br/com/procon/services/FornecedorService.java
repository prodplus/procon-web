package br.com.procon.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.procon.models.Fornecedor;
import br.com.procon.repositories.FornecedorRepository;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Service
public class FornecedorService {

	@Autowired
	private FornecedorRepository fornecedorRepository;

	public Fornecedor salvar(@Valid Fornecedor fornecedor) {
		try {
			fornecedor.setFantasia(fornecedor.getFantasia().toUpperCase().trim());
			fornecedor.setRazaoSocial(fornecedor.getRazaoSocial().toUpperCase().trim());
			return this.fornecedorRepository.save(fornecedor);
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "fornecedor já cadastrado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Fornecedor atualizar(Integer id, @Valid Fornecedor fornecedor) {
		try {
			return this.fornecedorRepository.findById(id).map(novo -> {
				novo.setCnpj(fornecedor.getCnpj());
				novo.setEmail(fornecedor.getEmail());
				novo.setEndereco(fornecedor.getEndereco());
				novo.setFantasia(fornecedor.getFantasia().toUpperCase().trim());
				novo.setFones(fornecedor.getFones());
				novo.setRazaoSocial(fornecedor.getRazaoSocial().toUpperCase().trim());
				return this.fornecedorRepository.save(novo);
			}).orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "fornecedor não localizado!",
					e.getCause());
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "fornecedor já cadastrado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Fornecedor buscar(Integer id) {
		try {
			return this.fornecedorRepository.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "fornecedor não localizado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<Fornecedor> listar(int pagina, int quant) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.ASC, "fantasia");
			return this.fornecedorRepository.findAll(pageable);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<Fornecedor> listar(String parametro, int pagina, int quant) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.ASC, "fantasia");
			return this.fornecedorRepository
					.findAllByFantasiaContainingIgnoreCaseOrRazaoSocialContainingIgnoreCaseOrCnpjContaining(
							parametro, parametro, parametro, pageable);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public void excluir(Integer id) {
		try {
			this.fornecedorRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"não é possível excluir o fornecedor!", e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public boolean fantasiaExiste(String fantasia) {
		try {
			return this.fornecedorRepository.existsByFantasia(fantasia);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public boolean cnpjExiste(String cnpj) {
		try {
			return this.fornecedorRepository.existsByCnpj(cnpj);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<Fornecedor> listar() {
		return this.fornecedorRepository.findAll();
	}

}
