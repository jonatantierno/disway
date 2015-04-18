package com.agoravitae.agoravitae;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonatan on 17/04/15.
 */
public class TTAdapter extends RecyclerView.Adapter<TTAdapter.ViewHolder>{

    List<String> list;
    RouteActivity activity;

    public TTAdapter(List<String> list, RouteActivity mainActivity) {
        this.list = list;
        this.activity = mainActivity;
    }

    public void addItems(String transfer) {
        list.add(transfer);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        public TextView mTextView;
        private final RouteActivity itemSelectedListener;

        public ViewHolder(ViewGroup v, RouteActivity itemSelectedListener) {
            super(v);
            this.itemSelectedListener = itemSelectedListener;
            this.mTextView = (TextView)v.findViewById(R.id.boardName);
        }

        @Override
        public void onClick(View v) {
            this.itemSelectedListener.onItemSelected(getPosition());
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TTAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.board_list_view, parent, false);
        // set the view's size, margins, paddings and layout parameters


        ViewHolder vh = new ViewHolder((ViewGroup) v, activity);
        v.setOnClickListener(vh);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(list.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}
