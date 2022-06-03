package com.aos.os.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.aos.os.domain.Tecnico;
import com.aos.os.dtos.TecnicoDTO;
import com.aos.os.services.TecnicoService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {

	// localhost:8080/tecnicos

	@Autowired
	private TecnicoService service;

	//Busca por ID
	@GetMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
		TecnicoDTO objDTO = new TecnicoDTO(service.findById(id));
		return ResponseEntity.ok().body(objDTO);
	}

	//Busca toda a lista
	@GetMapping
	public ResponseEntity<List<TecnicoDTO>> findAll() {
		// Opção 3
		List<TecnicoDTO> listDTO = service.findAll().stream().map(obj -> new TecnicoDTO(obj))
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(listDTO);

//		List<Tecnico> list = service.findAll();
//		List<TecnicoDTO> listDTO = new ArrayList();
//		
//		//Opção 1
//		for(Tecnico obj : list) {
//			listDTO.add(new TecnicoDTO(obj));
//		}
//		
//		//Opção 2
//		list.forEach(obj -> listDTO.add(new TecnicoDTO(obj)));
	}
	
	//Cria um tecnico
	@PostMapping
	public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO objDTO) {
		Tecnico newObj = service.create(objDTO);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	//Altera um tecnico
	@PutMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO objDTO) {
		
		TecnicoDTO newObj = new TecnicoDTO(service.update(id, objDTO));
		
		return ResponseEntity.ok().body(newObj);
	}
	
	//Deleta um tecnico
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
