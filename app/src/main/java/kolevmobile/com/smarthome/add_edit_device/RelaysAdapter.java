package kolevmobile.com.smarthome.add_edit_device;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kolevmobile.com.smarthome.ItemButtonObserver;
import kolevmobile.com.smarthome.R;
import kolevmobile.com.smarthome.model.RelayModel;

public class RelaysAdapter extends RecyclerView.Adapter<RelaysAdapter.MyViewHolder> {

    private List<RelayModel> relayModelList;

    private ItemButtonObserver onItemViewClickListener;

    public void setOnItemViewClickListener(ItemButtonObserver onItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextField;
        private TextView descriptionTextField;
        private TextView keyTextField;
        View editButton;
        View deleteButton;
        View buttonsLayout;

        public MyViewHolder(View view) {
            super(view);
            nameTextField = view.findViewById(R.id.relay_name_text_view);
            descriptionTextField = view.findViewById(R.id.relay_description_text_view);
            keyTextField = (TextView) view.findViewById(R.id.relay_key_text_view);

            View relayRowBaseLayout = itemView.findViewById(R.id.relay_row_base_layout);
            buttonsLayout = itemView.findViewById(R.id.relay_row_buttons_layout);
            relayRowBaseLayout.setOnTouchListener(new View.OnTouchListener() {
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
                    onItemViewClickListener.onClick(editButton, getPosition(), 0);
                }
            });
            this.deleteButton = itemView.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemViewClickListener.onClick(deleteButton, getPosition(), 0);
                }
            });

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
