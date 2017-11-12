package kolevmobile.com.smarthome.main;

/**
 * Created by me on 05/11/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import kolevmobile.com.smarthome.ItemButtonObserver;
import kolevmobile.com.smarthome.R;
import kolevmobile.com.smarthome.model.RelayModel;
import kolevmobile.com.smarthome.model.RelayStatus;

/**
 * Created by Juned on 3/27/2017.
 */

public class RelayDisplayAdapter extends RecyclerView.Adapter<RelayDisplayAdapter.MyView> {

    private List<RelayModel> relayModelList;
    public int position;

    private ItemButtonObserver onItemViewClickListener;


    public void setOnItemViewClickListener(ItemButtonObserver onItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener;
    }

    public class MyView extends RecyclerView.ViewHolder {

        public TextView relayTogglerInfo;
        public TextView relayName;
        public SwitchCompat relayTogglerButton;


        public MyView(View view) {
            super(view);
            relayTogglerButton = view.findViewById(R.id.relay_toggler);
            relayTogglerInfo = view.findViewById(R.id.relay_toggler_info);
            relayName = view.findViewById(R.id.relay_toggler_name);
            relayTogglerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemViewClickListener.onClick(relayTogglerButton, position, getPosition());
                }
            });
        }
    }


    public RelayDisplayAdapter(List<RelayModel> relayModelList, int position) {
        this.relayModelList = relayModelList;
        this.position = position;
    }

    public RelayDisplayAdapter(int position) {
        this.relayModelList = new ArrayList<>();
        this.position = position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setRelayModelList(List<RelayModel> relayModelList) {
        this.relayModelList = relayModelList;
        notifyDataSetChanged();
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.relay_toggler_element, parent, false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {
        RelayModel relayModel = relayModelList.get(position);
        holder.relayName.setText(relayModel.getName());
        RelayStatus relayStatus = relayModel.getActualStatus();

        if (relayStatus != null) {
            holder.relayTogglerButton.setChecked(relayStatus.getValue() == 1);
            holder.relayTogglerButton.setClickable(relayStatus.getConfirmed());
            holder.relayTogglerInfo.setVisibility(View.INVISIBLE);
            holder.relayTogglerButton.setVisibility(View.VISIBLE);
        } else {
            holder.relayTogglerInfo.setText("Not Info Yet");
            holder.relayTogglerInfo.setVisibility(View.VISIBLE);
            holder.relayTogglerButton.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return relayModelList.size();
    }

}
