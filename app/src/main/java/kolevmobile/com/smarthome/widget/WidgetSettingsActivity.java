package kolevmobile.com.smarthome.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import javax.inject.Inject;

import kolevmobile.com.smarthome.App;
import kolevmobile.com.smarthome.R;
import kolevmobile.com.smarthome.model.Device;
import kolevmobile.com.smarthome.model.SensorModel;
import kolevmobile.com.smarthome.model.Widget;

public class WidgetSettingsActivity extends AppCompatActivity {

    @Inject
    WidgetPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_settings);
        ((App) getApplication()).getPresenterComponent().inject(this);

        Intent intent = getIntent();

        Widget widget = (Widget) intent.getSerializableExtra(WidgetService.WIDGET_ID);

        Spinner spinner1 = findViewById(R.id.spinner1);
        Spinner spinner2 = findViewById(R.id.spinner2);
        ArrayAdapter<Device> deviceArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, presenter.loadAllDevices());
        spinner1.setAdapter(deviceArrayAdapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Device device = (Device) spinner1.getSelectedItem();
                spinner2.setAdapter(new ArrayAdapter<SensorModel>(WidgetSettingsActivity.this, R.layout.support_simple_spinner_dropdown_item, device.getSensorModelList());)
                ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        TextView okButton = findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.finishInitWidget(widget, spinner2.getSelectedItem());
            }
        });
        TextView cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(view -> onBackPressed());

    }
}