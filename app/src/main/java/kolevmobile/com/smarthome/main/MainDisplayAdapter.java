package kolevmobile.com.smarthome.main;

/**
 * Created by x on 21.10.2017 г..
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;

import kolevmobile.com.smarthome.ItemButtonObserver;
import kolevmobile.com.smarthome.R;
import kolevmobile.com.smarthome.custom_components.ExpandableLayout;
import kolevmobile.com.smarthome.model.Device;

public class MainDisplayAdapter extends RecyclerView.Adapter<MainDisplayAdapter.DeviceViewHolder> {

    private List<Device> activeDevices;
    private Context context;
    private ItemButtonObserver onItemViewClickListener;
    private HashSet<Integer> expandedPositionSet;


    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd.MM");

    void setOnItemViewClickListener(ItemButtonObserver onItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener;
    }

    MainDisplayAdapter(List<Device> devices, Context context) {
        this.activeDevices = devices;
        this.context = context;
        expandedPositionSet = new HashSet<>();
    }


    class DeviceViewHolder extends RecyclerView.ViewHolder {
        private TextView deviceNameTextView;
        private TextView deviceInfoTextView;
        private View refreshButton;
        private View detailsButton;
        private View editButton;
        private View deleteButton;
        private ExpandableLayout expandableLayout;
        private AVLoadingIndicatorView avi;
        private RecyclerView sensorsListView;
        private RecyclerView relaysListView;

        private SensorDisplayAdapter sensorDisplayAdapter;
        private RelayDisplayAdapter relayDisplayAdapter;

        void startAnim() {
            avi.smoothToShow();
        }

        void stopAnim() {
            avi.smoothToHide();
        }

        private DeviceViewHolder(View itemView) {
            super(itemView);

            expandableLayout =  itemView.findViewById(R.id.expandable_layout);

            this.deviceNameTextView = itemView.findViewById(R.id.deviceNameTextView);
            this.deviceInfoTextView = itemView.findViewById(R.id.deviceInfoTextView);
            this.refreshButton = itemView.findViewById(R.id.refreshButton);
            refreshButton.setOnClickListener(view -> {
                onItemViewClickListener.onClick(refreshButton, getPosition(), 0);
            });
            this.detailsButton = itemView.findViewById(R.id.detailsButton);
            detailsButton.setOnClickListener(view -> onItemViewClickListener.onClick(detailsButton, getPosition(), 0));
            this.editButton = itemView.findViewById(R.id.editButton);
            editButton.setOnClickListener(view -> onItemViewClickListener.onClick(editButton, getPosition(), 0));
            this.deleteButton = itemView.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(view -> onItemViewClickListener.onClick(deleteButton, getPosition(), 0));
            avi = itemView.findViewById(R.id.avi);

            sensorsListView = itemView.findViewById(R.id.sensors_list_view);
            relaysListView = itemView.findViewById(R.id.relays_list_view);
            RecyclerView.LayoutManager recyclerViewLayoutManagerSensor = new LinearLayoutManager(context);
            RecyclerView.LayoutManager recyclerViewLayoutManagerRelay = new LinearLayoutManager(context);
            sensorsListView.setLayoutManager(recyclerViewLayoutManagerSensor);
            relaysListView.setLayoutManager(recyclerViewLayoutManagerRelay);
            sensorDisplayAdapter = new SensorDisplayAdapter(context);
            relayDisplayAdapter = new RelayDisplayAdapter(context);
            LinearLayoutManager HorizontalLayoutSensor = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            LinearLayoutManager HorizontalLayoutRelay = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            sensorsListView.setLayoutManager(HorizontalLayoutSensor);
            relaysListView.setLayoutManager(HorizontalLayoutRelay);

            sensorsListView.setAdapter(sensorDisplayAdapter);
            relaysListView.setAdapter(relayDisplayAdapter);

            relayDisplayAdapter.setOnItemViewClickListener(onItemViewClickListener);
        }

    }

    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_adapter_element, parent, false);

        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DeviceViewHolder holder, final int listPosition) {
        if (holder.expandableLayout.isExpanded()) {
            holder.expandableLayout.close();
        }
        Device device = activeDevices.get(listPosition);
        holder.relayDisplayAdapter.setPosition(listPosition);
        holder.relayDisplayAdapter.setDevice(device);

        if (device.isRefreshing()) {
            holder.startAnim();
        } else {
            holder.stopAnim();
        }

        holder.deviceNameTextView.setText(device.getName());

        if (device.getError() != null) {
            holder.sensorsListView.setVisibility(View.GONE);
            holder.relaysListView.setVisibility(View.GONE);
            String errorName = null;
            switch (device.getError()) {
                case SCHEMA_ERROR:
                    errorName = context.getString(R.string.not_info);
                    break;
                case READING_ERROR:
                    errorName = context.getString(R.string.not_info);
                    break;
                case COMUNICATING_ERROR:
                    errorName = context.getString(R.string.not_info);
            }
            holder.deviceInfoTextView.setText(errorName);
            holder.deviceInfoTextView.setTextColor(Color.RED);
        } else {
            if (device.getSensorModelList() != null && !device.getSensorModelList().isEmpty()) {
                holder.sensorsListView.setVisibility(View.VISIBLE);
                holder.sensorDisplayAdapter.setSensorModelList(device.getSensorModelList());
            } else {
                holder.sensorsListView.setVisibility(View.GONE);
            }
            if (device.getRelayModelList() != null && !device.getRelayModelList().isEmpty()) {
                holder.relaysListView.setVisibility(View.VISIBLE);
                holder.relayDisplayAdapter.setRelayModelList(device.getRelayModelList());
            } else {
                holder.relaysListView.setVisibility(View.GONE);
            }
            if (device.getActualizationDate() != null) {
                holder.deviceInfoTextView.setTextColor(Color.BLUE);
                holder.deviceInfoTextView.setText(simpleDateFormat.format(device.getActualizationDate()));
            } else {
                holder.deviceInfoTextView.setText(context.getString(R.string.not_info));
            }
        }
    }


    @Override
    public int getItemCount() {
        return activeDevices.size();
    }

    public void notifyItemChanged(Object device) {
        notifyItemChanged(activeDevices.indexOf(device));
    }


}
