package br.ufjf.dcc193.trab02.Controllers;

import java.util.ArrayList;
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
import br.ufjf.dcc193.trab02.Persistence.AvaliadorRepository;

@Controller
public class AvaliadorController {

    @Autowired
    private AvaliadorRepository repositoryAvaliador;
    @Autowired
    private AreaDeConhecimentoRepository repositoryConhecimentos;
    @Autowired
    private AvaliadorConhecimentoRepository repositoryAC;

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
    public ModelAndView carregaCadastroAreaConhecimento (@PathVariable(value = "id", required = true) Long id, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        Avaliador avaliadorCarregar = new Avaliador();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                List<AreaDeConhecimento> conhecimentos = repositoryConhecimentos.findAll();
                for (AreaDeConhecimento var : conhecimentos) {
                    var.setNome(var.getId() + " - " + var.getNome());
                }
                AreaDeConhecimento conhecimento = new AreaDeConhecimento();
                mv.addObject("conhecimentos", conhecimentos);
                AreaDeConhecimento conhecimento2 = new AreaDeConhecimento();
                mv.addObject("conhe", conhecimento2);
                mv.addObject("id", id);
                mv.setViewName("cadastro-area-conhecimento-avaliador");
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
    public ModelAndView realizaCadastroAreaConhecimento (@RequestParam(value = "id", required = true) Long id, 
    @RequestParam(value = "conhecimentos", required = true) String conhecimento, HttpSession session)
    {
        String [] areas = conhecimento.split(";");
        ModelAndView mv = new ModelAndView();
        Avaliador av = repositoryAvaliador.getOne(id);
        if (av != null)
        {
            for(int i = 0; i < areas.length; i++)
            {
                AvaliadorConhecimento acFinal = new AvaliadorConhecimento();
                acFinal.setAvaliador(av.getId());
                acFinal.setConhecimento(Long.parseLong(areas[i]));
                repositoryAC.save(acFinal);
                System.out.println(acFinal.getId());
                System.out.println(acFinal.getAvaliador());
                System.out.println(acFinal.getConhecimento());    
            }
            mv.setViewName("redirect:lista-avaliadores");
        }
        else
        {
            List<AreaDeConhecimento> conhecimentos = repositoryConhecimentos.findAll();
            mv.addObject("conhecimentos", conhecimentos);
            AreaDeConhecimento conhecimento2 = new AreaDeConhecimento();
            mv.addObject("conhecimento", conhecimento2);
            mv.addObject("id", id);
            mv.setViewName("cadastro-area-conhecimento-avaliador");
        }
        return mv;
    }

    @RequestMapping(value = {"/listar-area-conhecimento-avaliador/{id}"}, method = RequestMethod.GET)
    public ModelAndView carregaListaAreaConhecimentoAvaliador (@PathVariable(value = "id", required = true) Long id, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        Avaliador avaliadorCarregar = new Avaliador();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                List<AreaDeConhecimento> conhecimentos = new ArrayList<>();
                List<AvaliadorConhecimento> todos = repositoryAC.findAll();
                for (AvaliadorConhecimento var : todos) {
                    if (var.getAvaliador().equals(id))
                    {
                        AreaDeConhecimento conhecimento = repositoryConhecimentos.getOne(var.getConhecimento());
                        System.out.println(conhecimento.getNome());
                        conhecimentos.add(conhecimento);
                    }
                }
                System.out.println(conhecimentos.size());
                mv.addObject("conhecimentos", conhecimentos);
                mv.addObject("id", id);
                mv.setViewName("lista-area-conhecimento-avaliador");
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
        
}