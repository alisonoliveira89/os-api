package com.aos.os.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aos.os.domain.Cliente;
import com.aos.os.domain.OS;
import com.aos.os.domain.Tecnico;
import com.aos.os.domain.enuns.Prioridade;
import com.aos.os.domain.enuns.Status;
import com.aos.os.repositories.ClienteRepository;
import com.aos.os.repositories.OSRepository;
import com.aos.os.repositories.TecnicoRepository;

@Service
public class DBService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private OSRepository osRepository;

	public void instanciaDB() {

		Tecnico t1 = new Tecnico(null, "Alison Oliveira", "935.827.530-85", "(11) 91234-8888", "av teste");
		Tecnico t2 = new Tecnico(null, "Leticia Santos", "483.886.930-42", "(11) 94321-7777", "av tes 02");
		Cliente c1 = new Cliente(null, "Carine Santos", "937.790.300-98", "(11) 95678-9999", "xxxx");
		OS os1 = new OS(null, Prioridade.ALTA, Status.ANDAMENTO, "teste OS", t1, c1);

		t1.getList().add(os1);
		t2.getList().add(os1);
		c1.getList().add(os1);

		tecnicoRepository.saveAll(Arrays.asList(t1, t2));
		clienteRepository.saveAll(Arrays.asList(c1));
		osRepository.saveAll(Arrays.asList(os1));
	}
}
