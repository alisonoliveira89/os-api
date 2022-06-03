package com.aos.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aos.os.domain.Pessoa;
import com.aos.os.domain.Tecnico;
import com.aos.os.dtos.TecnicoDTO;
import com.aos.os.repositories.PessoaRepository;
import com.aos.os.repositories.TecnicoRepository;
import com.aos.os.services.exceptions.DataIntegratyViolationException;
import com.aos.os.services.exceptions.ObjectNotFoundException;

import ch.qos.logback.core.joran.conditional.IfAction;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrato! Id: " + id + ", Tipo: " + Tecnico.class.getName()));
	}

	public List<Tecnico> findAll() {
		return repository.findAll();
	}

	public Tecnico create(TecnicoDTO objDTO) {
		// Tecnico newObj = new Tecnico(null, objDTO.getNome(), objDTO.getCpf(),
		// objDTO.getTelefone());
		// return repository.save(newObj);
		if (findbyCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		return repository
				.save(new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone(), objDTO.getEndereco()));
	}

	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		Tecnico oldObj = findById(id);

		if (findbyCPF(objDTO) != null && findbyCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}

		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		oldObj.setEndereco(objDTO.getEndereco());
		return repository.save(oldObj);
	}

	public void delete(Integer id) {
		Tecnico obj = findById(id);
		if(obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Técnico possui Ordens de Serviço, não pode ser deletado!");
		}
		repository.deleteById(id);
	}

	private Pessoa findbyCPF(TecnicoDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
		if (obj != null) {
			return obj;
		}
		return null;
	}

}
