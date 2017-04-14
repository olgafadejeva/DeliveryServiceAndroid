package uk.ac.sussex.deliveryservice.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import uk.ac.sussex.deliveryservice.R;
import uk.ac.sussex.deliveryservice.model.Vehicle;

/*
Represents vehicles in a list
 */
public class VehiclesListAdapter extends ArrayAdapter<Vehicle> implements View.OnClickListener  {

    private Context mContext;
    private int lastPosition = -1;

    public VehiclesListAdapter(ArrayList<Vehicle> data, Context context) {
        super(context, R.layout.vehicle_row_item, data);
        this.mContext = context;
    }

    private static class ViewHolder {
        TextView txtName;
        TextView regNumber;
        TextView maxLoad;
        TextView dimensions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Vehicle dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        VehiclesListAdapter.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new VehiclesListAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.vehicle_row_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.regNumber = (TextView) convertView.findViewById(R.id.reg_num);
            viewHolder.dimensions = (TextView) convertView.findViewById(R.id.dimensions);
            viewHolder.maxLoad = (TextView) convertView.findViewById(R.id.max_load);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (VehiclesListAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;
        String dimensionsString = dataModel.getHeight() + "x" + dataModel.getLength() + "x" + dataModel.getWidth();

        viewHolder.txtName.setText(dataModel.getVehicleName());
        viewHolder.regNumber.setText(dataModel.getRegistrationNumber());
        viewHolder.maxLoad.setText(dataModel.getMaxLoad() + "kg");
        viewHolder.dimensions.setText(dimensionsString);
        return convertView;
    }

    @Override
    public void onClick(View view) {

    }
}
