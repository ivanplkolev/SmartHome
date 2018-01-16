package kolevmobile.com.smarthome.details;

import android.os.Handler;

import java.text.SimpleDateFormat;


public interface DetailsPresenter {

    enum DetailsPeriod {
        DAY(1, 1, new SimpleDateFormat("HH")),
        WEEK(7, 24, new SimpleDateFormat("EEE")),
        MONTH(30, 24, new SimpleDateFormat("dd")),
        YEAR(365, 44,  new SimpleDateFormat("MMM"));

        private int days;
        private SimpleDateFormat sdf;
        private int intervalinHours;

        DetailsPeriod(int days, int intervalinHours, SimpleDateFormat sdf) {
            this.days = days;
            this.intervalinHours = intervalinHours;
            this.sdf = sdf;
        }

        int getDays() {
            return days;
        }

        public int getIntervalinHours() {
            return intervalinHours;
        }

        public SimpleDateFormat getSdf() {
            return sdf;
        }
    }


    void setView(DetailsView view);

    void setDetailsHandler(Handler handler);

    void initDevice(Long deviceId);

    void loadHistory(DetailsPeriod period);

}
