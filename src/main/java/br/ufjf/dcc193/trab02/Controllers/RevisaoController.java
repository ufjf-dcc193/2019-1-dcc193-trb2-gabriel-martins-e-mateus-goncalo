package br.ufjf.dcc193.trab02.Controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.ufjf.dcc193.trab02.Models.Avaliador;
import br.ufjf.dcc193.trab02.Models.Revisao;
import br.ufjf.dcc193.trab02.Persistence.AvaliadorRepository;
import br.ufjf.dcc193.trab02.Persistence.RevisaoRepository;

@Controller
public class RevisaoController {

    @Autowired
    private RevisaoRepository repositoryRevisao;
    @Autowired
    private AvaliadorRepository repositoryAvaliador;

    @RequestMapping(value = {"/lista-revisoes-administrador"}, method = RequestMethod.GET)
    public ModelAndView carregaRevisaoAdministrador (HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                List<Revisao> revisoes = repositoryRevisao.findAll();
                mv.addObject("revisoes", revisoes);
                mv.setViewName("/lista-revisoes-administrador");
            }
            else
            {
                mv.setViewName("redirect:/principal-avaliador");
            }
        }
        else
        {
            mv.setViewName("redirect:/login");
        }
        return mv;
    }

    @RequestMapping(value = {"/lista-revisoes-avaliador"}, method = RequestMethod.GET)
    public ModelAndView carregaRevisaoAvaliador (HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                mv.setViewName("redirect:/principal-adm");
            }
            else
            {
                Avaliador revisor = repositoryAvaliador.getOne(avaliador.getId());
                List<Revisao> revisoes = repositoryRevisao.findByAvaliador(revisor);
                mv.addObject("revisoes", revisoes);
                mv.setViewName("/lista-revisoes-avaliador");
            }
        }
        else
        {
            mv.setViewName("redirect:/login");
        }
        return mv;
    }
    
}