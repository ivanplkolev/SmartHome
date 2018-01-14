package kolevmobile.com.smarthome.details;

/**
 * Created by me on 05/11/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kolevmobile.com.smarthome.R;
import kolevmobile.com.smarthome.model.SensorModel;


public class DetailsChartsAdapter extends RecyclerView.Adapter<DetailsChartsAdapter.MyView> {

    private List<SensorModel> sensorModelList;
    private Context context;

    class MyView extends RecyclerView.ViewHolder {

        TextView sensorNameTextView;
        DetailsChartView detailsChartView;

        MyView(View view) {
            super(view);
            sensorNameTextView = view.findViewById(R.id.sensor_name_details_element);
            detailsChartView = view.findViewById(R.id.details_chart);
        }
    }

    DetailsChartsAdapter(List<SensorModel> sensorModelList, Context context) {
        this.context = context;
        this.sensorModelList = sensorModelList;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_details, parent, false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {
        SensorModel sensorModel = sensorModelList.get(position);
        holder.sensorNameTextView.setText(sensorModel.getName());
        holder.detailsChartView.setValues(sensorModel.getSensroValueList());
    }

    @Override
    public int getItemCount() {
        return sensorModelList.size();
    }

}
