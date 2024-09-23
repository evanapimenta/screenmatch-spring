package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.SeasonRecord;
import br.com.alura.screenmatch.model.ShowRecord;
import br.com.alura.screenmatch.model.EpisodeRecord;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConvertData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

        // Lista episódios
//        List<EpisodeRecord> episodes = seasonList.stream()
//                .flatMap(t -> t.episodes().stream()).toList();
        // Lista episódios

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
        // Seleciona todos episódios através de stream


//        // Filtra episódios a partir de uma data digitada pelo usuário
//        System.out.println("A partir de que ano gostaria de ver os episódios? ");
//        var year = input.nextInt();
//        input.nextLine();
//
//        LocalDate date = LocalDate.of(year, 1, 1);
//
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodios.stream().filter(e -> e.getReleaseDate() != null && e.getReleaseDate().isAfter(date))
//                .forEach(e -> System.out.print(
//                        "\nTemporada: " + e.getSeason() +
//                        "\nTítulo: " + e.getTitle() +
//                        "\nNº Episódio: " + e.getEpisodeNumber() +
//                        "\nData de lançamento: " + e.getReleaseDate().format(dtf) +
//                        "\nAvaliação: " + e.getRating()
//                ));
//
        // Filtra episódios a partir de uma data digitada pelo usuário


        // Procura episódio por trecho
//        System.out.println("Digite um trecho de título: ");
//        var episodeTitle = input.nextLine();
//
//        Optional<Episodio> searchedEpisode = episodios.stream()
//                .filter(e -> e.getTitle().toUpperCase().contains(episodeTitle.toUpperCase()))
//                .findFirst();
//
//        if (searchedEpisode.isPresent()) {
//            System.out.println("Nome do episódio: " + searchedEpisode.get().getTitle() +
//                    " - Episódio: " + searchedEpisode.get().getEpisodeNumber());
//            System.out.println("Temporada: " + searchedEpisode.get().getSeason());
//        }
        // Procura episódio por trecho

        // Exibe média por temporada
//        Map<Integer, Double> ratingBySeason = episodios.stream()
//                .filter(e -> e.getRating() > 0.0)
//                .collect(Collectors.groupingBy(Episodio::getSeason, Collectors.averagingDouble(Episodio::getRating)));
//
//        System.out.println(ratingBySeason);
        // Exibe média por temporada

        // Exibe estatísticas gerais dos episódios
        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getRating));

        System.out.println("Média avaliação episódios: " + est.getAverage());
        System.out.println("Avaliação melhor episódio: " + est.getMax());
        System.out.println("Avaliação pior episódio: " + est.getMin());
        System.out.println("Total de avaliações: " + est.getCount());
        // Exibe estatísticas gerais dos episódios

    }
}
