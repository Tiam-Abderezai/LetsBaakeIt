
package com.example.letsbakeit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.letsbakeit.R;
import com.example.letsbakeit.databinding.ItemIngredientBinding;
import com.example.letsbakeit.model.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private static final String INTENT_INGREDIENT_KEY = "ingredientObject";
    private List<Ingredient> mIngredients;
    Context mContext;

    public IngredientAdapter(Context context, List<Ingredient> ingredients) {
        mContext = context;
        mIngredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // DataBinding used for Ingredients
        ItemIngredientBinding itemIngredientBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_ingredient, parent, false);
        IngredientViewHolder ingredientViewHolder = new IngredientViewHolder(itemIngredientBinding);
        return ingredientViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = mIngredients.get(position);
        holder.itemIngredientBinding.setIngredient(ingredient);
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }


    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        ItemIngredientBinding itemIngredientBinding;

        public IngredientViewHolder(@NonNull ItemIngredientBinding itemView) {
            super(itemView.getRoot());
            itemIngredientBinding = itemView;
        }
    }
}
