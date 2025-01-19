package com.edu.ifpb.poo.brasileiraoscraping.service;

import com.edu.ifpb.poo.brasileiraoscraping.BrasileiraoScraper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BrasileiraoService {

    private static final String URL_BRASILEIRAO = "https://ge.globo.com/futebol/brasileirao-serie-a/";

    public List<Map<String, String>> getTabela() {
        return BrasileiraoScraper.extrairDadosTabela(URL_BRASILEIRAO);
    }
}