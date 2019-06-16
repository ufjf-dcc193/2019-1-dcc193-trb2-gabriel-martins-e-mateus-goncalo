package br.ufjf.dcc193.trab02.Controllers;

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

import br.ufjf.dcc193.trab02.Models.Avaliador;
import br.ufjf.dcc193.trab02.Models.Revisao;
import br.ufjf.dcc193.trab02.Models.Trabalho;
import br.ufjf.dcc193.trab02.Persistence.AvaliadorRepository;
import br.ufjf.dcc193.trab02.Persistence.RevisaoRepository;
import br.ufjf.dcc193.trab02.Persistence.TrabalhoRepository;

@Controller
public class RevisaoController {

    @Autowired
    private RevisaoRepository repositoryRevisao;
    @Autowired
    private AvaliadorRepository repositoryAvaliador;
    @Autowired
    private TrabalhoRepository repositoryTrabalho;

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
                Set<Revisao> revisoes = revisor.getRevisoes();
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

    @RequestMapping(value = {"/revisar-trabalho/{id}"}, method = RequestMethod.GET)
    public ModelAndView carregaFazerRevisao (@PathVariable(value = "id", required = true) Long id, HttpSession session)
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
                Trabalho trabalho = repositoryTrabalho.getOne(id);
                Set<Revisao> revisoes = repositoryAvaliador.getOne(avaliador.getId()).getRevisoes();
                Revisao revisao = new Revisao();
                for (Revisao var : revisoes) {
                    if (var.getTrabalho().getId().equals(id))
                    {
                        if (var.getStatus() != 0)
                        {
                            String status;
                            mv.addObject("revisao", var);
                            if (var.getStatus() == 1)
                            {
                                status = "avaliado";
                            }
                            else if (var.getStatus() == 2)
                            {
                                status = "impedido";
                            }
                            else if (var.getStatus() == 3)
                            {
                                status = "validado";
                            }
                            else
                            {
                                status = "invalidado";
                            }
                            mv.addObject("status", status);
                            mv.setViewName("/trabalho-ja-corrigido");
                            return mv;
                        }
                        else
                        {
                            revisao = repositoryRevisao.getOne(var.getId());
                        }
                    }
                }
                mv.addObject("trabalho", trabalho);
                mv.addObject("idtrabalho", trabalho.getId());
                mv.addObject("revisao", revisao);
                mv.setViewName("/revisar-trabalho-avaliador");
            }
        }
        else
        {
            mv.setViewName("redirect:/login");
        }
        return mv;
    }

    @RequestMapping(value = {"/revisar-trabalho"}, params = "revisardepois", method = RequestMethod.POST)
    public ModelAndView salvarFazerRevisaoDepois (@RequestParam(value = "id", required = true) Long id, 
    Revisao revisao, HttpSession session)
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
                Boolean salvaBanco = false;
                if (revisao.getId() == null)
                {
                    salvaBanco = true;
                }
                Trabalho trabalho = repositoryTrabalho.getOne(id);
                Avaliador avaliadorFinal = repositoryAvaliador.getOne(avaliador.getId());
                revisao.setStatus(0);
                revisao.setAvaliador(avaliadorFinal);
                revisao.setTrabalho(trabalho);
                repositoryRevisao.save(revisao);
                if (salvaBanco)
                {
                    avaliadorFinal.getRevisoes().add(revisao);
                    trabalho.getRevisoes().add(revisao);
                    repositoryAvaliador.save(avaliadorFinal);
                    repositoryTrabalho.save(trabalho);
                }
                mv.setViewName("redirect:/lista-trabalhos-todos");
            }
        }
        else
        {
            mv.setViewName("redirect:/login");
        }
        return mv;
    }

    @RequestMapping(value = {"/revisar-trabalho"}, params = "revisaragora", method = RequestMethod.POST)
    public ModelAndView salvarFazerRevisaoAgora (@RequestParam(value = "id", required = true) Long id, 
    Revisao revisao, HttpSession session)
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
                Boolean salvaBanco = false;
                if (revisao.getId() == null)
                {
                    salvaBanco = true;
                }
                Trabalho trabalho = repositoryTrabalho.getOne(id);
                Avaliador avaliadorFinal = repositoryAvaliador.getOne(avaliador.getId());
                revisao.setStatus(1);
                revisao.setAvaliador(avaliadorFinal);
                revisao.setTrabalho(trabalho);
                repositoryRevisao.save(revisao);
                if (salvaBanco)
                {
                    avaliadorFinal.getRevisoes().add(revisao);
                    trabalho.getRevisoes().add(revisao);
                    repositoryAvaliador.save(avaliadorFinal);
                    repositoryTrabalho.save(trabalho);
                }
                mv.setViewName("redirect:/lista-trabalhos-todos");
            }
        }
        else
        {
            mv.setViewName("redirect:/login");
        }
        return mv;
    }

    @RequestMapping(value = {"/revisar-trabalho"}, params = "pular", method = RequestMethod.POST)
    public ModelAndView salvarPularRevisao (@RequestParam(value = "id", required = true) Long id, 
    Revisao revisao, HttpSession session)
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
                Boolean salvaBanco = false;
                if (revisao.getId() == null)
                {
                    salvaBanco = true;
                }
                revisao.setNota(0);
                revisao.setDescricao("");
                Trabalho trabalho = repositoryTrabalho.getOne(id);
                Avaliador avaliadorFinal = repositoryAvaliador.getOne(avaliador.getId());
                revisao.setStatus(0);
                revisao.setAvaliador(avaliadorFinal);
                revisao.setTrabalho(trabalho);
                repositoryRevisao.save(revisao);
                if (salvaBanco)
                {
                    avaliadorFinal.getRevisoes().add(revisao);
                    trabalho.getRevisoes().add(revisao);
                    repositoryAvaliador.save(avaliadorFinal);
                    repositoryTrabalho.save(trabalho);
                }
                mv.setViewName("redirect:/lista-trabalhos-todos");            }
        }
        else
        {
            mv.setViewName("redirect:/login");
        }
        return mv;
    }

}