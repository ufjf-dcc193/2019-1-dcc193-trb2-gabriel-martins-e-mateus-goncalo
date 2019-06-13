package br.ufjf.dcc193.trab02;

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
		Avaliador avaliador = new Avaliador();
		avaliador.setEmail("admin");
		avaliador.setChave("admin");
		repAvaliador.save(avaliador);
		System.out.println(repAvaliador.findAll().size());
	}

}
