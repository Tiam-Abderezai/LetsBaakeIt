

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
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.letsbakeit.R;
import com.example.letsbakeit.RecipeActivity;
import com.example.letsbakeit.databinding.ItemStepBinding;
import com.example.letsbakeit.model.Step;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    private List<Step> mSteps;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    public StepAdapter(List<Step> list) {
        this.mSteps = list;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public Step getStepAt(int position) {
        return mSteps.get(position);
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemStepBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_step, parent, false);
        return new StepViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.binding.tvStepNumber.setText("Step " + Integer.toString(mSteps.get(position).getStepId()));
        holder.binding.tvStepShDesc.setText(mSteps.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }


    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemStepBinding binding;

        public StepViewHolder(@NonNull ItemStepBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

}

