package kolevmobile.com.smarthome.main;

/**
 * Created by x on 21.10.2017 г..
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.List;

import kolevmobile.com.smarthome.ItemButtonObserver;
import kolevmobile.com.smarthome.R;
import kolevmobile.com.smarthome.model.Device;

public class DisplayAdapter extends RecyclerView.Adapter<DisplayAdapter.DeviceViewHolder> {

    private List<Device> devices;

    private Context context;

    private ItemButtonObserver onItemViewClickListener;

    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd.MM");


    public void setOnItemViewClickListener(ItemButtonObserver onItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener;
    }


    public class DeviceViewHolder extends RecyclerView.ViewHolder {

        TextView deviceNameTextView;
        TextView tempTextView;
        TextView humidTextView;
        TextView lastRefreshTextView;
        //        TextView errorMessageTextView;
        View refreshButton;
        View detailsButton;
        View editButton;
        View deleteButton;
        View animationLayout;
        View setts_layout;
        View content_layout;
        AVLoadingIndicatorView avi;

        RecyclerView sensorsListView;
        RecyclerView relaysListView;
        RecyclerView.LayoutManager recyclerViewLayoutManagerSensor;
        RecyclerView.LayoutManager recyclerViewLayoutManagerRelay;
        SensorDisplayAdapter sensorDisplayAdapter;
        RelayDisplayAdapter relayDisplayAdapter;
        LinearLayoutManager HorizontalLayoutSensor;
        LinearLayoutManager HorizontalLayoutRelay;
        View childView;
        int relayPosition;

        void startAnim() {
            animationLayout.setVisibility(View.VISIBLE);
            avi.show();
            // or avi.smoothToShow();
        }

        void stopAnim() {
            avi.hide();
            animationLayout.setVisibility(View.GONE);
            // or avi.smoothToHide();
        }

//        ImageView imageViewIcon;

        public DeviceViewHolder(View itemView) {
            super(itemView);

            View card_base_layout = itemView.findViewById(R.id.card_base_layout);
            setts_layout = itemView.findViewById(R.id.setts_layout);
            content_layout = itemView.findViewById(R.id.content_layout);
            Animation animSlideAppear = AnimationUtils.loadAnimation(context, R.anim.slide_appear);
            Animation animSlideDisappear = AnimationUtils.loadAnimation(context, R.anim.slide_disappear);
            Animation animSlideUp = AnimationUtils.loadAnimation(context, R.anim.slide_down);
            Animation animSlideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);

            card_base_layout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View var1, MotionEvent var2) {
//                    if (!devices.get(getPosition()).isRefreshing()) {
                    boolean vissible = setts_layout.getVisibility() == View.VISIBLE;
                    setts_layout.setVisibility(vissible ? View.GONE : View.VISIBLE);
                    setts_layout.startAnimation(vissible ? animSlideDisappear : animSlideAppear);
//                    content_layout.startAnimation(vissible ? animSlideUp : animSlideDown);
//                    }
                    return false;
                }

            });
            this.deviceNameTextView = itemView.findViewById(R.id.deviceNameTextView);
//            this.tempTextView = itemView.findViewById(R.id.tempTextView);
//            this.humidTextView = itemView.findViewById(R.id.humidTextView);
            this.lastRefreshTextView = itemView.findViewById(R.id.lastRefreshTextView);
//            this.errorMessageTextView = itemView.findViewById(R.id.errorMessageTextView);
            this.refreshButton = itemView.findViewById(R.id.refreshButton);
            refreshButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setts_layout.setVisibility(View.INVISIBLE);
                    onItemViewClickListener.onClick(refreshButton, getPosition(), 0);
                }
            });
            this.detailsButton = itemView.findViewById(R.id.detailsButton);
            detailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemViewClickListener.onClick(detailsButton, getPosition(), 0);
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
            avi = itemView.findViewById(R.id.avi);
            animationLayout = itemView.findViewById(R.id.animation_layout);


            sensorsListView = itemView.findViewById(R.id.sensors_list_view);
            relaysListView = itemView.findViewById(R.id.relays_list_view);

            recyclerViewLayoutManagerSensor = new LinearLayoutManager(context);
            recyclerViewLayoutManagerRelay = new LinearLayoutManager(context);

            sensorsListView.setLayoutManager(recyclerViewLayoutManagerSensor);
            relaysListView.setLayoutManager(recyclerViewLayoutManagerRelay);

            // Adding items to RecyclerView.
//            AddItemsToRecyclerViewArrayList();

            sensorDisplayAdapter = new SensorDisplayAdapter();
            relayDisplayAdapter = new RelayDisplayAdapter(getPosition());

            HorizontalLayoutSensor = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            HorizontalLayoutRelay = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            sensorsListView.setLayoutManager(HorizontalLayoutSensor);
            relaysListView.setLayoutManager(HorizontalLayoutRelay);

            sensorsListView.setAdapter(sensorDisplayAdapter);
            relaysListView.setAdapter(relayDisplayAdapter);

            relayDisplayAdapter.setOnItemViewClickListener(onItemViewClickListener);


//            relaysListView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//                GestureDetector gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
//                    @Override
//                    public boolean onSingleTapUp(MotionEvent motionEvent) {
//                        return true;
//                    }
//                });
//
//                @Override
//                public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
//                    childView = recyclerView.findchildViewUnder(motionEvent.getX(), motionEvent.getY());
//                    if (childView != null && gestureDetector.onTouchEvent(motionEvent)) {
//                        relayPosition = recyclerView.getChildAdapterPosition(childView);
//                        Toast.makeText(context, String.valueOf(relayPosition), Toast.LENGTH_LONG).show();
//                    }
//                    return false;
//                }
//
//                @Override
//                public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {
//
//                }
//
//                @Override
//                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//                }
//            });
        }
    }

    public DisplayAdapter(List<Device> devices, Context context) {
        this.devices = devices;
        this.context = context;
    }

    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);


        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DeviceViewHolder holder, final int listPosition) {

        Device device = devices.get(listPosition);


        holder.relayDisplayAdapter.setPosition(listPosition);

//        if (devices.get(listPosition).isRefreshing()) {
//            holder.startAnim();
//        } else {
//            holder.stopAnim();
//        }

        if(device.getError() != null){
            holder.sensorsListView.setVisibility(View.GONE);
            holder.relaysListView.setVisibility(View.GONE);
            holder.lastRefreshTextView.setText(device.getError().name());
            holder.lastRefreshTextView.setTextColor(Color.RED);
        } else {


            if (device.getSensorModelList() != null && !device.getSensorModelList().isEmpty()) {
                holder.sensorsListView.setVisibility(View.VISIBLE);
                holder.sensorDisplayAdapter.setSensorModelList(device.getSensorModelList());
            } else {
                holder.sensorsListView.setVisibility(View.GONE);
            }
            if (device.getRelayModelList() != null && !device.getRelayModelList().isEmpty()) {
                holder.relayDisplayAdapter.setRelayModelList(device.getRelayModelList());
            } else {
                holder.relaysListView.setVisibility(View.GONE);
            }


            holder.deviceNameTextView.setText(device.getName());
            if (device.getActualizationDate() != null) {
                holder.lastRefreshTextView.setTextColor(Color.BLUE);
                holder.lastRefreshTextView.setText(simpleDateFormat.format(device.getActualizationDate()));
            } else {
                holder.lastRefreshTextView.setText("Not connected yet!");
            }
        }
    }


    @Override
    public int getItemCount() {
        return devices.size();
    }

    public void notifyItemChanged(Object device){
        notifyItemChanged(devices.indexOf(device));
    }


}
