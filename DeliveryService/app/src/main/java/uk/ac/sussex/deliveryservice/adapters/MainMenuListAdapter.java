package uk.ac.sussex.deliveryservice.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import uk.ac.sussex.deliveryservice.R;

/**
 * Created by Olga on 08/02/2017.
 */
public class MainMenuListAdapter extends ArrayAdapter<String> {

    private ArrayList<String> dataSet;
    private Context mContext;

    public MainMenuListAdapter(ArrayList<String> data, Context context) {
        super(context, R.layout.main_menu_item, data);
        this.dataSet = data;
        this.mContext = context;
    }

    private static class ViewHolder {
        TextView menuItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        MainMenuListAdapter.ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {

            viewHolder = new MainMenuListAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.main_menu_item, parent, false);
            viewHolder.menuItem = (TextView) convertView.findViewById(R.id.menu_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MainMenuListAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.menuItem.setText(dataModel);
        return convertView;
    }
}
