package kolevmobile.com.smarthome.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kolevmobile.com.smarthome.App;
import kolevmobile.com.smarthome.R;
import kolevmobile.com.smarthome.about.AboutActivity;
import kolevmobile.com.smarthome.add_edit_device.AddEditDeviceActivity;
import kolevmobile.com.smarthome.connection.Communicator;
import kolevmobile.com.smarthome.connection.CommunicatorImpl;
import kolevmobile.com.smarthome.details.DetailsActivity;
import kolevmobile.com.smarthome.model.DaoSession;
import kolevmobile.com.smarthome.model.Device;
import kolevmobile.com.smarthome.model.DeviceDao;
import kolevmobile.com.smarthome.model.RelayModel;


public class MainActivity extends AppCompatActivity implements MainView {

    private MainPresenter presenter;

    private MainDisplayAdapter mainDisplayAdapter;
    private Handler mainHandler;

    public final static int DO_UPDATE_ALL_VIEWS = 0;
    public final static int DO_UPDATE_DEVICE_VIEW = 1;
    public final static int DO_REMOVE_DEVICE_VIEW = 2;
    public final static int DO_INIT_DEVICES = 3;

    @BindView(R.id.main_navigation_view)
    private NavigationView mainNavigationView;
    @BindView(R.id.main_drawer)
    private DrawerLayout mainDrawer;
    @BindView(R.id.main_toolbar)
    private Toolbar mainToolbar;
    @BindView(R.id.main_recycler_view)
    private RecyclerView mainRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mainToolbar.setTitle("Main activity");


        RecyclerView.LayoutManager mainLayoutManager = new LinearLayoutManager(this);
        mainRecyclerView.setLayoutManager(mainLayoutManager);
        mainRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mainDisplayAdapter = new MainDisplayAdapter(this);
        mainDisplayAdapter.setOnItemViewClickListener((view, position, subPosition) -> {
            switch (view.getId()) {
                case R.id.refreshButton:
                    presenter.refreshDevice(position);
                    break;
                case R.id.detailsButton:
                    presenter.showDeviceDetails(position);
                    break;
                case R.id.editButton:
                    presenter.editDevice(position);
                    break;
                case R.id.deleteButton:
                    presenter.removeDevice(position);
                    break;
                case R.id.relay_toggler:
                    presenter.switchDeviceRelay(position, subPosition, ((SwitchCompat) view).isChecked());
                    break;
            }
        });
        mainRecyclerView.setAdapter(mainDisplayAdapter);

        mainHandler = new Handler() {
            public void handleMessage(Message msg) {
                final int what = msg.what;
                switch (what) {
                    case DO_UPDATE_ALL_VIEWS:
                        mainDisplayAdapter.notifyDataSetChanged();
                        break;
                    case DO_UPDATE_DEVICE_VIEW:
                        mainDisplayAdapter.notifyItemChanged(msg.obj);
                        break;
                    case DO_REMOVE_DEVICE_VIEW:
                        mainDisplayAdapter.notifyItemRemoved((Integer) msg.obj);
                        break;
                    case DO_INIT_DEVICES:
                        mainDisplayAdapter.setActiveDevices((List<Device>) msg.obj);
                        mainDisplayAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };
        setUpNavigationView();

        presenter = new MainPresenterImpl(this, mainHandler);
        presenter.onCreate();
    }


    @Override
    public void onBackPressed() {
        if (mainDrawer.isDrawerOpen(GravityCompat.START)) {
            mainDrawer.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    private void setUpNavigationView() {
        mainNavigationView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.add_device:
                    presenter.addDevice();
                    mainDrawer.closeDrawers();
                    break;
                case R.id.about_page:
                    gotoAboutPage();
                    mainDrawer.closeDrawers();
                    break;
            }
            return true;
        });
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mainDrawer, mainToolbar, R.string.open_drawer, R.string.close_drawer);
        mainDrawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    public void gotoAboutPage() {
        startActivity(new Intent(this, AboutActivity.class));
    }

    public Handler getMainHandler() {
        return mainHandler;
    }
}
