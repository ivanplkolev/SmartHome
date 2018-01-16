package kolevmobile.com.smarthome.details;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import kolevmobile.com.smarthome.App;
import kolevmobile.com.smarthome.R;
import kolevmobile.com.smarthome.model.DeviceDao;


public class DetailsActivity extends AppCompatActivity implements DetailsView, View.OnClickListener {

    public static final String DEVICE_FOR_DETAILS = "DEVICE_FOR_DETAILS";

    DeviceDao deviceDao;

    @BindView(R.id.detailsDeviceNameTextView)
    TextView detailsDeviceNameTextView;
    @BindView(R.id.detailsDeviceDescTextView)
    TextView detailsDeviceDescTextView;
    @BindView(R.id.day)
    TextView dayButton;
    @BindView(R.id.week)
    TextView weekButton;
    @BindView(R.id.month)
    TextView monthButton;
    @BindView(R.id.year)
    TextView yearButton;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.details_chart_recycler_view)
    RecyclerView detailsRecyclerView;

    @Inject
    DetailsPresenter presenter;
    //    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd.MM");
    private DetailsChartsAdapter detailsChartsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        toolbar.setTitle(R.string.details);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dayButton.setOnClickListener(this);
        weekButton.setOnClickListener(this);
        monthButton.setOnClickListener(this);
        yearButton.setOnClickListener(this);




        RecyclerView.LayoutManager detailsLayoutManager = new LinearLayoutManager(this);
        detailsRecyclerView.setLayoutManager(detailsLayoutManager);
        detailsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        detailsChartsAdapter = new DetailsChartsAdapter(this);
        detailsRecyclerView.setAdapter(detailsChartsAdapter);


        ((App) getApplication()).getPresenterComponent().inject(this);

        presenter.setDetailsHandler(new DetailsHandler());

        Long deviceId = getIntent().getLongExtra(DEVICE_FOR_DETAILS, 0L);

        presenter.initDevice(deviceId);
    }

    @Override
    public void onClick(View view) {
        DetailsPresenter.DetailsPeriod period;
        switch (view.getId()) {
            case R.id.day:
                period = DetailsPresenter.DetailsPeriod.DAY;
                break;
            case R.id.week:
                period = DetailsPresenter.DetailsPeriod.WEEK;
                break;
            case R.id.month:
                period = DetailsPresenter.DetailsPeriod.MONTH;
                break;
            case R.id.year:
                period = DetailsPresenter.DetailsPeriod.YEAR;
                break;
            default:
                period = DetailsPresenter.DetailsPeriod.MONTH;
        }
        presenter.loadHistory(period);
    }

    class DetailsHandler extends Handler {
        final static int DO_UPDATE_ALL_VIEWS = 0;
        final static int DO_INIT_SENSORS = 1;
        final static int DO_SET_DEVICE_NAME = 2;
        final static int DO_SET_DEVICE_DESCRIPTION = 3;

        public void handleMessage(Message msg) {
            final int what = msg.what;
            switch (what) {
                case DO_UPDATE_ALL_VIEWS:
                    detailsChartsAdapter.notifyDataSetChanged();
                    break;
                case DO_INIT_SENSORS:
                    detailsChartsAdapter.setDetailModelList((List<DetailModel>) msg.obj);
                    detailsChartsAdapter.notifyDataSetChanged();
                    break;
                case DO_SET_DEVICE_NAME:
                    detailsDeviceNameTextView.setText((String) msg.obj);
                    break;
                case DO_SET_DEVICE_DESCRIPTION:
                    detailsDeviceDescTextView.setText((String) msg.obj);
                    break;
            }
        }
    }
}