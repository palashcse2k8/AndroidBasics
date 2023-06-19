// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.example.androidbasics.OCR.others;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.gms.vision.CameraSource;

import java.util.ArrayList;
import java.util.List;

/**
 * A view which renders a series of custom graphics to be overlayed on top of an associated preview
 * (i.e., the camera preview). The creator can add graphics objects, update the objects, and remove
 * them, triggering the appropriate drawing and invalidation within the view.
 *
 * <p>Supports scaling and mirroring of the graphics relative the camera's preview properties. The
 * idea is that detection items are expressed in terms of a preview size, but need to be scaled up
 * to the full view size, and also mirrored in the case of the front-facing camera.
 *
 * <p>Associated {@link Graphic} items should use the following methods to convert to view
 * coordinates for the graphics that are drawn:
 *
 * <ol>
 *   <li>{@link Graphic#scaleX(float)} and {@link Graphic#scaleY(float)} adjust the size of the
 *       supplied value from the preview scale to the view scale.
 *   <li>{@link Graphic#translateX(float)} and {@link Graphic#translateY(float)} adjust the
 *       coordinate from the preview's coordinate system to the view coordinate system.
 * </ol>
 */
public class GraphicOverlay extends View {


    private final Object lock = new Object();
    private final List<Graphic> graphics = new ArrayList<>();
    public RectF frameRectF = new RectF();
    public float centerX;
    public float centerY;
    //public Rect pFrameRect;
    //public int pCenterX;
    //public int pCenterY;
    private float dx, dy = 400;
    private Paint framePaint = new Paint();
    public Context context;
    private int previewWidth;
    private float widthScaleFactor = 1.0f;
    private int previewHeight;
    private float heightScaleFactor = 1.0f;
    private int facing = CameraSource.CAMERA_FACING_BACK;

    public GraphicOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initDrawTools();
    }


    public float scaleX(float horizontal) {
        return horizontal * widthScaleFactor;
    }


    public float scaleY(float vertical) {
        return vertical * heightScaleFactor;
    }


    public Context getApplicationContext() {
        return getContext().getApplicationContext();
    }

    public float translateX(float x) {
        if (facing == CameraSource.CAMERA_FACING_FRONT) {
            return getWidth() - scaleX(x);
        } else {
            return scaleX(x);
        }
    }


    public float translateY(float y) {
        return scaleY(y);
    }

    public RectF translateRect(Rect rect) {
        return new RectF(
                translateX(rect.left),
                translateY(rect.top),
                translateX(rect.right),
                translateY(rect.bottom));
    }

    public RectF translateRect(RectF rect) {
        return new RectF(
                translateX(rect.left),
                translateY(rect.top),
                translateX(rect.right),
                translateY(rect.bottom));
    }

