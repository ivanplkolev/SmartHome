package kolevmobile.com.smarthome.add_edit_device;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kolevmobile.com.smarthome.ItemButtonObserver;
import kolevmobile.com.smarthome.R;
import kolevmobile.com.smarthome.model.SensorModel;

public class SensorsAdapter extends RecyclerView.Adapter<SensorsAdapter.MyViewHolder> {

    private List<SensorModel> sensorModelList;

    private ItemButtonObserver onItemViewClickListener;

    public void setOnItemViewClickListener(ItemButtonObserver onItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sensor_name_text_view)
        TextView nameTextField;
        @BindView(R.id.sensor_description_text_view)
        TextView descriptionTextField;
        @BindView(R.id.sensor_key_text_view)
        TextView keyTextField;
        @BindView(R.id.sensor_units_text_view)
        TextView unitsTextField;
        View editButton;
        View deleteButton;
        View buttonsLayout;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            View sensorRowBaseLayout = itemView.findViewById(R.id.sensor_row_base_layout);
            buttonsLayout = itemView.findViewById(R.id.sensor_row_buttons_layout);
            sensorRowBaseLayout.setOnTouchListener((var1, var2) -> {
                boolean vissible = buttonsLayout.getVisibility() == View.VISIBLE;
                buttonsLayout.setVisibility(vissible ? View.INVISIBLE : View.VISIBLE);
                return false;
            });
            editButton = itemView.findViewById(R.id.editButton);
            editButton.setOnClickListener(view12 -> onItemViewClickListener.onClick(editButton, getPosition(), 0));
            deleteButton = itemView.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(view1 -> onItemViewClickListener.onClick(deleteButton, getPosition(), 0));
        }
    }

    public SensorsAdapter(List<SensorModel> sensorModelList) {
        this.sensorModelList = sensorModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sensor_row_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SensorModel sensorModel = sensorModelList.get(position);
        holder.nameTextField.setText(sensorModel.getName());
        holder.descriptionTextField.setText(sensorModel.getDescription());
        holder.keyTextField.setText(sensorModel.getKey());
        holder.unitsTextField.setText(sensorModel.getUnits());
    }

    @Override
    public int getItemCount() {
        return sensorModelList.size();
    }
}
