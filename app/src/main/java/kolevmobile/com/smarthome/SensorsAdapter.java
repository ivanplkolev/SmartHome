package kolevmobile.com.smarthome;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kolevmobile.com.smarthome.model.SensorModel;

public class SensorsAdapter extends RecyclerView.Adapter<SensorsAdapter.MyViewHolder> {

    private List<SensorModel> sensorModelList;

    private ItemButtonObserver onItemViewClickListener;

    public void setOnItemViewClickListener(ItemButtonObserver onItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextField;
        private TextView descriptionTextField;
        private TextView keyTextField;
        private TextView unitsTextField;
        View editButton;
        View deleteButton;
        View buttonsLayout;

        public MyViewHolder(View view) {
            super(view);
            nameTextField = (TextView) view.findViewById(R.id.sensor_name_text_view);
            descriptionTextField = (TextView) view.findViewById(R.id.sensor_description_text_view);
            keyTextField = (TextView) view.findViewById(R.id.sensor_key_text_view);
            unitsTextField = (TextView) view.findViewById(R.id.sensor_units_text_view);

            View sensorRowBaseLayout = itemView.findViewById(R.id.sensor_row_base_layout);
            buttonsLayout = itemView.findViewById(R.id.sensor_row_buttons_layout);
            sensorRowBaseLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View var1, MotionEvent var2) {
                    boolean vissible = buttonsLayout.getVisibility() == View.VISIBLE;
                    buttonsLayout.setVisibility(vissible ? View.INVISIBLE : View.VISIBLE);
                    return false;
                }
            });
            this.editButton = itemView.findViewById(R.id.editButton);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemViewClickListener.onClick(editButton, getPosition());
                }
            });
            this.deleteButton = itemView.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemViewClickListener.onClick(deleteButton, getPosition());
                }
            });
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
