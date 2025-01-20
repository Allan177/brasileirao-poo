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

    public static List<Team> extrairDadosTabela(String url) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // comentado exibe o navegador
        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get(url);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".classificacao__pontos-corridos")));

            String html = driver.findElement(By.cssSelector(".classificacao__pontos-corridos")).getAttribute("innerHTML");

            Document doc = Jsoup.parse(html);

            Elements linhasEquipes = doc.select("table.tabela__equipes tbody tr");
            Elements linhasPontos = doc.select("table.tabela__pontos tbody tr");

            List<Team> teams = new ArrayList<>();

            for (int i = 0; i < linhasEquipes.size(); i++) {
                Element linhaEquipe = linhasEquipes.get(i);
                Element linhaPonto = linhasPontos.get(i);

                String position = linhaEquipe.selectFirst("td.classificacao__equipes--posicao").text();
                String name = linhaEquipe.selectFirst("strong.classificacao__equipes--nome").text();
                Elements pontos = linhaPonto.select("td.classificacao__pontos");

                String points = pontos.get(0).text();
                String gamesPlayed = pontos.get(1).text();
                String wins = pontos.get(2).text();
                String draws = pontos.get(3).text();
                String losses = pontos.get(4).text();
                String goalsFor = pontos.get(5).text();
                String goalsAgainst = pontos.get(6).text();
                String goalDifference = pontos.get(7).text();
                String performance = pontos.get(8).text();

                StringBuilder lastGames = new StringBuilder();
                Elements lastGamesElements = linhaPonto.select("span.classificacao__ultimos_jogos");
                for (Element jogo : lastGamesElements) {
                    String classe = jogo.className();
                    if (classe.contains("classificacao__ultimos_jogos--v")) {
                        lastGames.append("V");
                    } else if (classe.contains("classificacao__ultimos_jogos--d")) {
                        lastGames.append("D");
                    } else if (classe.contains("classificacao__ultimos_jogos--e")) {
                        lastGames.append("E");
                    }
                }

                Team team = new Team(position, name, points, gamesPlayed, wins, draws, losses, goalsFor, goalsAgainst, goalDifference, performance, lastGames.toString());
                teams.add(team);
            }

            return teams;

        } finally {
            driver.quit();
        }
    }


    public static void main(String[] args) {
        String url = "https://ge.globo.com/futebol/brasileirao-serie-a/";

        try {
            List<Team> tabela = extrairDadosTabela(url);
            System.out.println("Tabela do Brasileirão:");
            System.out.printf(
                    "%-5s %-20s %-3s %-3s %-3s %-3s %-3s %-3s %-3s %-3s %-6s %s\n",
                    "Pos", "Time", "P", "J", "V", "E", "D", "GP", "GC", "SG", "%", "Últimos Jogos"
            );
            System.out.println("--------------------------------------------------------------------------------------");
            tabela.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("Erro ao fazer web scraping: " + e.getMessage());
        }
    }
}