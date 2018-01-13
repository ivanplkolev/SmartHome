package kolevmobile.com.smarthome.about;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import kolevmobile.com.smarthome.R;


public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.about);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        Picasso.with(this).load(R.drawable.background_image).fit().into((ImageView) findViewById(R.id.main_layout_bkg));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    public void sendMail(){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", getString(R.string.string_about_email), null));
        startActivity(Intent.createChooser(emailIntent, "Contact Ivan Kolev"));
    }

    public void openWeb(){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.string_about_linkedin)));
        startActivity(i);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.mail_button: sendMail();break;
            case R.id.llinkedIn_button:openWeb(); break;
        }
    }
}
