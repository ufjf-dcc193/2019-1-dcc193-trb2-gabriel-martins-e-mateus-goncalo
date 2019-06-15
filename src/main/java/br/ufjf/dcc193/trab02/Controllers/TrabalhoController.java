package br.ufjf.dcc193.trab02.Controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.ufjf.dcc193.trab02.Models.AreaDeConhecimento;
import br.ufjf.dcc193.trab02.Models.Avaliador;
import br.ufjf.dcc193.trab02.Models.Trabalho;
import br.ufjf.dcc193.trab02.Persistence.AreaDeConhecimentoRepository;
import br.ufjf.dcc193.trab02.Persistence.TrabalhoRepository;

@Controller
public class TrabalhoController {

    @Autowired
    private TrabalhoRepository repositoryTrabalho;
    @Autowired
    private AreaDeConhecimentoRepository repositoryConhecimento;

    @RequestMapping({"/lista-trabalhos"})
    public ModelAndView carregaTrabalhos (HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        List<Trabalho> trabalhos = repositoryTrabalho.findAll();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                mv.addObject("trabalhos", trabalhos);
                mv.setViewName("lista-trabalhos");
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

    @RequestMapping(value = {"/cadastro-trabalho"}, method = RequestMethod.GET)
    public ModelAndView carregaCadastro (HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        Trabalho trabalho = new Trabalho();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                List<AreaDeConhecimento> conhecimentos = repositoryConhecimento.findAll();
                mv.addObject("conhecimentos", conhecimentos);
                mv.addObject("trabalho", trabalho);
                mv.setViewName("cadastro-trabalho");
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

    @RequestMapping(value = {"/cadastro-trabalho"}, method = RequestMethod.POST)
    public ModelAndView realizaCadastro (Trabalho trabalho, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (trabalho != null)
        {
            repositoryTrabalho.save(trabalho);
            mv.setViewName("redirect:lista-trabalhos");
        }
        else
        {
            mv.addObject("avaliador", trabalho);
            mv.setViewName("redirect:cadastro-trabalho");
        }
        return mv;
    }
        
}