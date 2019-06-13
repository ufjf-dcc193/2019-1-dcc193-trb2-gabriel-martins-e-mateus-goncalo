package br.ufjf.dcc193.trab02.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @RequestMapping({"/", "/index.html"})
    public String index ()
    {
        return "index";
    }
        
}