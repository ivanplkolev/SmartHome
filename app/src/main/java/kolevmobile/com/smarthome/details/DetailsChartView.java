package kolevmobile.com.smarthome.details;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class DetailsChartView extends View {

    ChartUtils utils;

    private SimpleDateFormat sdf;

    static final Paint pointPaint = new Paint();

    static final Paint linePaint = new Paint();

    static final Paint textDatePaint = new Paint();
    static final Paint textValuePaint = new Paint();

    static {
        pointPaint.setColor(Color.RED);
        pointPaint.setStyle(Paint.Style.FILL);
        linePaint.setColor(Color.BLUE);
        linePaint.setStrokeWidth(5);
        linePaint.setStyle(Paint.Style.STROKE);
        textValuePaint.setColor(Color.RED);
        textValuePaint.setTextSize(30);
        textDatePaint.setColor(Color.DKGRAY);
        textDatePaint.setTextSize(30);
        textDatePaint.setTextAlign(Paint.Align.CENTER);
        textValuePaint.setTextAlign(Paint.Align.CENTER);
    }

    public DetailsChartView(Context context) {
        super(context);
    }

    public DetailsChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void update(DetailModel detailModel) {
        this.utils = new ChartUtils(detailModel.getSensorValueList(), detailModel.getDetailsPeriod());
        this.sdf = detailModel.getDetailsPeriod().getSdf();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (utils == null) {
            return;
        }
        float width = getWidth();
        float height = getHeight();

        float startW = 0.2f * width;
        float startH = 0.1f * height;

        float endW = 0.9f * width;
        float endH = 0.8f * height;

        float dateLine = 0.9f * height;
        float valuesLine = 0.1f * width;


        float horizontalStep = (endW - startW) / (utils.getMaxDate().getTime() - utils.getMinDate().getTime() != 0 ? utils.getMaxDate().getTime() - utils.getMinDate().getTime() : 2);
        float verticalStep = (endH - startH) / (utils.getMaxRounded() - utils.getMinRounded());

        //debug:
//        canvas.drawRect(startW, startH, endW, endH, new Paint());
//        canvas.drawLine(valuesLine, startH, valuesLine, endH, textDatePaint);
//        canvas.drawLine(startW, dateLine, endW, dateLine, textValuePaint);

        for (float i = utils.getMinRounded(); i <= utils.getMaxRounded(); i += 1 / utils.getScale()) {
            int pointY = (int) (endH - ((i - utils.getMinRounded()) * verticalStep));
            canvas.drawText(String.valueOf(i), valuesLine, pointY, textValuePaint);
            canvas.drawLine(startW, pointY, endW, pointY, textValuePaint);
        }


        Path chartPath = new Path();

        Map<Date, Float> values = utils.getvalues();
        boolean notStarted = true;
        for (Map.Entry<Date, Float> val : values.entrySet()) {
            Date date = val.getKey();
            Float value = val.getValue();
            int pointX = (int) ((int) startW + ((utils.getMinDate().getTime() == utils.getMaxDate().getTime() ? 1 : date.getTime() - utils.getMinDate().getTime()) * horizontalStep));
            int pointY = (int) (endH - ((value - utils.getMinRounded()) * verticalStep));
            if (notStarted) {
                chartPath.moveTo(pointX, pointY);
                notStarted = false;
            } else {
                chartPath.lineTo(pointX, pointY);
            }
            canvas.drawText(sdf.format(date), pointX, dateLine, textDatePaint);
            canvas.drawCircle(pointX, pointY, 12, pointPaint);
        }
        canvas.drawPath(chartPath, linePaint);
    }


}
