package kolevmobile.com.smarthome.details;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kolevmobile.com.smarthome.R;


public class DetailsChartsAdapter extends RecyclerView.Adapter<DetailsChartsAdapter.MyView> {

    private List<DetailModel> detailModelList;
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

    DetailsChartsAdapter(Context context) {
        this.context = context;
    }

    void setDetailModelList(List<DetailModel> detailModelList) {
        this.detailModelList = detailModelList;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_details, parent, false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {
        DetailModel detailModel = detailModelList.get(position);
        holder.sensorNameTextView.setText(detailModel.getName());
        holder.detailsChartView.update(detailModel);
    }

    @Override
    public int getItemCount() {
        return detailModelList != null ? detailModelList.size() : 0;
    }

}
