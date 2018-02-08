package com.example.wmramazan.getirhackathontask3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by wmramazan on 8.02.2018.
 */

public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.RecordListViewHolder> {

    private ArrayList<RecordItem> records;
    private Context context;

    public RecordListAdapter(Context context, ArrayList<RecordItem> posts) {
        this.context = context;
        this.records = posts;
    }

    @Override
    public RecordListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false);
        return new RecordListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecordListViewHolder holder, final int position) {
        final RecordItem item = records.get(position);

        holder.title.setText(String.valueOf(item.getTotalCount()));
        holder.text.setText(item.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return (null != records ? records.size() : 0);
    }

    public static class RecordListViewHolder extends RecyclerView.ViewHolder {

        protected TextView title, text;

        public RecordListViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.record_title);
            this.text = (TextView) itemView.findViewById(R.id.record_text);
        }
    }
    
}
