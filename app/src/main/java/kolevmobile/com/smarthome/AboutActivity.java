package kolevmobile.com.smarthome;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class AboutActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("About..");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_edit_black_24dp));
        Picasso.with(this).load(R.drawable.iot).fit().into((ImageView) findViewById(R.id.main_layout_bkg));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    public void sendMail(View v){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", getString(R.string.string_about_email), null));
        startActivity(Intent.createChooser(emailIntent, "Contact Ivan Kolev"));
    }

    public void openWeb(View v){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.string_about_linkedin)));
        startActivity(i);
    }

}
