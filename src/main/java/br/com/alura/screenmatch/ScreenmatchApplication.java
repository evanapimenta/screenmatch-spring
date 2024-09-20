package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.Episode;
import br.com.alura.screenmatch.model.Season;
import br.com.alura.screenmatch.model.Show;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConvertData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoAPI();
		var json = consumoApi.getData("https://www.omdbapi.com/?t=the+boys&apikey=44fb9be2");

		ConvertData conversor = new ConvertData();

		Show show = conversor.getData(json, Show.class);
		System.out.println(show);

		json = consumoApi.getData("https://www.omdbapi.com/?t=the+boys&season=1&episode=2&apikey=44fb9be2");
		Episode episode = conversor.getData(json, Episode.class);
		System.out.println(episode);

		json = consumoApi.getData("https://www.omdbapi.com/?t=the+boys&season=1&apikey=44fb9be2");
		Season season = conversor.getData(json, Season.class);
		System.out.println(season);
	}

}
