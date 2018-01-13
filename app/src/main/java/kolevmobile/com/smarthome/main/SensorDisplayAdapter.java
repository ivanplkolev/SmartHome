package kolevmobile.com.smarthome.main;

/**
 * Created by me on 05/11/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kolevmobile.com.smarthome.R;
import kolevmobile.com.smarthome.model.SensorModel;


public class SensorDisplayAdapter extends RecyclerView.Adapter<SensorDisplayAdapter.MyView> {

    private List<SensorModel> sensorModelList;
    private Context context;

    class MyView extends RecyclerView.ViewHolder {
        @BindView(R.id.sensor_name)
        TextView sensorNameTextView;
        @BindView(R.id.sensor_value)
        TextView sensorValueTextView;
        @BindView(R.id.sensor_units)
        TextView sensorUnitsTextView;

        MyView(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    SensorDisplayAdapter(Context context) {
        this.context = context;
        this.sensorModelList = new ArrayList<>();
    }

    void setSensorModelList(List<SensorModel> sensorModelList) {
        this.sensorModelList = sensorModelList;
        notifyDataSetChanged();
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sensor_display_element, parent, false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {

        SensorModel sensorModel = sensorModelList.get(position);
        holder.sensorNameTextView.setText(sensorModel.getName());
        if (sensorModel.getActualValue() != null) {
            holder.sensorValueTextView.setText(String.format("%.1f", sensorModel.getActualValue().getValue()));
        } else {
            holder.sensorValueTextView.setText(context.getString(R.string.not_info));
        }

        holder.sensorUnitsTextView.setText(sensorModel.getUnits());
    }

    @Override
    public int getItemCount() {
        return sensorModelList.size();
    }

}
