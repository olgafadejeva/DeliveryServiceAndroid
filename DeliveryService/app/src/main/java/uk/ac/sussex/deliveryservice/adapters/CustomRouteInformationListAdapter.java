package uk.ac.sussex.deliveryservice.adapters;


import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import uk.ac.sussex.deliveryservice.R;
import uk.ac.sussex.deliveryservice.model.Address;
import uk.ac.sussex.deliveryservice.model.PickUpAddress;
import uk.ac.sussex.deliveryservice.model.RouteViewModel;

public class CustomRouteInformationListAdapter extends ArrayAdapter<RouteViewModel> implements View.OnClickListener{

    private ArrayList<RouteViewModel> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView statusField;
        TextView deliverByDateField;
        TextView deliveryDateField;
        TextView overallDistanceField;
        TextView overallTimeRequiredField;
        TextView depotAddressField;
        TextView deliveriesNumberField;
        TextView vehicleField;
    }

    public CustomRouteInformationListAdapter(ArrayList<RouteViewModel> data, Context context) {
        super(context, R.layout.route_information_row_item, data);
        this.dataSet = data;
        this.mContext = context;

    }


    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        Object object = getItem(position);
        RouteViewModel dataModel = (RouteViewModel) object;

        switch (v.getId()) {
            case R.id.item_info:
                Snackbar.make(v, "Delivery date " + dataModel.getDeliveryDate(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();

                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        RouteViewModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        CustomRouteInformationListAdapter.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new CustomRouteInformationListAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.route_information_row_item, parent, false);
            viewHolder.statusField = (TextView) convertView.findViewById(R.id.route_status);
            viewHolder.deliverByDateField = (TextView) convertView.findViewById(R.id.deliver_by);
            viewHolder.deliveryDateField = (TextView) convertView.findViewById(R.id.delivery_date);
            viewHolder.overallDistanceField = (TextView) convertView.findViewById(R.id.overall_distance);
            viewHolder.overallTimeRequiredField = (TextView) convertView.findViewById(R.id.overall_time);
            viewHolder.depotAddressField = (TextView) convertView.findViewById(R.id.depot_address);
            viewHolder.deliveriesNumberField = (TextView) convertView.findViewById(R.id.deliveries_number);
            viewHolder.vehicleField = (TextView) convertView.findViewById(R.id.vehicle);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomRouteInformationListAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        PickUpAddress address = dataModel.getPickUpAddress();
        String lineTwoOfAddress = address.getLineTwo() == null ? "\n" : "\n" + address.getLineTwo() + "\n";
        String pickUpAddressString = address.getLineOne() + lineTwoOfAddress + address.getCity() + "\n" + address.getPostCode();
        String vehicleString = dataModel.getVehicle().getVehicleName() + ", " + dataModel.getVehicle().getRegistrationNumber();

        viewHolder.statusField.setText(dataModel.getStatus());
        viewHolder.deliverByDateField.setText(dataModel.getDeliverByDate());
        viewHolder.deliveryDateField.setText(dataModel.getDeliveryDate());
        viewHolder.overallDistanceField.setText("" + dataModel.getOverallDistance());
        viewHolder.overallTimeRequiredField.setText("" + dataModel.getOverallTime());
        viewHolder.depotAddressField.setText(pickUpAddressString);
        viewHolder.deliveriesNumberField.setText("" + dataModel.getDeliveries().size());
        viewHolder.vehicleField.setText(vehicleString);

        return convertView;
    }
}
