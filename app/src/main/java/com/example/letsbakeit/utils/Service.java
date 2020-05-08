package com.example.letsbakeit.utils;

import com.example.letsbakeit.model.Ingredient;
import com.example.letsbakeit.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Ingredient>> getIngredients();

}
