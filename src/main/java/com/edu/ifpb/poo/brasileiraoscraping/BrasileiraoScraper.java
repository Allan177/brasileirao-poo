package com.edu.ifpb.poo.brasileiraoscraping;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BrasileiraoScraper {

    public static List<Map<String, String>> extrairDadosTabela(String url) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get(url);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".classificacao__pontos-corridos")));

            String html = driver.findElement(By.cssSelector(".classificacao__pontos-corridos")).getAttribute("innerHTML");

            Document doc = Jsoup.parse(html);

            Elements linhasEquipes = doc.select("table.tabela__equipes tbody tr");
            Elements linhasPontos = doc.select("table.tabela__pontos tbody tr");


            List<Map<String, String>> tabelaData = new ArrayList<>();

            for (int i = 0; i < linhasEquipes.size(); i++) {
                Map<String, String> linhaData = new LinkedHashMap<>();

                Element linhaEquipe = linhasEquipes.get(i);
                Element linhaPonto = linhasPontos.get(i);


                linhaData.put("Posicao", linhaEquipe.selectFirst("td.classificacao__equipes--posicao").text());
                linhaData.put("Time", linhaEquipe.selectFirst("strong.classificacao__equipes--nome").text());
                linhaData.put("Variacao", linhaEquipe.selectFirst("span.classificacao__icone").parent().text());


                Elements pontos = linhaPonto.select("td.classificacao__pontos");

                linhaData.put("P", pontos.get(0).text());
                linhaData.put("J", pontos.get(1).text());
                linhaData.put("V", pontos.get(2).text());
                linhaData.put("E", pontos.get(3).text());
                linhaData.put("D", pontos.get(4).text());
                linhaData.put("GP", pontos.get(5).text());
                linhaData.put("GC", pontos.get(6).text());
                linhaData.put("SG", pontos.get(7).text());
                linhaData.put("%", pontos.get(8).text());

                String ultimosJogos = "";
                Elements ultimosJogosElements = linhaPonto.select("span.classificacao__ultimos_jogos");
                for (Element jogo : ultimosJogosElements) {
                    String classe = jogo.className();
                    if (classe.contains("classificacao__ultimos_jogos--v")) {
                        ultimosJogos += "V";
                    } else if (classe.contains("classificacao__ultimos_jogos--d")) {
                        ultimosJogos += "D";
                    } else if (classe.contains("classificacao__ultimos_jogos--e")) {
                        ultimosJogos += "E";
                    }

                }
                linhaData.put("Últimos Jogos", ultimosJogos);

                tabelaData.add(linhaData);
            }


            return tabelaData;

        } finally {
            driver.quit();
        }
    }


    public static void main(String[] args) {
        String url = "https://ge.globo.com/futebol/brasileirao-serie-a/";

        try {
            List<Map<String, String>> tabela = extrairDadosTabela(url);
            System.out.println("Dados da tabela do Brasileirão:");
            tabela.forEach(linha -> {
                linha.forEach((key, value) -> System.out.println(key + ": " + value));
                System.out.println("-------------------");
            });
        } catch (Exception e) {
            System.err.println("Erro ao fazer web scraping: " + e.getMessage());
        }
    }
}