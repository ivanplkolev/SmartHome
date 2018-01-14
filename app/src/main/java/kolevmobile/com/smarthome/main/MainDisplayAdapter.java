package kolevmobile.com.smarthome.main;


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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kolevmobile.com.smarthome.ItemButtonObserver;
import kolevmobile.com.smarthome.R;
import kolevmobile.com.smarthome.custom_components.ExpandableLayout;
import kolevmobile.com.smarthome.model.Device;

public class MainDisplayAdapter extends RecyclerView.Adapter<MainDisplayAdapter.DeviceViewHolder> {

    private List<Device> activeDevices;
    private Context context;
    private ItemButtonObserver onItemViewClickListener;


    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd.MM");

    MainDisplayAdapter(Context context, ItemButtonObserver onItemViewClickListene) {
        this.context = context;
        this.onItemViewClickListener = onItemViewClickListene;
    }

    public void setActiveDevices(List<Device> activeDevices) {
        this.activeDevices = activeDevices;
    }


    class DeviceViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.deviceNameTextView)
        TextView deviceNameTextView;
        @BindView(R.id.deviceInfoTextView)
        TextView deviceInfoTextView;
        @BindView(R.id.refreshButton)
        View refreshButton;
        @BindView(R.id.detailsButton)
        View detailsButton;
        @BindView(R.id.editButton)
        View editButton;
        @BindView(R.id.deleteButton)
        View deleteButton;
        @BindView(R.id.expandable_layout)
        ExpandableLayout expandableLayout;
        @BindView(R.id.avi)
        AVLoadingIndicatorView avi;
        @BindView(R.id.sensors_list_view)
        RecyclerView sensorsListView;
        @BindView(R.id.relays_list_view)
        RecyclerView relaysListView;

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

            ButterKnife.bind(this, itemView);

            refreshButton.setOnClickListener(view -> {
                onItemViewClickListener.onClick(refreshButton, getPosition(), 0);
            });
            detailsButton.setOnClickListener(view -> onItemViewClickListener.onClick(detailsButton, getPosition(), 0));
            editButton.setOnClickListener(view -> onItemViewClickListener.onClick(editButton, getPosition(), 0));
            deleteButton.setOnClickListener(view -> onItemViewClickListener.onClick(deleteButton, getPosition(), 0));

            RecyclerView.LayoutManager recyclerViewLayoutManagerSensor = new LinearLayoutManager(context);
            RecyclerView.LayoutManager recyclerViewLayoutManagerRelay = new LinearLayoutManager(context);
            sensorsListView.setLayoutManager(recyclerViewLayoutManagerSensor);
            relaysListView.setLayoutManager(recyclerViewLayoutManagerRelay);
            sensorDisplayAdapter = new SensorDisplayAdapter(context);
            relayDisplayAdapter = new RelayDisplayAdapter(context, onItemViewClickListener);
            LinearLayoutManager HorizontalLayoutSensor = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            LinearLayoutManager HorizontalLayoutRelay = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            sensorsListView.setLayoutManager(HorizontalLayoutSensor);
            relaysListView.setLayoutManager(HorizontalLayoutRelay);
            sensorsListView.setAdapter(sensorDisplayAdapter);
            relaysListView.setAdapter(relayDisplayAdapter);
        }

    }

    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_row_view, parent, false);

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
        return activeDevices != null ? activeDevices.size() : 0;
    }

    public void notifyItemChanged(Object device) {
        notifyItemChanged(activeDevices.indexOf(device));
    }


}
