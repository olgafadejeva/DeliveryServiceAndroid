package uk.ac.sussex.deliveryservice.adapters;

import android.content.Context;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import uk.ac.sussex.deliveryservice.R;
import uk.ac.sussex.deliveryservice.model.RouteViewModel;


public class CustomRouteListAdapter extends ArrayAdapter<RouteViewModel> implements View.OnClickListener {

    private ArrayList<RouteViewModel> dataSet;
    Context mContext;
    private int lastPosition = -1;

    private static class ViewHolder {
        TextView status;
        TextView numberOfDeliveries;
        TextView deliveryDate;
        ImageView statusIcon;
    }

    public CustomRouteListAdapter(ArrayList<RouteViewModel> data, Context context) {
        super(context, R.layout.route_row_item, data);
        this.dataSet = data;
        this.mContext = context;

    }


    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        Object object = getItem(position);
        RouteViewModel dataModel = (RouteViewModel) object;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        RouteViewModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.route_row_item, parent, false);
            viewHolder.status = (TextView) convertView.findViewById(R.id.status);
            viewHolder.numberOfDeliveries = (TextView) convertView.findViewById(R.id.deliveries_number);
            viewHolder.deliveryDate = (TextView) convertView.findViewById(R.id.delivery_date);
            viewHolder.statusIcon = (ImageView) convertView.findViewById(R.id.status_icon);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        switch(dataModel.getStatus()) {
            case ("In progress") :
                viewHolder.statusIcon.setColorFilter(Color.parseColor("#f0ad4e"));
                break;
            case ("Completed") :
                viewHolder.statusIcon.setColorFilter(Color.parseColor("#5cb85c"));
                break;
            case ("Pending") :
                viewHolder.statusIcon.setColorFilter(Color.parseColor("#d9534f"));
                break;
            default:
                viewHolder.statusIcon.setColorFilter(Color.parseColor("#f0ad4e"));
                break;
        }

        viewHolder.status.setText(dataModel.getStatus());
        viewHolder.deliveryDate.setText(dataModel.getDeliveryDate());
        viewHolder.numberOfDeliveries.setText(""+ dataModel.getNumberOfDeliveries());
        return convertView;
    }
}

