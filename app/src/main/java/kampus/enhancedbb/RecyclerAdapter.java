package kampus.enhancedbb;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Павел on 13.12.2015.




public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.BBViewHolder> {

    private List<IdleBB> contactList;

    public RecyclerAdapter(List<IdleBB> contactList) {
        this.contactList = contactList;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(BBViewHolder contactViewHolder, int i) {
        IdleBB ci = contactList.get(i);
        contactViewHolder.b_title.setText(ci.title);
        contactViewHolder.b_body.setText(ci.body);

    }

    @Override
    public BBViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.recycler_item, viewGroup, false);

        return new BBViewHolder(itemView);
    }

    public static class BBViewHolder extends RecyclerView.ViewHolder {
        protected TextView b_title;
        protected TextView b_body;

        public  BBViewHolder(View v)
        {
            super(v);
            b_title = (TextView) v.findViewById(R.id.b_title);
            b_body = (TextView) v.findViewById(R.id.b_body);
        }
    }

}
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<String> b_titleList;
    private ArrayList<String> b_bodyList;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView b_TitleTextView;
        public TextView b_BodyTextView;

        public ViewHolder(View v) {
            super(v);
            b_TitleTextView = (TextView) v.findViewById(R.id.b_title);
            b_BodyTextView = (TextView) v.findViewById(R.id.b_body);
        }
    }

    public RecyclerAdapter(ArrayList<String> titleList, ArrayList<String> bodyList) {
        b_bodyList = bodyList;
        b_titleList = titleList;
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

    }

    @Override
    public int getItemCount() {
        return b_titleList.size();




    }

}