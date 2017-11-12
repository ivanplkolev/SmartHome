package kolevmobile.com.smarthome.main;

/**
 * Created by me on 05/11/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kolevmobile.com.smarthome.R;
import kolevmobile.com.smarthome.model.SensorModel;

/**
 * Created by Juned on 3/27/2017.
 */

public class SensorDisplayAdapter extends RecyclerView.Adapter<SensorDisplayAdapter.MyView> {

    private List<SensorModel> sensorModelList;

    public class MyView extends RecyclerView.ViewHolder {

        public TextView sensorNameTextView;
        public TextView sensorValueTextView;
        public TextView sensorUnitsTextView;

        public MyView(View view) {
            super(view);

            sensorNameTextView = view.findViewById(R.id.sensor_name);
            sensorValueTextView = view.findViewById(R.id.sensor_value);
            sensorUnitsTextView = view.findViewById(R.id.sensor_units);
        }
    }


    public SensorDisplayAdapter(List<SensorModel> sensorModelList) {
        this.sensorModelList = sensorModelList;
    }

    public SensorDisplayAdapter() {
        this.sensorModelList = new ArrayList<>();
    }

    public void setSensorModelList(List<SensorModel> sensorModelList) {
        this.sensorModelList = sensorModelList;
        notifyDataSetChanged();
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sensor_data_element, parent, false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {

        SensorModel sensorModel = sensorModelList.get(position);
        holder.sensorNameTextView.setText(sensorModel.getName());
        if(sensorModel.getActualValue() !=null) {
            holder.sensorValueTextView.setText(String.valueOf(sensorModel.getActualValue().getValue()));
        } else {
            holder.sensorValueTextView.setText("");
        }

        holder.sensorUnitsTextView.setText(sensorModel.getUnits());
    }

    @Override
    public int getItemCount() {
        return sensorModelList.size();
    }

}
