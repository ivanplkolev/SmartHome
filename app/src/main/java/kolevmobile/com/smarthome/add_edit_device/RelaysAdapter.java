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
import kolevmobile.com.smarthome.custom_components.ExpandableLayout;
import kolevmobile.com.smarthome.model.RelayModel;

public class RelaysAdapter extends RecyclerView.Adapter<RelaysAdapter.MyViewHolder> {

    private List<RelayModel> relayModelList;
    private ItemButtonObserver onItemViewClickListener;

    public void setOnItemViewClickListener(ItemButtonObserver onItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.relay_name_text_view)
        TextView nameTextField;
        @BindView(R.id.relay_description_text_view)
        TextView descriptionTextField;
        @BindView(R.id.relay_key_text_view)
        TextView keyTextField;
        @BindView(R.id.expandable_layout)
        ExpandableLayout expandableLayout;
        @BindView(R.id.editButton)
        View editButton;
        @BindView(R.id.deleteButton)
        View deleteButton;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            editButton.setOnClickListener(view12 -> onItemViewClickListener.onClick(editButton, getPosition(), 0));
            deleteButton.setOnClickListener(view1 -> onItemViewClickListener.onClick(deleteButton, getPosition(), 0));
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
        if (holder.expandableLayout.isExpanded()) {
            holder.expandableLayout.close();
        }
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
