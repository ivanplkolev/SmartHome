package kolevmobile.com.smarthome.details;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import kolevmobile.com.smarthome.model.SensorValue;


public class ChartUtils {

    private TreeMap<Date, Float> valuesMap = new TreeMap<>();

    private Date mindate;
    private Date maxDate;

    private Float maxVal;
    private Float minVal;

    private float scale = 1f;
    private float minRounded;
    private float maxRounded;


    private Date truncate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        switch (period) {
            case YEAR:
                cal.set(Calendar.DAY_OF_MONTH, 0);
                cal.add(Calendar.MONTH, 1);
            case MONTH:
            case WEEK:
                cal.set(Calendar.HOUR_OF_DAY, 0);
            case DAY:
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
        }
        return cal.getTime();
    }

    private DetailsPresenter.DetailsPeriod period;

    ChartUtils(List<SensorValue> values, DetailsPresenter.DetailsPeriod period) {
        this.period = period;
        Map<Date, List<Float>> map = new HashMap<>();
        for (SensorValue val : values) {
            Date date = val.getMeasuredAt();
            Float value = val.getValue();
            Date truncatedDte = truncate(date);
            List<Float> list = map.get(truncatedDte);
            if (list == null) {
                list = new ArrayList<>();
                map.put(truncatedDte, list);
            }
            list.add(value);
        }

        for (Map.Entry<Date, List<Float>> entry : map.entrySet()) {
            Date date = entry.getKey();
            Float value = 0f;
            for (Float val : entry.getValue()) {
                value += val;
            }
            value /= entry.getValue().size();
            valuesMap.put(date, value);

            if (mindate == null) {
                mindate = date;
            } else if (date.before(mindate)) {
                mindate = date;
            }

            if (maxDate == null) {
                maxDate = date;
            } else if (date.after(maxDate)) {
                maxDate = date;
            }

            if (maxVal == null) {
                maxVal = value;
            } else if (value > maxVal) {
                maxVal = value;
            }

            if (minVal == null) {
                minVal = value;
            } else if (value < minVal) {
                minVal = value;
            }
        }

        float d = maxVal - minVal;
        while (d < 0) {
            d *= 10;
            scale *= 10;
        }
        while (d > 10) {
            d /= 10;
            scale /= 10;
        }

        float min = minVal * scale;
        float max = maxVal * scale;
        int minInt = Math.round(min) > min ? Math.round(min) - 1 : Math.round(min);
        int maxInt = Math.round(max) < max ? Math.round(max) + 1 : Math.round(max);
        minRounded = minInt / scale;
        maxRounded = maxInt / scale;
    }

    Date getMinDate() {
        return mindate;
    }

    Date getMaxDate() {
        return maxDate;
    }

    Map<Date, Float> getvalues() {
        return valuesMap;
    }

    public float getScale() {
        return scale;
    }

    public float getMinRounded() {
        return minRounded;
    }

    public float getMaxRounded() {
        return maxRounded;
    }
}
