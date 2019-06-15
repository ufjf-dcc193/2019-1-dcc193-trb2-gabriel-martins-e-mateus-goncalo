package br.ufjf.dcc193.trab02.Controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
                mv.setViewName("redirect:principal-adm");
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

    @RequestMapping(value = {"/cadastro-avaliador"}, method = RequestMethod.GET)
    public ModelAndView carregaCadastro (HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        Avaliador avaliadorCarregar = new Avaliador();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                mv.addObject("avaliador", avaliadorCarregar);
                mv.setViewName("cadastro-avaliador");
            }
            else
            {
                mv.addObject("avaliador", avaliador);
                mv.setViewName("redirect:principal-avaliador");
            }
        }
        else
        {
            mv.addObject("avaliador", avaliadorCarregar);
            mv.setViewName("login");
        }
        return mv;
    }

    @RequestMapping(value = {"/cadastro-avaliador"}, method = RequestMethod.POST)
    public ModelAndView realizaCadastro (Avaliador avaliador, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        Avaliador av = repositoryAvaliador.findByEmail(avaliador.getEmail());
        if (av == null)
        {
            repositoryAvaliador.save(avaliador);
            mv.setViewName("redirect:lista-avaliadores");
        }
        else
        {
            mv.addObject("avaliador", avaliador);
            mv.setViewName("redirect:cadastro-avaliador");
        }
        return mv;
    }

    @RequestMapping(value = {"/cadastrar-area-conhecimento-avaliador/{id}"}, method = RequestMethod.GET)
    public ModelAndView carregaAreaConhecimento (@PathVariable(value = "id", required = true) Long id, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        Avaliador avaliadorCarregar = new Avaliador();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                mv.addObject("avaliador", avaliadorCarregar);
                mv.setViewName("cadastro-avaliador");
            }
            else
            {
                mv.addObject("avaliador", avaliador);
                mv.setViewName("redirect:principal-avaliador");
            }
        }
        else
        {
            mv.addObject("avaliador", avaliadorCarregar);
            mv.setViewName("login");
        }
        return mv;
    }

    @RequestMapping(value = {"/cadastrar-area-conhecimento-avaliador"}, method = RequestMethod.POST)
    public ModelAndView realizaCadastroAreaConhecimento (Avaliador avaliador, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        Avaliador av = repositoryAvaliador.findByEmail(avaliador.getEmail());
        if (av == null)
        {
            repositoryAvaliador.save(avaliador);
            mv.setViewName("redirect:lista-avaliadores");
        }
        else
        {
            mv.addObject("avaliador", avaliador);
            mv.setViewName("redirect:cadastro-avaliador");
        }
        return mv;
    }
        
}