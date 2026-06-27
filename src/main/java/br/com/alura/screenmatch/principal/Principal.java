package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner sc = new Scanner(System.in);
    //https://www.omdbapi.com/?t=gilmore+girls&season=5&apikey=6585022c
    private ConsumoApi consumoApi = new ConsumoApi();

    private ConverteDados conversor = new ConverteDados();

    private List<DadosTemporada> dadosTemporadaList = new ArrayList<>();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";

    private final String API_KEY = "&apikey=6585022c";

    public void exibirMenu() {
        System.out.println("Digite o nome da série:");
        var nomeSerie = sc.nextLine();
        var enderco = ENDERECO + nomeSerie.replace(" ", "+") + API_KEY;
        var json = consumoApi.obterDados(enderco);
        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            json = consumoApi.obterDados(enderco+"&season="+i);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            dadosTemporadaList.add(dadosTemporada);
        }
        dadosTemporadaList.forEach(t -> {
            System.out.println("Temporada: " + t.numero());
            t.epsodios().forEach(e -> System.out.println("Ep "+e.numero()+": "+ e.titulo()));
        });




    }
}
