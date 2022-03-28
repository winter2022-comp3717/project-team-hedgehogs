package com.bcit.hedgehog_honeymoon;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class PowerUpRecycler extends RecyclerView.Adapter<PowerUpRecycler.ViewHolder> {

    private PowerUps[] localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * This template comes with a TextView
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameValue;
        private final TextView descriptionValue;
        private final TextView costValue;
        private final TextView numberPurchased;
        private final ImageView img;

        public ViewHolder(View view) {
            super(view);

            nameValue = view.findViewById(R.id.textView_power_up_name);
            descriptionValue = view.findViewById(R.id.textView_recycler_desc);
            costValue = view.findViewById(R.id.textView_power_up_cost);
            img = view.findViewById(R.id.imageView_power_ups);
            numberPurchased = view.findViewById(R.id.textView_recycler_quantity);
        }

        public TextView getNameValue() {
            return nameValue;
        }
        public TextView getDescriptionValue() {
            return descriptionValue;
        }
        public TextView getCostValue() {
            return costValue;
        }
        public ImageView getImg() {
            return img;
        }
        public TextView getNumberPurchased() {return numberPurchased;}
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     *                by RecyclerView.
     */
    public PowerUpRecycler(PowerUps [] dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_power_ups, viewGroup, false); //error here should be expected, this is a template

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        PowerUps item = localDataSet[position];
        viewHolder.getNameValue().setText(item.getName());
        viewHolder.getDescriptionValue().setText(item.getDescription());
        viewHolder.getCostValue().setText(Integer.toString(item.getCost()));
        viewHolder.getImg().setImageResource(item.getImageId());

        if(position == 0){
            viewHolder.getNumberPurchased()
                    .setText(Integer.toString(GameActivity.numberOfMealWorms));
        } else if (position == 1){
            viewHolder.getNumberPurchased()
                    .setText(Integer.toString(GameActivity.numberOfSafaris));
        } else if (position == 2){
            viewHolder.getNumberPurchased()
                    .setText(Integer.toString(GameActivity.numberOfLadyHogs));
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}