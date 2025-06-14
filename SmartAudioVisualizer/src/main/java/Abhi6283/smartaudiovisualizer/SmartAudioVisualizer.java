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
    private final Random random = new Random();

    private boolean isPaused = false;

    private final Runnable simulationRunnable = new Runnable() {
        @Override
        public void run() {
            if (!isPaused) {
                for (int i = 0; i < barCount; i++) {
                    barHeights[i] = 10 + random.nextInt(getHeight());
                }
                invalidate();
                postDelayed(this, barSpeed);
            }
        }
    };

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
        isPaused = false;
        removeCallbacks(simulationRunnable);
        post(simulationRunnable);
    }

    public void pauseSimulation() {
        isPaused = true;
        removeCallbacks(simulationRunnable);
    }

    public void resumeSimulation() {
        if (isPaused) {
            isPaused = false;
            post(simulationRunnable);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int totalBarSpace = barCount * barWidth + (barCount - 1) * barSpacing;
        float startX = (getWidth() - totalBarSpace) / 2f;

        float x = startX;
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

    // ===== DYNAMIC SETTERS =====

    public void setBarCount(int count) {
        this.barCount = count;
        this.barHeights = new int[barCount];
        invalidate();
    }

    public void setBarWidth(int widthPx) {
        this.barWidth = widthPx;
        barPaint.setStrokeWidth(barWidth);
        invalidate();
    }

    public void setBarSpacing(int spacingPx) {
        this.barSpacing = spacingPx;
        invalidate();
    }

    public void setBarColor(int color) {
        this.barColor = color;
        barPaint.setColor(color);
        invalidate();

    }

    public void setBarSpeed(int speedMs) {
        this.barSpeed = speedMs;
    }

    public int getBarCount() {
        return barCount;
    }
}
