package br.ufjf.dcc193.trab02.Controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.ufjf.dcc193.trab02.Models.Avaliador;
import br.ufjf.dcc193.trab02.Persistence.AvaliadorRepository;

@Controller
public class LoginController {

    @Autowired
    private AvaliadorRepository repositoryAvaliador;

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView carregaLogin (HttpSession session)
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
                mv.setViewName("redirect:/principal-avaliador");
            }
        }
        else
        {
            Avaliador avaliador = new Avaliador();
            mv.addObject("avaliador", avaliador);
            mv.setViewName("login");
        }
        return mv;
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public ModelAndView realizaLogin (Avaliador avaliador, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        Avaliador av = repositoryAvaliador.findByEmail(avaliador.getEmail());
        if (av != null && av.getChave().equals(avaliador.getChave()))
        {
            session.setAttribute("usuarioLogado", av);
            mv.addObject("avaliador", av);
            if (av.getEmail().equals("admin"))
            {
                mv.setViewName("redirect:/principal-adm");
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

    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    public ModelAndView realizaLogout (HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        session.setAttribute("usuarioLogado", null);
        mv.setViewName("redirect:/index");
        return mv;
    }
        
}