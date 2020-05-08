package com.example.letsbakeit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.letsbakeit.adapters.RecipeAdapter;
import com.example.letsbakeit.model.Ingredient;
import com.example.letsbakeit.model.Recipe;
import com.example.letsbakeit.utils.Client;
import com.example.letsbakeit.utils.Service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private TextView tv_name_list;
    private List<Recipe> mRecipes;
    private ArrayList<Recipe> recipeList;
    private RecipeAdapter mRecipeAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    public void initialize() {
        mRecipes = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecipeAdapter = new RecipeAdapter(this, mRecipes);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Service service = Client.getClient().create(Service.class);

        Call<List<Recipe>> callRecipes = service.getRecipes();
        callRecipes.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (!response.isSuccessful()) {
                    tv_name_list.setText("Code: " + response.code());
                    System.out.println(response.code());
                    return;
                }
                List<Recipe> recipes = response.body();
                mRecyclerView.setAdapter(new RecipeAdapter(getApplicationContext(), recipes));
                System.out.println("haha " + recipes.get(1).getmIngredients().get(0).getmIngredient());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });


    }
}
