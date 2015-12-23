package kampus.enhancedbb;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Павел on 22.12.2015.
 */
public class SpinProfileAdapter extends ArrayAdapter<Profile> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private List<Profile> values;

    public SpinProfileAdapter(Context context, int textViewResourceId,
                              List<Profile> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }



    public SpinProfileAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
        this.values = values;
    }

    public int getCount(){
        return values.size();
    }

    public Profile getItem(int position){
        return values.get(position);
    }

    public long getItemId(int position){
        return values.get(position).id;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = new TextView(context);
        label.setTextColor(Color.WHITE);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(values.get(position).name);

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.WHITE);
        label.setText(values.get(position).name);

        return label;
    }
}
