package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.SeasonRecord;
import br.com.alura.screenmatch.model.ShowRecord;
import br.com.alura.screenmatch.model.EpisodeRecord;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConvertData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private final static Scanner input = new Scanner(System.in);
    private final static ConsumoAPI consumoApi = new ConsumoAPI();
    private final static ConvertData convertData = new ConvertData();

    private final String URL = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=44fb9be2";

    public void showMenu() {
        System.out.println("Digite o nome da série para busca: ");

        var showName = input.nextLine();
        String showSearch = URL + showName.replace(" ", "+") + API_KEY;

        var json = consumoApi.getData(showSearch);

        ShowRecord show = convertData.convert(json, ShowRecord.class);

        System.out.println("Informações sobre a série");
        System.out.println(show);

        List<SeasonRecord> seasonList = new ArrayList<>();

		for (int i = 1; i <= show.numberOfSeasons(); i++) {
			json = consumoApi.getData(URL + showName.replace(" ", "+") + "&season=" + i + API_KEY);

			SeasonRecord season = convertData.convert(json, SeasonRecord.class);
			seasonList.add(season);
		}

        List<EpisodeRecord> episodes = seasonList.stream()
                .flatMap(t -> t.episodes().stream()).toList();

        // Lista melhores episódios
//        List<EpisodeRecord> bestEpisodes = episodes.stream()
//                .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
//                .sorted(Comparator.comparing(EpisodeRecord::rating).reversed())
//                .limit(5).toList();
//
//        bestEpisodes.forEach(System.out::println);

        // Seleciona todos episódios através de stream
        List<Episodio> episodios = seasonList.stream()
                .flatMap(t -> t.episodes().stream()
                        .map(e -> new Episodio(e.number(), e))).toList();

        episodios.forEach(System.out::println);

        // Filtra episódios a partir de uma data digitada pelo usuário
        System.out.println("A partir de que ano gostaria de ver os episódios? ");
        var year = input.nextInt();
        input.nextLine();

        LocalDate date = LocalDate.of(year, 1, 1);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream().filter(e -> e.getReleaseDate() != null && e.getReleaseDate().isAfter(date))
                .forEach(e -> System.out.print(
                        "\nTemporada: " + e.getSeason() +
                        "\nTítulo: " + e.getTitle() +
                        "\nNº Episódio: " + e.getEpisodeNumber() +
                        "\nData de lançamento: " + e.getReleaseDate().format(dtf) +
                        "\nAvaliação: " + e.getRating()
                ));
    }
}
