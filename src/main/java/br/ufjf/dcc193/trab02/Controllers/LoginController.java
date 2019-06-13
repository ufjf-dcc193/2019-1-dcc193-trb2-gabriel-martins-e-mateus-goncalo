package br.ufjf.dcc193.trab02.Controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.ufjf.dcc193.trab02.Models.Avaliador;
import br.ufjf.dcc193.trab02.Persistence.AvaliadorRepository;

@Controller
public class LoginController {

    @Autowired
    private AvaliadorRepository repositoryAvaliador;

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String carregaLogin (HttpSession session)
    {
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail() == "admin")
            {
                return "principal-adm";
            }
            else
            {
                return "principal-avaliador";
            }
        }
        else
        {
            return "login";
        }
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public String realizaLogin (Avaliador avaliador, HttpSession session)
    {
        Avaliador av = repositoryAvaliador.findByEmail(avaliador.getEmail());
        if (av.getChave().equals(avaliador.getChave()))
        {
            session.setAttribute("usuarioLogado", avaliador);
            if (av.getEmail().equals("admin"))
            {
                return "principal-adm";
            }
            else
            {
                return "principal-avaliador";
            }
        }
        else
        {
            return "login";
        }
    }
        
}