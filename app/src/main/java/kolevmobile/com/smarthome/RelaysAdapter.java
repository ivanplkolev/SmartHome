package kolevmobile.com.smarthome;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kolevmobile.com.smarthome.model.RelayModel;
import kolevmobile.com.smarthome.model.SensorModel;

public class RelaysAdapter extends RecyclerView.Adapter<RelaysAdapter.MyViewHolder> {

    private List<RelayModel> relayModelList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextField;
        private TextView descriptionTextField;
        private TextView keyTextField;

        public MyViewHolder(View view) {
            super(view);
            nameTextField = view.findViewById(R.id.sensor_name_text_view);
            descriptionTextField = view.findViewById(R.id.sensor_description_text_view);
            keyTextField = (TextView) view.findViewById(R.id.sensor_key_text_view);
        }
    }


    public RelaysAdapter(List<RelayModel> relayModelList) {
        this.relayModelList = relayModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.relay_row_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RelayModel relayModel = relayModelList.get(position);
        holder.nameTextField.setText(relayModel.getName());
        holder.descriptionTextField.setText(relayModel.getDescription());
        holder.keyTextField.setText(relayModel.getKey());
    }

    @Override
    public int getItemCount() {
        return relayModelList.size();
    }
}
