package br.ufjf.dcc193.trab02;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import br.ufjf.dcc193.trab02.Models.Avaliador;
import br.ufjf.dcc193.trab02.Persistence.AvaliadorRepository;

@SpringBootApplication
public class MainApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(MainApplication.class, args);
		AvaliadorRepository repAvaliador = ctx.getBean(AvaliadorRepository.class);
		List<Avaliador> avaliadores = repAvaliador.findAll();
		if (avaliadores.size() == 0)
		{
			Avaliador avaliador = new Avaliador();
			avaliador.setEmail("admin");
			avaliador.setChave("admin");
			avaliador.setNome("admin");
			repAvaliador.save(avaliador);
		}
	}

}
