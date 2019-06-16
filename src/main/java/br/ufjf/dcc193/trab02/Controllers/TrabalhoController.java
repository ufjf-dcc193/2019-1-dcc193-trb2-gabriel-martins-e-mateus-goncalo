package br.ufjf.dcc193.trab02.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import br.ufjf.dcc193.trab02.Models.Revisao;
import br.ufjf.dcc193.trab02.Models.Trabalho;
import br.ufjf.dcc193.trab02.Persistence.AreaDeConhecimentoRepository;
import br.ufjf.dcc193.trab02.Persistence.AvaliadorConhecimentoRepository;
import br.ufjf.dcc193.trab02.Persistence.AvaliadorRepository;
import br.ufjf.dcc193.trab02.Persistence.TrabalhoRepository;

@Controller
public class TrabalhoController {

    @Autowired
    private TrabalhoRepository repositoryTrabalho;
    @Autowired
    private AreaDeConhecimentoRepository repositoryConhecimento;
    @Autowired
    private AvaliadorConhecimentoRepository repositoryAC;
    @Autowired
    private AvaliadorRepository repositoryAvaliador;

    @RequestMapping({"/lista-trabalhos"})
    public ModelAndView carregaTrabalhos (HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                List<Trabalho> trabalhos = repositoryTrabalho.findAll();
                mv.addObject("trabalhos", trabalhos);
                mv.setViewName("/lista-trabalhos");
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

    @RequestMapping(value = {"/cadastro-trabalho"}, method = RequestMethod.GET)
    public ModelAndView carregaCadastro (HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                Trabalho trabalho = new Trabalho();
                List<AreaDeConhecimento> conhecimentos = repositoryConhecimento.findAll();
                mv.addObject("conhecimentos", conhecimentos);
                mv.addObject("trabalho", trabalho);
                mv.setViewName("/cadastro-trabalho");
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

    @RequestMapping(value = {"/cadastro-trabalho"}, method = RequestMethod.POST)
    public ModelAndView realizaCadastro (Trabalho trabalho, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                if (trabalho != null)
                {
                    repositoryTrabalho.save(trabalho);
                    mv.setViewName("redirect:/lista-trabalhos");
                }
                else
                {
                    mv.addObject("trabalho", trabalho);
                    mv.setViewName("redirect:/cadastro-trabalho");
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
    
    @RequestMapping(value = {"/editar-trabalho/{id}"}, method = RequestMethod.GET)
    public ModelAndView carregaEditar (@PathVariable(value = "id", required = true) Long id, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                Trabalho trabalho = new Trabalho();
                trabalho = repositoryTrabalho.getOne(id);
                List<AreaDeConhecimento> conhecimentos = repositoryConhecimento.findAll();
                mv.addObject("conhecimentos", conhecimentos);
                mv.addObject("trabalho", trabalho);
                mv.addObject("id", id);
                mv.setViewName("/editar-trabalho");
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

    @RequestMapping(value = {"/editar-trabalho"}, method = RequestMethod.POST)
    public ModelAndView realizaEditar (@RequestParam(value = "id", required = true) Long id, Trabalho trabalho, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                if (trabalho != null)
                {
                    trabalho.setId(id);
                    repositoryTrabalho.save(trabalho);
                    mv.setViewName("redirect:/lista-trabalhos");
                }
                else
                {
                    mv.addObject("trabalho", trabalho);
                    mv.setViewName("/editar-trabalho");
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

    @RequestMapping(value = { "/excluir-trabalho/{id}" }, method = RequestMethod.GET)
    public ModelAndView carregaExcluir(@PathVariable(value = "id", required = true) Long id, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                repositoryTrabalho.deleteById(id);
                mv.setViewName("redirect:/lista-trabalhos");
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

    @RequestMapping({"/lista-trabalhos-todos"})
    public ModelAndView carregaTrabalhosParaAvaliar (HttpSession session)
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
                List<AvaliadorConhecimento> todosParaRevisar = repositoryAC.findAllByAvaliadorId(avaliador.getId());
                List<Trabalho> trabalhos = new ArrayList<>();
                for (AvaliadorConhecimento var : todosParaRevisar) {
                    AreaDeConhecimento a = repositoryConhecimento.getOne(var.getConhecimentoId());
                    List<Trabalho> trabalhoAux = repositoryTrabalho.findByTrabalhoAreaDeConhecimento(a);
                    for (Trabalho var2 : trabalhoAux) {
                        trabalhos.add(var2);
                    }
                }
                mv.addObject("trabalhos", trabalhos);
                mv.setViewName("/lista-trabalhos-todos");
            }
        }
        else
        {
            mv.setViewName("redirect:/login");
        }
        return mv;
    }

    @RequestMapping({"/lista-trabalhos-do-conhecimento/{id}"})
    public ModelAndView listaTrabalhosConhecimento (@PathVariable(value = "id", required = true) Long id, HttpSession session)
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
                AreaDeConhecimento trabalhoAreaDeConhecimento = repositoryConhecimento.getOne(id);
                List<Trabalho> trabalhos = repositoryTrabalho.findByTrabalhoAreaDeConhecimento(trabalhoAreaDeConhecimento);
                mv.addObject("trabalhos", trabalhos);
                mv.setViewName("/lista-trabalhos-do-conhecimento");
            }
        }
        else
        {
            mv.setViewName("redirect:/login");
        }
        return mv;
    }

    @RequestMapping({"/lista-trabalhos-do-conhecimento-adm/{id}"})
    public ModelAndView listaTrabalhosConhecimentoAdm (@PathVariable(value = "id", required = true) Long id, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                AvaliadorConhecimento ac = repositoryAC.getOne(id);                
                AreaDeConhecimento trabalhoAreaDeConhecimento = repositoryConhecimento.getOne(ac.getConhecimentoId());
                List<Trabalho> trabalhos = repositoryTrabalho.findByTrabalhoAreaDeConhecimento(trabalhoAreaDeConhecimento);
                mv.addObject("trabalhos", trabalhos);
                mv.setViewName("/lista-trabalhos-do-conhecimento-adm");
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

    @RequestMapping({"/lista-trabalhos-do-conhecimento-adm2/{id}"})
    public ModelAndView listaTrabalhosConhecimentoAdm2(@PathVariable(value = "id", required = true) Long id, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                AreaDeConhecimento trabalhoAreaDeConhecimento = repositoryConhecimento.getOne(id);
                List<Trabalho> trabalhos = repositoryTrabalho.findByTrabalhoAreaDeConhecimento(trabalhoAreaDeConhecimento);
                mv.addObject("trabalhos", trabalhos);
                mv.setViewName("/lista-trabalhos-do-conhecimento-adm");
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

    @RequestMapping({"/lista-trabalhos-avaliados"})
    public ModelAndView listaTrabalhosAvaliados (HttpSession session)
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
                List<Revisao> revisoesEnviar = new ArrayList<>();
                Set<Revisao> revisoes = repositoryAvaliador.getOne(avaliador.getId()).getRevisoes();
                for (Revisao var : revisoes) {
                    if (var.getStatus() == 0)
                    {
                        var.setStatusNome("a avaliar");
                    }
                    else if(var.getStatus() == 1)
                    {
                        var.setStatusNome("avaliado");
                        revisoesEnviar.add(var);
                    }
                    else if(var.getStatus() == 2)
                    {
                        var.setStatusNome("impedido");
                    }
                    else if(var.getStatus() == 3)
                    {
                        var.setStatusNome("validado");
                        revisoesEnviar.add(var);
                    }
                    else
                    {
                        var.setStatusNome("invalidado");
                        revisoesEnviar.add(var);
                    }
                }
                mv.addObject("revisoes", revisoesEnviar);
                mv.setViewName("/lista-trabalhos-avaliados");
            }
        }
        else
        {
            mv.setViewName("redirect:/login");
        }
        return mv;
    }

}