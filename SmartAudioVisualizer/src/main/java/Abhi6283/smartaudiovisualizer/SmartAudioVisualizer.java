package Abhi6283.smartaudiovisualizer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


import java.util.Random;

public class SmartAudioVisualizer extends View {

    private int barCount;
    private int barWidth;
    private int barSpacing;
    private int barColor;
    private int barSpeed;

    private int[] barHeights;
    private Paint barPaint;
    private Random random = new Random();

    public SmartAudioVisualizer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SmartAudioVisualizer(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        // Defaults
        barCount = 48;
        barWidth = dpToPx(4);
        barSpacing = dpToPx(2);
        barColor = 0xFF6757E6;
        barSpeed = 100;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LineBarVisualizer);
            barColor = a.getColor(R.styleable.LineBarVisualizer_barColor, barColor);
            barCount = a.getInt(R.styleable.LineBarVisualizer_barCount, barCount);
            barWidth = a.getDimensionPixelSize(R.styleable.LineBarVisualizer_barWidth, barWidth);
            barSpacing = a.getDimensionPixelSize(R.styleable.LineBarVisualizer_barSpacing, barSpacing);
            barSpeed = a.getInt(R.styleable.LineBarVisualizer_barSpeed, barSpeed);
            a.recycle();
        }

        barHeights = new int[barCount];
        barPaint = new Paint();
        barPaint.setColor(barColor);
        barPaint.setStrokeWidth(barWidth);
    }

    public void updateVisualizer(byte[] amplitudes) {
        for (int i = 0; i < barCount && i < amplitudes.length; i++) {
            barHeights[i] = Math.abs(amplitudes[i]);
        }
        invalidate();
    }

    public void simulateDemoWave() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < barCount; i++) {
                    barHeights[i] = 10 + random.nextInt(getHeight());
                }
                invalidate();
                simulateDemoWave(); // Loop
            }
        }, barSpeed);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = 0;
        for (int i = 0; i < barCount; i++) {
            int height = barHeights[i];
            int startY = (getHeight() - height) / 2;
            canvas.drawLine(x, startY, x, startY + height, barPaint);
            x += barWidth + barSpacing;
        }
    }

    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }
}
