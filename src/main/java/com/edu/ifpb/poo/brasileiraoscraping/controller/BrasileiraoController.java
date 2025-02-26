package com.edu.ifpb.poo.brasileiraoscraping.controller;

import com.edu.ifpb.poo.brasileiraoscraping.BrasileiraoScraper;
import com.edu.ifpb.poo.brasileiraoscraping.Team;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class BrasileiraoController {

    @GetMapping("/brasileirao")
    public String exibirTabela(Model model) {
        String url = "https://ge.globo.com/futebol/brasileirao-serie-a/";
        List<Team> teams = BrasileiraoScraper.extrairDadosTabela(url);
        model.addAttribute("teams", teams);
        return "brasileirao"; // Nome do template Thymeleaf
    }
}
