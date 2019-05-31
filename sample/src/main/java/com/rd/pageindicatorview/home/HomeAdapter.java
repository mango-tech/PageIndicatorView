package com.rd.pageindicatorview.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rd.pageindicatorview.sample.R;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<View> colors;

    public void setData(List<View> colors) {
        this.colors = colors;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        holder.page.addView(colors.get(position));
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        FrameLayout page;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            page = itemView.findViewById(R.id.layout_page);
        }
    }
}
