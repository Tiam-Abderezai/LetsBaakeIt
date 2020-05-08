package com.example.letsbakeit.adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.letsbakeit.R;
import com.example.letsbakeit.RecipeActivity;
import com.example.letsbakeit.model.Ingredient;
import com.example.letsbakeit.model.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private static final String INTENT_RECIPE_KEY = "recipeObject";
    private List<Recipe> mRecipes;
    private List<Ingredient> mIngredients;
    Context mContext;

    public RecipeAdapter(Context context, List<Recipe> recipes) {
        mContext = context;
        mRecipes = recipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.tv_Name.setText(mRecipes.get(position).getmName());
        holder.mRecipeCardView.setTag(R.id.item_recipe, position);
        holder.mRecipeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RecipeActivity.class);
                int position = (int) view.getTag(R.id.item_recipe);
                intent.putExtra(INTENT_RECIPE_KEY, mRecipes.get(position));
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }


    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        final CardView mRecipeCardView;
        TextView tv_Name;
        Context mContext;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            mRecipeCardView = (CardView) itemView.findViewById(R.id.card_view_recipe);
            tv_Name = (TextView) itemView.findViewById(R.id.tv_recipe_name);
            mContext = itemView.getContext();
        }
    }
}
