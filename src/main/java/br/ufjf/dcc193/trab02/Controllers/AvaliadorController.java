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
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                List<Avaliador> avaliadores = repositoryAvaliador.findAll();
                mv.addObject("avaliadores", avaliadores);
                mv.setViewName("/lista-avaliadores");
            }
            else
            {
                mv.setViewName("redirect:/principal-adm");
            }
        }
        else
        {
            mv.setViewName("redirect:/login");
        }
        return mv;
    }

    @RequestMapping(value = {"/cadastro-avaliador"}, method = RequestMethod.GET)
    public ModelAndView carregaCadastro (HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                Avaliador avaliadorCarregar = new Avaliador();
                mv.addObject("avaliador", avaliadorCarregar);
                mv.setViewName("/cadastro-avaliador");
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

    @RequestMapping(value = {"/cadastro-avaliador"}, method = RequestMethod.POST)
    public ModelAndView realizaCadastro (Avaliador avaliador, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {
            Avaliador avaliadorF = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliadorF.getEmail().equals("admin"))
            {        
                Avaliador av = repositoryAvaliador.findByEmail(avaliador.getEmail());
                if (av == null)
                {
                    repositoryAvaliador.save(avaliador);
                    mv.setViewName("redirect:/lista-avaliadores");
                }
                else
                {
                    mv.addObject("avaliador", avaliador);
                    mv.setViewName("redirect:/cadastro-avaliador");
                }
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

    @RequestMapping(value = {"/cadastrar-area-conhecimento-avaliador/{id}"}, method = RequestMethod.GET)
    public ModelAndView carregaCadastroAreaConhecimento (@PathVariable(value = "id", required = true) Long id, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                List<AreaDeConhecimento> conhecimentos = repositoryConhecimentos.findAll();
                for (AreaDeConhecimento var : conhecimentos) {
                    var.setNome(var.getId() + " - " + var.getNome());
                }
                mv.addObject("conhecimentos", conhecimentos);
                AreaDeConhecimento conhecimento2 = new AreaDeConhecimento();
                mv.addObject("conhe", conhecimento2);
                mv.addObject("id", id);
                mv.setViewName("/cadastro-area-conhecimento-avaliador");
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

    @RequestMapping(value = {"/cadastrar-area-conhecimento-avaliador"}, method = RequestMethod.POST)
    public ModelAndView realizaCadastroAreaConhecimento (@RequestParam(value = "id", required = true) Long id, 
    @RequestParam(value = "conhecimentos", required = true) String conhecimento, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                Avaliador av = repositoryAvaliador.getOne(id);
                if (av != null)
                {
                    String [] areas = conhecimento.split(";");
                    for(int i = 0; i < areas.length; i++)
                    {
                        AvaliadorConhecimento acFinal = new AvaliadorConhecimento();
                        acFinal.setAvaliador(av.getId());
                        acFinal.setConhecimento(Long.parseLong(areas[i]));
                        AreaDeConhecimento q = repositoryConhecimentos.getOne(Long.parseLong(areas[i]));
                        acFinal.setNome(q.getNome());
                        repositoryAC.save(acFinal);
                    }
                    mv.setViewName("redirect:/lista-avaliadores");
                }
                else
                {
                    List<AreaDeConhecimento> conhecimentos = repositoryConhecimentos.findAll();
                    mv.addObject("conhecimentos", conhecimentos);
                    AreaDeConhecimento conhecimento2 = new AreaDeConhecimento();
                    mv.addObject("conhecimento", conhecimento2);
                    mv.addObject("id", id);
                    mv.setViewName("/cadastro-area-conhecimento-avaliador");
                }
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

    @RequestMapping(value = {"/listar-area-conhecimento-avaliador/{id}"}, method = RequestMethod.GET)
    public ModelAndView carregaListaAreaConhecimentoAvaliador (@PathVariable(value = "id", required = true) Long id, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                List<AvaliadorConhecimento> conhecimentos = new ArrayList<>();
                List<AvaliadorConhecimento> todos = repositoryAC.findAll();
                for (AvaliadorConhecimento var : todos) {
                    if (var.getAvaliador().equals(id))
                    {
                        conhecimentos.add(var);
                    }
                }
                mv.addObject("conhecimentos", conhecimentos);
                mv.addObject("id", id);
                mv.setViewName("/lista-area-conhecimento-avaliador");
            }
            else
            {
                mv.addObject("avaliador", avaliador);
                mv.setViewName("redirect:/principal-avaliador");
            }
        }
        else
        {
            mv.setViewName("redirect:/login");
        }
        return mv;
    }
        
    @RequestMapping(value = { "/excluir-area-conhecimento-avaliador/{id}" }, method = RequestMethod.GET)
    public ModelAndView carregaExcluir(@PathVariable(value = "id", required = true) Long id, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                repositoryAC.deleteById(id);
                mv.setViewName("redirect:/lista-avaliadores");
            }
            else
            {
                mv.setViewName("redirect:/principal-adm");
            }
        }
        else
        {
            mv.setViewName("redirect:/login");
        }
        return mv;    
    }

    @RequestMapping(value = {"/editar-avaliador/{id}"}, method = RequestMethod.GET)
    public ModelAndView carregaEditar (@PathVariable(value = "id", required = true) Long id, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                Avaliador avaliadorCarregar = new Avaliador();
                avaliadorCarregar = repositoryAvaliador.getOne(id);
                mv.addObject("avaliador", avaliadorCarregar);
                mv.addObject("id", id);
                mv.setViewName("/cadastro-avaliador");
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

    @RequestMapping(value = {"/editar-avaliador"}, method = RequestMethod.POST)
    public ModelAndView editarCadastro (@RequestParam(value = "id", required = true) Long id, Avaliador avaliador, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {
            Avaliador avaliadorF = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliadorF.getEmail().equals("admin"))
            {        
                Avaliador av = repositoryAvaliador.findByEmail(avaliador.getEmail());
                if (av == null)
                {
                    avaliador.setId(id);
                    repositoryAvaliador.save(avaliador);
                    mv.setViewName("redirect:/lista-avaliadores");
                }
                else
                {
                    mv.addObject("avaliador", avaliador);
                    mv.setViewName("/editar-avaliador");
                }
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

    @RequestMapping(value = { "/excluir-avaliador/{id}" }, method = RequestMethod.GET)
    public ModelAndView carregaExcluirAvaliador(@PathVariable(value = "id", required = true) Long id, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                repositoryAvaliador.deleteById(id);
                mv.setViewName("redirect:/lista-avaliadores");
            }
            else
            {
                mv.setViewName("redirect:/principal-adm");
            }
        }
        else
        {
            mv.setViewName("redirect:/login");
        }
        return mv;    
    }

    @RequestMapping({"/lista-conhecimentos-avaliador"})
    public ModelAndView carrega (HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                mv.setViewName("redirect:/index");
            }
            else
            {
                List<AvaliadorConhecimento> avaliador_conhecimento = repositoryAC.findAllByAvaliadorId(avaliador.getId());
                List<AreaDeConhecimento> conhecimentos = new ArrayList<>();
                for (AvaliadorConhecimento var : avaliador_conhecimento) {
                    AreaDeConhecimento a = repositoryConhecimentos.getOne(var.getConhecimentoId());
                    conhecimentos.add(a);
                }
                mv.addObject("conhecimentos", conhecimentos);
                mv.addObject("avaliador", avaliador);
                mv.setViewName("/lista-conhecimentos-avaliador");
            }
        }
        else
        {
            mv.setViewName("redirect:/login");
        }
        return mv;
    }

}