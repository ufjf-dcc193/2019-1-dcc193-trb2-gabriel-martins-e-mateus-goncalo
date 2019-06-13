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
import br.ufjf.dcc193.trab02.Persistence.AreaDeConhecimentoRepository;

@Controller
public class AreaDeConhecimentroController {

    @Autowired
    private AreaDeConhecimentoRepository repositoryConhecimentos;

    @RequestMapping({"/lista-conhecimentos"})
    public ModelAndView carregaAvaliadores (HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        List<AreaDeConhecimento> conhecimentos = repositoryConhecimentos.findAll();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                mv.addObject("conhecimentos", conhecimentos);
                mv.setViewName("lista-areasconhecimento");
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

    @RequestMapping(value = {"/cadastro-conhecimento"}, method = RequestMethod.GET)
    public ModelAndView carregaCadastro (HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        Avaliador avaliadorCarregar = new Avaliador();
        AreaDeConhecimento conhecimento = new AreaDeConhecimento();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                mv.addObject("conhecimento", conhecimento);
                mv.setViewName("cadastro-conhecimento");
            }
            else
            {
                mv.addObject("avaliador", avaliador);
                mv.setViewName("redirect:principal-adm");
            }
        }
        else
        {
            mv.addObject("avaliador", avaliadorCarregar);
            mv.setViewName("login");
        }
        return mv;
    }

    @RequestMapping(value = {"/cadastro-conhecimento"}, method = RequestMethod.POST)
    public ModelAndView realizaCadastro (AreaDeConhecimento conhecimento, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        AreaDeConhecimento area = repositoryConhecimentos.findByNome(conhecimento.getNome());
        if (area == null)
        {
            repositoryConhecimentos.save(conhecimento);
            mv.setViewName("redirect:lista-conhecimentos");
        }
        else
        {
            mv.addObject("conhecimento", area);
            mv.setViewName("redirect:cadastro-conhecimento");
        }
        return mv; 
    }
        
}