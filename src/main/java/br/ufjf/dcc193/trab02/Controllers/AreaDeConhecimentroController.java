package br.ufjf.dcc193.trab02.Controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.ufjf.dcc193.trab02.Models.AreaDeConhecimento;
import br.ufjf.dcc193.trab02.Models.Avaliador;
import br.ufjf.dcc193.trab02.Models.AvaliadorConhecimento;
import br.ufjf.dcc193.trab02.Persistence.AreaDeConhecimentoRepository;
import br.ufjf.dcc193.trab02.Persistence.AvaliadorConhecimentoRepository;

@Controller
public class AreaDeConhecimentroController {

    @Autowired
    private AreaDeConhecimentoRepository repositoryConhecimentos;
    @Autowired
    private AvaliadorConhecimentoRepository repositoryAvaliadorConhecimento;

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
                mv.setViewName("/lista-areasconhecimento");
            }
            else
            {
                mv.addObject("avaliador", avaliador);
                mv.setViewName("redirect:/principal-adm");
            }
        }
        else
        {
            Avaliador avaliadorCarregar = new Avaliador();
            mv.addObject("avaliador", avaliadorCarregar);
            mv.setViewName("/login");
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
                mv.setViewName("/cadastro-conhecimento");
            }
            else
            {
                mv.addObject("avaliador", avaliador);
                mv.setViewName("redirect:/principal-adm");
            }
        }
        else
        {
            mv.addObject("avaliador", avaliadorCarregar);
            mv.setViewName("/login");
        }
        return mv;
    }

    @RequestMapping(value = {"/cadastro-conhecimento"}, method = RequestMethod.POST)
    public ModelAndView realizaCadastro (AreaDeConhecimento conhecimento, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        Avaliador avaliadorCarregar = new Avaliador();
        AreaDeConhecimento area = repositoryConhecimentos.findByNome(conhecimento.getNome());
        if (session.getAttribute("usuarioLogado") != null)
        {
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                if (area == null)
                {
                    repositoryConhecimentos.save(conhecimento);
                    mv.setViewName("redirect:/lista-conhecimentos");
                }
                else
                {
                    mv.addObject("conhecimento", area);
                    mv.setViewName("redirect:/cadastro-conhecimento");
                }
            }
            else
            {
                mv.addObject("avaliador", avaliador);
                mv.setViewName("redirect:/principal-adm");
            }
        }
        else
        {
            mv.addObject("avaliador", avaliadorCarregar);
            mv.setViewName("/login");
        }
        return mv; 
    }
     
    @RequestMapping(value = {"/editar-area-conhecimento/{id}"}, method = RequestMethod.GET)
    public ModelAndView carregaEditar (@PathVariable(value = "id", required = true) Long id, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        Avaliador avaliadorCarregar = new Avaliador();
        AreaDeConhecimento conhecimento = repositoryConhecimentos.getOne(id);
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                mv.addObject("conhecimento", conhecimento);
                mv.setViewName("/editar-conhecimento");
            }
            else
            {
                mv.addObject("avaliador", avaliador);
                mv.setViewName("redirect:/principal-adm");
            }
        }
        else
        {
            mv.addObject("avaliador", avaliadorCarregar);
            mv.setViewName("/login");
        }
        return mv;
    }

    @RequestMapping(value = {"/editar-conhecimento-area"}, method = RequestMethod.POST)
    public ModelAndView realizaEditar (@RequestParam(value = "id", required = true) Long id, AreaDeConhecimento conhecimento, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        Avaliador avaliadorCarregar = new Avaliador();
        AreaDeConhecimento area = repositoryConhecimentos.getOne(id);
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {            
                if (area != null)
                {
                    AreaDeConhecimento jaExiste = repositoryConhecimentos.findByNome(conhecimento.getNome());
                    if (jaExiste == null)
                    {
                        conhecimento.setId(id);
                        repositoryConhecimentos.save(conhecimento);
                        mv.setViewName("redirect:/lista-conhecimentos");
                    }
                    else
                    {
                        mv.addObject("conhecimento", area);
                        mv.setViewName("redirect:/cadastro-conhecimento");
                    }      
                }
                else
                {
                    mv.addObject("conhecimento", area);
                    mv.setViewName("redirect:/cadastro-conhecimento");
                }
            }
            else
            {
                mv.addObject("avaliador", avaliador);
                mv.setViewName("redirect:/principal-adm");
            }
        }
        else
        {
            mv.addObject("avaliador", avaliadorCarregar);
            mv.setViewName("/login");
        }
        return mv;
    }

    @RequestMapping(value = { "/excluir-area-conhecimento/{id}" }, method = RequestMethod.GET)
    public ModelAndView carregaExcluir(@PathVariable(value = "id", required = true) Long id, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        Avaliador avaliadorCarregar = new Avaliador();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                repositoryConhecimentos.deleteById(id);
                List<AvaliadorConhecimento> avCo = repositoryAvaliadorConhecimento.findAll();
                for (AvaliadorConhecimento var : avCo) {
                    if (var.getConhecimento().equals(id))
                    {
                        repositoryAvaliadorConhecimento.deleteById(var.getId());
                    }
                }
                mv.setViewName("redirect:/lista-conhecimentos");
            }
            else
            {
                mv.addObject("avaliador", avaliador);
                mv.setViewName("redirect:/principal-adm");
            }
        }
        else
        {
            mv.addObject("avaliador", avaliadorCarregar);
            mv.setViewName("/login");
        }
        return mv;    
    }

}