package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.Season;
import br.com.alura.screenmatch.model.Show;
import br.com.alura.screenmatch.model.Episode;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConvertData;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

        Show show = convertData.convert(json, Show.class);

        System.out.println("Informações sobre a série");
        System.out.println(show);

        List<Season> seasonList = new ArrayList<>();

		for (int i = 1; i <= show.numberOfSeasons(); i++) {
			json = consumoApi.getData(URL + showName + "&season=" + i + API_KEY);

			Season season = convertData.convert(json, Season.class);
			seasonList.add(season);
		}

        System.out.println("Temporadas");

        seasonList.forEach(s -> s.episodes().forEach(e -> System.out.println(e.title())));
    }
}
