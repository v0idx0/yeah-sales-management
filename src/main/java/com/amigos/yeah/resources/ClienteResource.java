package com.amigos.yeah.resources;

import javax.validation.Valid;

import com.amigos.yeah.domain.Cliente;
import com.amigos.yeah.dto.ClienteDTO;
import com.amigos.yeah.dto.ClienteNewDTO;
import com.amigos.yeah.services.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping( value = "/clientes" )
public class ClienteResource {
    
    @Autowired
    private ClienteService service;

    @RequestMapping( value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Cliente> find(@PathVariable Integer id) {
        Cliente obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto) {
		Cliente obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	

    @RequestMapping( value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id) {
        Cliente obj = service.fromDTO(objDTO);
        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<Cliente> list = service.findAll();
        // List<ClienteDTO> listDTO = list.stream().map()
        List<ClienteDTO> listDTO = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    // Rota para buscar dados com paginação
    // @RequestParam indica que o métodos serão parametros e não parte fixa da rota
    // Permitindo que esses dados sejam passados ou não
    // Caso não sejam passados, receberão seus respectivos valores padrão
    @RequestMapping(value="/page", method = RequestMethod.GET)
    public ResponseEntity<Page<ClienteDTO>> findPage(
        @RequestParam(value = "page", defaultValue = "0") Integer page, 
        @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
        @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
        @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        
        Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);
        Page<ClienteDTO> listDTO = list.map(cli -> new ClienteDTO(cli));
        return ResponseEntity.ok().body(listDTO);
    }

}