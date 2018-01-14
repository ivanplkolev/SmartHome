package kolevmobile.com.smarthome.details;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.Date;
import java.util.List;

import kolevmobile.com.smarthome.model.SensorValue;

public class DetailsChartView extends View {

    private List<SensorValue> values;
    float minVal;
    float maxVal;
    long startDate;
    long endDate;

    static final Paint pointPaint = new Paint();

    static final Paint linePaint = new Paint();

    public DetailsChartView(Context context) {
        super(context);
    }

    public DetailsChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setValues(List<SensorValue> values) {
        this.values = values;
        calculateChart();
    }

    private void calculateChart() {
        minVal = Float.MAX_VALUE;
        maxVal = Float.MIN_VALUE;
        startDate = values.get(0).getMeasuredAt().getTime();
        endDate = values.get(values.size() - 1).getMeasuredAt().getTime();
        for (SensorValue value : values) {
            Float val = value.getValue();
            Date date = value.getMeasuredAt();
            if (val < minVal) {
                minVal = val;
            }
            if (val > maxVal) {
                maxVal = val;
            }
        }
        pointPaint.setColor(Color.RED);
        pointPaint.setStyle(Paint.Style.FILL);
        linePaint.setColor(Color.BLUE);
        linePaint.setStrokeWidth(5);
        linePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (values == null) {
            return;
        }
        float width = getWidth();
        float height = getHeight();

        float horizontalStep = width / (endDate - startDate);
        float verticalStep = height / (maxVal - minVal);

        Path chartPath = new Path();
        for (int i = 0; i < values.size(); i++) {
            SensorValue val = values.get(i);
            int pointX = (int) ((val.getMeasuredAt().getTime() - startDate) * horizontalStep);
            int pointY = (int) ((val.getValue() - minVal) * verticalStep);
            if (i == 0) {
                chartPath.moveTo(pointX, pointY);
            } else {
                chartPath.lineTo(pointX, pointY);
            }
            canvas.drawCircle(pointX, pointY, 8, pointPaint);
        }
        canvas.drawPath(chartPath, linePaint);
    }


}
