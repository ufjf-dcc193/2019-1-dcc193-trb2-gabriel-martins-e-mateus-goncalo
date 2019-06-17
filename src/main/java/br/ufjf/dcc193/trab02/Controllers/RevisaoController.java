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

    @RequestMapping(value = {"/lista-revisoes"}, method = RequestMethod.GET)
    public ModelAndView carregaRevisaoAdministrador (HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                List<Revisao> revisoes = repositoryRevisao.findAll();
                List<Revisao> revisoesEnviar = new ArrayList<>();
                for (Revisao var : revisoes) {
                    if (var.getStatus() != 0 && var.getStatus() != 2)
                    {
                        if(var.getStatus() == 1)
                        {
                            var.setStatusNome("avaliado");
                            revisoesEnviar.add(var);
                        }
                        else if(var.getStatus() == 3)
                        {
                            var.setStatusNome("validado");
                            revisoesEnviar.add(var);
                        }
                        else if(var.getStatus() == 4)
                        {
                            var.setStatusNome("invalidado");
                            revisoesEnviar.add(var);
                        }    
                    }
                }
                mv.addObject("revisoes", revisoesEnviar);
                mv.setViewName("lista-revisoes");
            }
            else
            {
                mv.setViewName("redirect:principal-avaliador");
            }
        }
        else
        {
            mv.setViewName("redirect:login");
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
                mv.setViewName("redirect:principal-adm");
            }
            else
            {
                Avaliador revisor = repositoryAvaliador.getOne(avaliador.getId());
                Set<Revisao> revisoes = revisor.getRevisoes();
                mv.addObject("revisoes", revisoes);
                mv.setViewName("lista-revisoes-avaliador");
            }
        }
        else
        {
            mv.setViewName("redirect:login");
        }
        return mv;
    }

    @RequestMapping(value = {"/listar-revisoes-trabalho/{id}"}, method = RequestMethod.GET)
    public ModelAndView carregaRevisoesTrabalho (@PathVariable(value = "id", required = true) Long id, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                Trabalho trabalho = repositoryTrabalho.getOne(id);
                List<Revisao> revisoesEnviar = new ArrayList<>();
                Set<Revisao> revisoes = trabalho.getRevisoes();
                for (Revisao var : revisoes) {
                    if(var.getStatus() == 1)
                    {
                        var.setStatusNome("avaliado");
                        revisoesEnviar.add(var);
                    }
                    else if(var.getStatus() == 3)
                    {
                        var.setStatusNome("validado");
                        revisoesEnviar.add(var);
                    }
                    else if(var.getStatus() == 4)
                    {
                        var.setStatusNome("invalidado");
                        revisoesEnviar.add(var);
                    }
                }
                mv.addObject("revisoes", revisoesEnviar);
                mv.setViewName("lista-revisoes-trabalho");
            }
            else
            {
                mv.setViewName("redirect:principal-avaliador");
            }
        }
        else
        {
            mv.setViewName("redirect:login");
        }
        return mv;
    }

    @RequestMapping(value = {"/lista-revisoes-avaliador/{id}"}, method = RequestMethod.GET)
    public ModelAndView carregaRevisoesTrabalhoAvaliador (@PathVariable(value = "id", required = true) Long id, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                Avaliador av = repositoryAvaliador.getOne(id);
                List<Revisao> revisoesEnviar = new ArrayList<>();
                Set<Revisao> revisoes = av.getRevisoes();
                for (Revisao var : revisoes) {
                    if(var.getStatus() == 1)
                    {
                        var.setStatusNome("avaliado");
                        revisoesEnviar.add(var);
                    }
                }
                mv.addObject("revisoes", revisoesEnviar);
                mv.setViewName("lista-revisoes-trabalho");
            }
            else
            {
                mv.setViewName("redirect:principal-avaliador");
            }
        }
        else
        {
            mv.setViewName("redirect:login");
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
                mv.setViewName("redirect:principal-adm");
            }
            else
            {
                Trabalho trabalho = repositoryTrabalho.getOne(id);
                Set<Revisao> revisoes = repositoryAvaliador.getOne(avaliador.getId()).getRevisoes();
                Revisao revisao = new Revisao();
                for (Revisao var : revisoes) {
                    if (var.getTrabalho().getId().equals(id))
                    {
                        if (var.getStatus() != 0 && var.getStatus() != 2)
                        {
                            String status;
                            mv.addObject("revisao", var);
                            if (var.getStatus() == 1)
                            {
                                status = "avaliado";
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
                            mv.setViewName("trabalho-ja-corrigido");
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
                mv.setViewName("revisar-trabalho-avaliador");
            }
        }
        else
        {
            mv.setViewName("redirect:login");
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
                mv.setViewName("redirect:principal-adm");
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
                mv.setViewName("redirect:lista-trabalhos-todos");
            }
        }
        else
        {
            mv.setViewName("redirect:login");
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
                mv.setViewName("redirect:principal-adm");
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
                mv.setViewName("redirect:lista-trabalhos-todos");
            }
        }
        else
        {
            mv.setViewName("redirect:login");
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
                mv.setViewName("redirect:principal-adm");
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
                revisao.setStatus(2);
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
                mv.setViewName("redirect:lista-trabalhos-todos");            }
        }
        else
        {
            mv.setViewName("redirect:login");
        }
        return mv;
    }

    @RequestMapping(value = {"/mudar-status-revisao/{id}"}, method = RequestMethod.GET)
    public ModelAndView carregaMudarStatusRevisao (@PathVariable(value = "id", required = true) Long id, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                Revisao revisao = repositoryRevisao.getOne(id);
                if(revisao.getStatus() == 1)
                {
                    revisao.setStatusNome("avaliado");
                }
                else if(revisao.getStatus() == 3)
                {
                    revisao.setStatusNome("validado");
                }
                else if(revisao.getStatus() == 4)
                {
                    revisao.setStatusNome("invalidado");
                }
                
                mv.addObject("statusatual", revisao.getStatusNome());
                mv.addObject("revisao", revisao);
                mv.addObject("id", revisao.getId());
                mv.setViewName("mudar-status-revisao");
            }
            else
            {
                mv.setViewName("redirect:principal-avaliador");
            }
        }
        else
        {
            mv.setViewName("redirect:login");
        }
        return mv;
    }

    @RequestMapping(value = {"/mudar-status-revisao"}, method = RequestMethod.POST)
    public ModelAndView salvarMudarStatus (@RequestParam(value = "id", required = true) Long id, 
    @RequestParam(value = "statusmudado", required = true) Integer statusNovo, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Avaliador avaliador = (Avaliador) session.getAttribute("usuarioLogado");
            if (avaliador.getEmail().equals("admin"))
            {
                Revisao revisao = repositoryRevisao.getOne(id);
                revisao.setStatus(statusNovo);
                repositoryRevisao.save(revisao);
                mv.setViewName("redirect:lista-revisoes");
            }
            else
            {
                mv.setViewName("redirect:principal-avaliador");                
            }
        }
        else
        {
            mv.setViewName("redirect:login");
        } 
        return mv;
    }

}