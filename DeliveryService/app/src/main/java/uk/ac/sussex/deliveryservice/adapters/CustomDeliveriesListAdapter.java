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
import uk.ac.sussex.deliveryservice.model.Delivery;
import uk.ac.sussex.deliveryservice.model.RouteViewModel;

public class CustomDeliveriesListAdapter extends ArrayAdapter<Delivery> implements View.OnClickListener {

    private ArrayList<Delivery> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView statusField;
        TextView itemSizeField;
        TextView itemWeightField;
        TextView deliverByDateField;
        TextView clientNameField;
        TextView clientAddressField;
    }

    public CustomDeliveriesListAdapter(ArrayList<Delivery> data, Context context) {
        super(context, R.layout.delivery_row_item, data);
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
        Delivery dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        CustomDeliveriesListAdapter.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new CustomDeliveriesListAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.delivery_row_item, parent, false);
            viewHolder.statusField = (TextView) convertView.findViewById(R.id.delivery_status);
            viewHolder.itemSizeField = (TextView) convertView.findViewById(R.id.item_size);
            viewHolder.itemWeightField = (TextView) convertView.findViewById(R.id.item_weight);
            viewHolder.deliverByDateField = (TextView) convertView.findViewById(R.id.deliver_by);
            viewHolder.clientNameField = (TextView) convertView.findViewById(R.id.client_name);
            viewHolder.clientAddressField = (TextView) convertView.findViewById(R.id.client_address);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomDeliveriesListAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        String clientNameString = dataModel.getClient().getFirstName() + " " + dataModel.getClient().getLastName();
        Address clientAddress = dataModel.getClient().getAddress();
        String clientAddressString = clientAddress.getLineOne() + "\n" + clientAddress.getLineTwo() + "\n" + clientAddress.getCity() + "\n" + clientAddress.getPostCode();
        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        String deliverByDateString = formatter.format(dataModel.getDeliverBy());

        viewHolder.statusField.setText(dataModel.getStatusString());
        viewHolder.itemSizeField.setText(dataModel.getItemSize());
        viewHolder.itemWeightField.setText("" + dataModel.getItemWeight());
        viewHolder.deliverByDateField.setText(deliverByDateString);
        viewHolder.clientNameField.setText(clientNameString);
        viewHolder.clientAddressField.setText(clientAddressString);
        return convertView;
    }
}