/*

    public float revScaleX(float horizontal) {
        return horizontal / widthScaleFactor;
    }

    public float revScaleY(float vertical) {
        return vertical / heightScaleFactor;
    }

    public float revTranslateX(float x) {
        if (facing == CameraSource.CAMERA_FACING_FRONT) {
            return getWidth() - revScaleX(x);
        } else {
            return revScaleX(x);
        }
    }


    public RectF revTranslateRectF(Rect rect) {
        return new RectF(
                revTranslateX(rect.left),
                revTranslateY(rect.top),
                revTranslateX(rect.right),
                revTranslateY(rect.bottom));
    }

    public RectF revTranslateRectF(RectF rect) {
        return new RectF(
                revTranslateX(rect.left),
                revTranslateY(rect.top),
                revTranslateX(rect.right),
                revTranslateY(rect.bottom));
    }

    public Rect revTranslateRect(RectF rect) {
        return new Rect(
                (int) revTranslateX(rect.left),
                (int) revTranslateY(rect.top),
                (int) revTranslateX(rect.right),
                (int) revTranslateY(rect.bottom));
    }

    public float revTranslateY(float y) {
        return revScaleY(y);
    }
*/


    public void clear() {
        synchronized (lock) {
            graphics.clear();

        }
        invalidate();
        postInvalidate();
    }


    public void add(Graphic graphic) {
        synchronized (lock) {
            graphics.add(graphic);
        }
        postInvalidate();
    }


    public void remove(Graphic graphic) {
        synchronized (lock) {
            graphics.remove(graphic);
        }
        postInvalidate();
    }

    /**
     * Sets the camera attributes for size and facing direction, which informs how to transform image
     * coordinates later.
     */
    public void setCameraInfo(int previewWidth, int previewHeight, int facing) {
        synchronized (lock) {
            this.previewWidth = previewWidth;
            this.previewHeight = previewHeight;
            this.facing = facing;
        }
        postInvalidate();
    }


    void initDrawTools() {
        framePaint.setColor(Color.WHITE);
        framePaint.setStyle(Paint.Style.STROKE);
        framePaint.setStrokeWidth(5);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //Log.d("=====>>", "onMeasure : (" + widthMeasureSpec + "," + heightMeasureSpec + ")  :  (" + getWidth() + " , " + getHeight() + " )");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
       // Log.d("=====>>", "onMeasure : (" + widthMeasureSpec + "," + heightMeasureSpec + ")  :  (" + getWidth() + " , " + getHeight() + " )");
    }

    float cx,cy;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        synchronized (lock) {
            if ((previewWidth != 0) && (previewHeight != 0)) {
                widthScaleFactor = (float) getWidth() / previewWidth;
                heightScaleFactor = (float) getHeight() / previewHeight;
            }


            /*centerX = translateX(Utils.CenterX);
            centerY = translateY(Utils.CenterY);

            *//*pCenterX = (int) revTranslateX(centerX);
            pCenterY = (int) revTranslateY(centerY);*//*

            cx = getWidth() / 2f;
            cy = getHeight() / 2f;

            dy=cx*2/3;

            frameRectF.left = 0;
            frameRectF.right = getWidth();
            frameRectF.top = cy-cx;
            frameRectF.bottom = cy+cx;

            *//*pFrameRect = revTranslateRect(frameRectF);*//*

            //canvas.drawRect(frameRectF, framePaint);

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            int color = ContextCompat.getColor(context, R.color.frameOutside);
            paint.setColor(color);
            paint.setColor(Color.RED);
            //paint.setStyle(Paint.Style.FILL);
            canvas.drawPaint(paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            //canvas.drawRoundRect(frameRectF, getWidth() / 2, getHeight() / 2, paint);
            //canvas.drawCircle( getWidth() / 2, getHeight() / 2,Math.min(getWidth() / 2, getHeight() / 2), paint);
            //canvas.drawCircle( centerX, centerY,Math.min(getWidth() / 2, getHeight() / 2), paint);

            canvas.drawRect(frameRectF,paint);
            //canvas.drawRect(frameRectF, paint);*/

            for (Graphic graphic : graphics) {
                graphic.draw(canvas);
            }
        }
    }

    /**
     * Base class for a custom graphics object to be rendered within the graphic overlay. Subclass
     * this and implement the {@link Graphic#draw(Canvas)} method to define the graphics element. Add
     * instances to the overlay using {@link GraphicOverlay#add(Graphic)}.
     */
    public abstract static class Graphic {
        protected GraphicOverlay overlay;

        public Graphic(GraphicOverlay overlay) {
            this.overlay = overlay;
        }

        /**
         * Draw the graphic on the supplied canvas. Drawing should use the following methods to convert
         * to view coordinates for the graphics that are drawn:
         *
         * <ol>
         *   <li>{@link Graphic#scaleX(float)} and {@link Graphic#scaleY(float)} adjust the size of the
         *       supplied value from the preview scale to the view scale.
         *   <li>{@link Graphic#translateX(float)} and {@link Graphic#translateY(float)} adjust the
         *       coordinate from the preview's coordinate system to the view coordinate system.
         * </ol>
         *
         * @param canvas drawing canvas
         */
        public abstract void draw(Canvas canvas);

    }
}


