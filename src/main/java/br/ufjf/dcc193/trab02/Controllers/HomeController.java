package br.ufjf.dcc193.trab02.Controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ufjf.dcc193.trab02.Models.Avaliador;

@Controller
public class HomeController {

    @RequestMapping({"", "/index", "/principal-adm", "/principal-avaliador"})
    public String index (HttpSession session)
    {
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                return "/principal-adm";
            }
            else
            {
                return "/principal-avaliador";
            }
        }
        else
        {
            return "/index";
        }
    }

    @RequestMapping({"/sobre"})
    public String sobre ()
    {
        return "/sobre";
    }
        
}