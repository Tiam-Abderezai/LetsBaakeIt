package com.example.letsbakeit.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.letsbakeit.R;
import com.example.letsbakeit.RecipeActivity;
import com.example.letsbakeit.adapters.IngredientAdapter;
import com.example.letsbakeit.adapters.StepAdapter;
import com.example.letsbakeit.databinding.FragmentStepBinding;
import com.example.letsbakeit.model.Recipe;
import com.example.letsbakeit.model.Step;
import com.example.letsbakeit.utils.Client;
import com.example.letsbakeit.utils.Service;
import com.google.gson.internal.bind.util.ISO8601Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StepFragment extends Fragment {

    private static final String INTENT_RECIPE_KEY = "recipeObject";

    private FragmentStepBinding binding;
    private StepDetailFragment stepDetailFragment = new StepDetailFragment();

    public StepFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_step, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        List<Step> mSteps = new ArrayList<>();
        final Recipe recipe = getActivity().getIntent().getExtras().getParcelable(INTENT_RECIPE_KEY);
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
//                mRV_Ingredients.setAdapter(new IngredientAdapter(getApplicationContext(), recipes.get(recipe.getmId() - 1).getmIngredients()));
//                binding.rvIngredients.setAdapter(new IngredientAdapter(getApplicationContext(), recipes.get(recipe.getmId() - 1).getmIngredients()));
//                binding.rvSteps.setAdapter(new StepAdapter(getApplicationContext(), recipes.get(recipe.getmId() - 1).getmSteps()));
//                mRV_Steps.setAdapter(new StepAdapter(getApplicationContext(), recipes.get(recipe.getmId() - 1).getmSteps()));
//                System.out.println("funkie" + recipes.get(recipe.getmId() - 1).getmSteps());

                for (int i = 0; i < recipes.get(recipe.getmId() - 1).getmSteps().size(); i++) {
                    mSteps.add(new Step(i,
                            recipes.get(recipe.getmId() - 1).getmSteps().get(i).getShortDescription()
                            ,
                            recipes.get(recipe.getmId() - 1).getmSteps().get(i).getDescription(),
                            recipes.get(recipe.getmId() - 1).getmSteps().get(i).getVideoUrl(),
                            ""));
                }
//                mSteps.add(new Step(0, "Poop", "", "", ""));
//                mSteps.add(new Step(1, "Fart", "", "", ""));
//                mSteps.add(new Step(2, "Piss", "", "", ""));
//                mSteps.add(new Step(3, "Piss", "", "", ""));

                StepAdapter adapter = new StepAdapter(mSteps);
                binding.rvStepsFrag.setAdapter(adapter);
                adapter.setListener((v, position) -> {
                    viewModel.setSelected(adapter.getStepAt(position));
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.container, stepDetailFragment)
                            .addToBackStack(null)
                            .commit();
                });

            }


            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
//
//
    }
}
