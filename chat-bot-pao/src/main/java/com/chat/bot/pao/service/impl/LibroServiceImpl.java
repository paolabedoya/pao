package com.chat.bot.pao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.bot.pao.agents.util.AgentFactory;
import com.chat.bot.pao.model.Libro;
import com.chat.bot.pao.model.dto.LibroDTO;
import com.chat.bot.pao.repository.LibroRepository;
import com.chat.bot.pao.service.LibroService;

import akka.actor.ActorRef;

@Service
public class LibroServiceImpl implements LibroService {

	@Autowired 
	private LibroRepository libroRepository; 
	
	private static final long TIEMPO_CREACION_ESPADA = 3000;
	
	@Override
	public List<Libro> listarLibros() {
		return (List<Libro>) libroRepository.findAll();
	}

	@Override
	public List<Libro> obtenerLibrosByNombre(String nombreLibro) {
		LibroDTO libroDto = new LibroDTO();
		libroDto.setNombreLibro(nombreLibro);
		AgentFactory.espadachin.tell(libroDto, ActorRef.noSender());
		try {
			crearEspada();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return libroDto.getListLibros();
	}
	
	public void crearEspada() throws InterruptedException {
		Thread.sleep(TIEMPO_CREACION_ESPADA);
	}


}
