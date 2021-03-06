package com.chat.bot.pao.agents;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.util.ObjectUtils;

import com.chat.bot.pao.agents.util.AgentFactory;
import com.chat.bot.pao.model.Libro;
import com.chat.bot.pao.model.dto.LibroDTO;
import com.chat.bot.pao.service.impl.BuscadorService;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

@Named("buscador")
@Scope("prototype")
public class Buscador extends UntypedActor {
	public enum Mensaje {
		
	}

	private static final Logger log = LoggerFactory.getLogger(Buscador.class);
	private ArrayList<ActorRef> espadachines;
	private final BuscadorService buscadorService;
	
	
	

	@Inject
	public Buscador(BuscadorService herreroService) {
		this.buscadorService = herreroService;
	}

	@Override
	public void preStart() {
		//espadachines = new ArrayList<>();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Object message) throws InterruptedException {
		log.info("[Buscador] ha recibido el mensaje: \"{}\".", message);
		LibroDTO libro = (LibroDTO) message;
		List<Libro> lstLibros = buscadorService.obtenerListaLibrosBynombre(libro.getNombreLibro());
		libro.setListLibros(lstLibros);
		List<Libro> listLibrosRecomendados = buscadorService.obtenerListaLibrosRecomendados(libro.getListLibros(), libro.getNombreLibro());
		libro.setListLibrosRecomendados(listLibrosRecomendados);
		if(!ObjectUtils.isEmpty(lstLibros)) {
			AgentFactory.recomendador.tell(libro, getSelf());
		} else {
			unhandled(message);
		}
		/*if (o == Mensaje.CREAR_ESPADA) {
			espadachines.add(getSender());
			AgentFactory.minero.tell(Minero.Mensaje.OBTENER_MATERIALES, getSelf());
		} else if (o == Mensaje.MATERIALES) {
			log.info("[Herrero] está creando espada...");
			herreroService.crearEspada();
			log.info("[Herrero] ha creado espada.");
			if (!espadachines.isEmpty()) {
				espadachines.get(0).tell(Espadachin.Mensaje.ESPADA_NUEVA, getSelf());
				espadachines.remove(0);
			}
		} else {
			unhandled(o);
		}*/
	}

}
