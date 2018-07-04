package br.com.brunocardoso.desafionw.api;

import java.util.List;

import br.com.brunocardoso.desafionw.model.Hero;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HeroesService {

    @POST("heroes")
    @Headers("Content-Type: application/json")
    Call<Void> makeHeroe(@Body Hero hero);

    @POST("heroes")
    @Headers("Content-Type: application/json")
    Call<Void> updateHeroe(@Body Hero hero);

    @DELETE("heroes/{id}")
    Call<Void> deleteHeroe(@Path("id") int id);

    @GET("heroes/{id}")
    Call<Hero> getHeroe(@Path("id") int id);

    @GET("heroes")
    Call<List<Hero>> getHeroes();
}
