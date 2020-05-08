package com.example.letsbakeit;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.letsbakeit.adapters.IngredientAdapter;
import com.example.letsbakeit.adapters.StepAdapter;
import com.example.letsbakeit.fragments.StepFragment;
import com.example.letsbakeit.model.Ingredient;
import com.example.letsbakeit.model.Recipe;
import com.example.letsbakeit.model.Step;
import com.example.letsbakeit.utils.Client;
import com.example.letsbakeit.utils.Service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeActivity extends AppCompatActivity {

    private StepFragment stepFragment = new StepFragment();

    private static final String INTENT_RECIPE_KEY = "recipeObject";

    // INGREDIENT member variables
    private RecyclerView mRV_Ingredients;
    private List<Ingredient> mIngredients;
    private ArrayList<Ingredient> ingredientList;
    private IngredientAdapter mIngredientAdapter;

    // STEP member variables
    private RecyclerView mRV_Steps;
    private List<Step> mSteps;
    private ArrayList<Step> stepList;
    private StepAdapter mStepAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Recipe recipe = getIntent().getExtras().getParcelable(INTENT_RECIPE_KEY);
        setTitle(recipe.getmName());

        TextView tv_Ingredients_Title = (TextView) findViewById(R.id.tv_ingredients_title);
        TextView tv_Steps_Title = (TextView) findViewById(R.id.tv_steps_title);

        // Load all the INGREDIENTS and STEPS for the selected recipe
        init();
        tv_Ingredients_Title.setText("Ingredients");
        tv_Steps_Title.setText("Steps");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, stepFragment)
                .addToBackStack("step")
                .commit();
    }

    private void init() {
        mIngredients = new ArrayList<>();
        mRV_Ingredients = (RecyclerView) findViewById(R.id.rv_ingredients);
        mIngredientAdapter = new IngredientAdapter(this, mIngredients);
        mRV_Ingredients.setLayoutManager(new LinearLayoutManager(this));

//        mSteps = new ArrayList<>();
//        mRV_Steps = (RecyclerView) findViewById(R.id.rv_steps);
//        mStepAdapter = new StepAdapter(this, mSteps);
//        mRV_Steps.setLayoutManager(new LinearLayoutManager(this));

        final Recipe recipe = getIntent().getExtras().getParcelable(INTENT_RECIPE_KEY);

        // Pull Recipe's INGREDIENTS and STEPS from server
        final Service service = Client.getClient().create(Service.class);
        Call<List<Recipe>> callRecipes = service.getRecipes();
        callRecipes.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (!response.isSuccessful()) {
                    System.out.println(response.code());
                    return;
                }
                List<Recipe> recipes = response.body();
                mRV_Ingredients.setAdapter(new IngredientAdapter(getApplicationContext(), recipes.get(recipe.getmId() - 1).getmIngredients()));
//                mRV_Steps.setAdapter(new StepAdapter(getApplicationContext(), recipes.get(recipe.getmId() - 1).getmSteps()));

//                System.out.println("huehuehue" + recipes.get(recipe.getmId()).getmIngredients());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });


    }

}



