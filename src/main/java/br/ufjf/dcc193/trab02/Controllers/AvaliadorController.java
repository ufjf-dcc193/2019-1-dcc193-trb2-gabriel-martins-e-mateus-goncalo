package br.ufjf.dcc193.trab02.Controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ufjf.dcc193.trab02.Models.Avaliador;
import br.ufjf.dcc193.trab02.Persistence.AvaliadorRepository;

@Controller
public class AvaliadorController {

    @Autowired
    private AvaliadorRepository repositoryAvaliador;

    @RequestMapping({"/lista-avaliadores"})
    public ModelAndView carregaAvaliadores (HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        List<Avaliador> avaliadores = repositoryAvaliador.findAll();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                mv.addObject("avaliadores", avaliadores);
                mv.setViewName("lista-avaliadores");
            }
            else
            {
                mv.addObject("avaliador", avaliador);
                mv.setViewName("redirect:principal-avaliador");
            }
        }
        else
        {
            Avaliador avaliadorCarregar = new Avaliador();
            mv.addObject("avaliador", avaliadorCarregar);
            mv.setViewName("login");
        }
        return mv;
    }
        
}