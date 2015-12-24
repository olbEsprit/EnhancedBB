package kampus.enhancedbb;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<String> b_titleList;
    private ArrayList<String> b_bodyList;
    private ArrayList<String> b_authorList;
    private ArrayList<String> b_dateList;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView b_TitleTextView;
        public TextView b_BodyTextView;
        public TextView b_AuthorTextView;
        public TextView b_DateTextView;


        public ViewHolder(View v) {
            super(v);
            b_TitleTextView = (TextView) v.findViewById(R.id.b_title);
            b_BodyTextView = (TextView) v.findViewById(R.id.b_body);
            b_AuthorTextView = (TextView) v.findViewById(R.id.b_author);
            b_DateTextView = (TextView) v.findViewById(R.id.b_date);
        }
    }

    public RecyclerAdapter(ArrayList<String> titleList, ArrayList<String> bodyList, ArrayList<String> authorList, ArrayList<String> dateList) {
        b_bodyList = bodyList;
        b_titleList = titleList;
        b_authorList = authorList;
        b_dateList = dateList;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.b_TitleTextView.setText(b_titleList.get(position));
        holder.b_BodyTextView.setText(b_bodyList.get(position));
        holder.b_AuthorTextView.setText(b_authorList.get(position));
        holder.b_DateTextView.setText(b_dateList.get(position));

    }

    @Override
    public int getItemCount() {
        return b_titleList.size();
    }

    public void swap(){
        b_titleList.clear();
        b_bodyList.clear();
        notifyDataSetChanged();
    }

}