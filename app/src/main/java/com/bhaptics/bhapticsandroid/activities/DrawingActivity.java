package com.bhaptics.bhapticsandroid.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bhaptics.bhapticsandroid.BhapticsModule;
import com.bhaptics.bhapticsandroid.R;
import com.bhaptics.bhapticsmanger.HapticPlayer;
import com.bhaptics.commons.model.PathPoint;
import com.bhaptics.commons.model.PositionType;

import java.util.Arrays;

public class DrawingActivity extends Activity implements View.OnClickListener {
    public static final String TAG = DrawingActivity.class.getSimpleName();

    private Button backButton;

    private HapticPlayer hapticPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        LinearLayout linearLayout = findViewById(R.id.drawing_container);

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(this);

        hapticPlayer = BhapticsModule.getHapticPlayer();
        DrawingView dv = new DrawingView(this);
        linearLayout.addView(dv);
    }

    @Override
    public void onClick(View v) {
        finish();
    }


    public class DrawingView extends View {
        public int width;
        public  int height;
        private Bitmap  mBitmap;
        private Canvas  mCanvas;
        private Path    mPath;
        private Paint   mBitmapPaint;
        private Context context;
        private Paint circlePaint;
        private Path circlePath;

        private Paint mPaint;

        private int canvasWidth, canvasHeight;

        public DrawingView(Context c) {
            super(c);
            context = c;
            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
            circlePaint = new Paint();
            circlePath = new Path();
            circlePaint.setAntiAlias(true);
            circlePaint.setColor(Color.BLUE);
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setStrokeJoin(Paint.Join.MITER);
            circlePaint.setStrokeWidth(4f);


            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setDither(true);
            mPaint.setColor(Color.BLUE);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeJoin(Paint.Join.ROUND);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStrokeWidth(30);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            canvasHeight = h;
            canvasWidth = w;

            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawBitmap( mBitmap, 0, 0, mBitmapPaint);
            canvas.drawPath( mPath,  mPaint);
            canvas.drawPath( circlePath,  circlePaint);
        }

        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 4;

        private void touch_start(float x, float y) {
            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
        }

        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
                mX = x;
                mY = y;

                circlePath.reset();
                circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
            }

            float pathX = x / canvasWidth;
            float pathY = y / canvasHeight;

            // it's hard to go 0 or 1
            float xNormalized = Math.min(1f, Math.max(1.2f * pathX - 0.1f, 0f));
            float yNormalized = Math.min(1f, Math.max(1.2f * pathY - 0.1f, 0f));


            Log.e(TAG, "touch_move: " + pathX + ", " + pathY  + ", " + xNormalized + " , " + yNormalized);
            hapticPlayer.submitPath("VestFront", PositionType.VestFront,
                    Arrays.asList(new PathPoint(xNormalized, yNormalized, 100)), 1000);

            hapticPlayer.submitPath("VestBack", PositionType.VestBack,
                    Arrays.asList(new PathPoint(xNormalized, yNormalized, 100)), 1000);

            hapticPlayer.submitPath("Left", PositionType.Left,
                    Arrays.asList(new PathPoint(xNormalized, yNormalized, 100)), 1000);

            hapticPlayer.submitPath("Right", PositionType.Right,
                    Arrays.asList(new PathPoint(xNormalized, yNormalized, 100)), 1000);
        }

        private void touch_up() {
            mPath.lineTo(mX, mY);
            circlePath.reset();
            // commit the path to our offscreen
            mCanvas.drawPath(mPath,  mPaint);
            // kill this so we don't double draw
            mPath.reset();
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            invalidate();

            hapticPlayer.turnOffAll();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    invalidate();
                    break;
            }
            return true;
        }
    }
}
