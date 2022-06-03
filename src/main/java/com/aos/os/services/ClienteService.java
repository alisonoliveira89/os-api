package com.aos.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aos.os.domain.Cliente;
import com.aos.os.domain.Pessoa;
import com.aos.os.dtos.ClienteDTO;
import com.aos.os.repositories.ClienteRepository;
import com.aos.os.repositories.PessoaRepository;
import com.aos.os.services.exceptions.DataIntegratyViolationException;
import com.aos.os.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Cliente findById(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrato! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	public Cliente create(ClienteDTO objDTO) {
		if (findByCpf(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		return clienteRepository
				.save(new Cliente(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone(), objDTO.getEndereco()));
	}

	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		Cliente oldObj = findById(id);

		if (findByCpf(objDTO) != null && findByCpf(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}

		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		oldObj.setEndereco(objDTO.getEndereco());
		return clienteRepository.save(oldObj);
	}

	public void delete(Integer id) {
		Cliente obj = findById(id);
		if (obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Cliente já possui Ordens de serviço, não pode ser deletado");
		}

		clienteRepository.deleteById(id);
	}

	public Pessoa findByCpf(ClienteDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
		if (obj != null) {
			return obj;
		}
		return null;
	}

}
