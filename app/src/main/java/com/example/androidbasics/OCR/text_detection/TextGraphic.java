package com.example.androidbasics.OCR.text_detection;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.androidbasics.OCR.others.GraphicOverlay;


/**
 * Graphic instance for rendering TextBlock position, size, and ID within an associated graphic
 * overlay view.
 */
public class TextGraphic extends GraphicOverlay.Graphic {

    private static final int TEXT_COLOR = Color.WHITE;
    private static final float TEXT_SIZE = 54.0f;
    private static final float STROKE_WIDTH = 5.0f;

    private final Paint rectPaint;
    private final Paint textPaint;
    Rect rect;

    TextGraphic(GraphicOverlay overlay, Rect rect) {
        super(overlay);

        this.rect = rect;

        rectPaint = new Paint();
        rectPaint.setColor(TEXT_COLOR);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(STROKE_WIDTH);

        textPaint = new Paint();
        textPaint.setColor(TEXT_COLOR);
        textPaint.setTextSize(TEXT_SIZE);
        // Redraw the overlay, as this graphic has been added.
        //postInvalidate();
    }

    /**
     * Draws the text block annotations for position, size, and raw value on the supplied canvas.
     */
    RectF rectF;
    @Override
    public void draw(Canvas canvas) {

        /*Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int color = ContextCompat.getColor(overlay.context, R.color.frameOutside);
        paint.setColor(color);
        //paint.setColor(Color.RED);
        //paint.setStyle(Paint.Style.FILL);
        canvas.drawPaint(paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));*/
        //canvas.drawRoundRect(frameRectF, getWidth() / 2, getHeight() / 2, paint);
        //canvas.drawCircle( getWidth() / 2, getHeight() / 2,Math.min(getWidth() / 2, getHeight() / 2), paint);
        //canvas.drawCircle( centerX, centerY,Math.min(getWidth() / 2, getHeight() / 2), paint);
        rectF=overlay.translateRect(rect);
        //canvas.drawRect(rectF,paint);



        canvas.drawRect(rectF, rectPaint);

        // Renders the text at the bottom of the box.
        //canvas.drawText(text.getText(), rect.left, rect.bottom, textPaint);*/
    }
}
