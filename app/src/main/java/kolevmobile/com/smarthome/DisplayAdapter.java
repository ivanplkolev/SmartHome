package kolevmobile.com.smarthome;

/**
 * Created by x on 21.10.2017 г..
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import kolevmobile.com.smarthome.model.Device;

public class DisplayAdapter extends RecyclerView.Adapter<DisplayAdapter.MyViewHolder> {

    private List<Device> devices;

    private Context context;

    private ItemButtonObserver onItemViewClickListener;

    public void setOnItemViewClickListener(ItemButtonObserver onItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView deviceNameTextView;
        TextView tempTextView;
        TextView humidTextView;
        TextView lastRefreshTextView;
        TextView errorMessageTextView;
        View refreshButton;
        View detailsButton;
        View editButton;
        View deleteButton;
        View animationLayout;
        View setts_layout;
        AVLoadingIndicatorView avi;

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

        public MyViewHolder(View itemView) {
            super(itemView);

            View card_base_layout = itemView.findViewById(R.id.card_base_layout);
            setts_layout = itemView.findViewById(R.id.setts_layout);
            Animation animSlideAppear = AnimationUtils.loadAnimation(context, R.anim.slide_appear);
            Animation animSlideDisappear = AnimationUtils.loadAnimation(context, R.anim.slide_disappear);

            card_base_layout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View var1, MotionEvent var2) {
//                    if (!devices.get(getPosition()).isRefreshing()) {
                        boolean vissible = setts_layout.getVisibility() == View.VISIBLE;
                        setts_layout.setVisibility(vissible ? View.INVISIBLE : View.VISIBLE);
                        setts_layout.startAnimation(vissible ? animSlideDisappear : animSlideAppear);
//                    }
                    return false;
                }
            });
            this.deviceNameTextView = itemView.findViewById(R.id.deviceNameTextView);
            this.tempTextView = itemView.findViewById(R.id.tempTextView);
            this.humidTextView = itemView.findViewById(R.id.humidTextView);
            this.lastRefreshTextView = itemView.findViewById(R.id.lastRefreshTextView);
            this.errorMessageTextView = itemView.findViewById(R.id.errorMessageTextView);
            this.refreshButton = itemView.findViewById(R.id.refreshButton);
            refreshButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setts_layout.setVisibility(View.INVISIBLE);
                    onItemViewClickListener.onClick(refreshButton, getPosition());
                }
            });
            this.detailsButton = itemView.findViewById(R.id.detailsButton);
            detailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemViewClickListener.onClick(detailsButton, getPosition());
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
            avi = itemView.findViewById(R.id.avi);
            animationLayout = itemView.findViewById(R.id.animation_layout);
        }
    }

    public DisplayAdapter(List<Device> devices, Context context) {
        this.devices = devices;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

//        if (devices.get(listPosition).isRefreshing()) {
//            holder.startAnim();
//        } else {
//            holder.stopAnim();
//        }

        TextView deviceNameTextView = holder.deviceNameTextView;
        TextView tempTextView = holder.tempTextView;
        TextView humidTextView = holder.humidTextView;
        TextView lastRefreshTextView = holder.lastRefreshTextView;
        TextView errorMessageTextView = holder.errorMessageTextView;

//        deviceNameTextView.setText(devices.get(listPosition).getName());
//        if (devices.get(listPosition).getActualSensorDataUid() != null) {
//            tempTextView.setText(devices.get(listPosition).getActualSensorData().getTemperatureString());
//            humidTextView.setText(devices.get(listPosition).getActualSensorData().getHumidityString());
//            lastRefreshTextView.setText(devices.get(listPosition).getActualSensorData().getDateString());
//        }
//        errorMessageTextView.setText(devices.get(listPosition).getErrorMessage());
    }


    @Override
    public int getItemCount() {
        return devices.size();
    }

}
