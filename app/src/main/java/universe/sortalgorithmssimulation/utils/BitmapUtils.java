package universe.sortalgorithmssimulation.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Nhat on 5/17/2017.
 */

public final class BitmapUtils {

    private static BitmapFactory.Options options;

    private BitmapUtils() {
    }

    public static Bitmap loadResizedBitmap(Resources resources, int drawableId, int width, int height) {
        /*if(options == null) {
            options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(resources, drawableId, options);
            int srcWidth = options.outWidth;
            options.inJustDecodeBounds = false;
            options.inScaled = true;
            options.inSampleSize = 4;
            options.inDensity = srcWidth;
            options.inTargetDensity = width * options.inSampleSize;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        }
        return BitmapFactory.decodeResource(resources, drawableId, options);*/
        return getResizedBitmap(BitmapFactory.decodeResource(resources, drawableId), width, height);
    }

    public static Bitmap getResizedBitmap(Bitmap bitmap, int bitmapWidth, int bitmapHeight) {
        return Bitmap.createScaledBitmap(bitmap, bitmapWidth, bitmapHeight, true);
    }

    public static Bitmap drawTextToBitmap(Bitmap bitmap, float scale, String gText) {

        Bitmap.Config bitmapConfig =
                bitmap.getConfig();
        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);
        // new antialised Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(Color.rgb(61, 61, 61));
        // text size in pixels
        paint.setTextSize(scale);
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.RED);
        paint.setAntiAlias(true);
        // draw text to the Canvas center
        Rect bounds = new Rect();
        paint.getTextBounds(gText, 0, gText.length(), bounds);
        Log.d("HERE", bounds.width() + "");
        int x = (bitmap.getWidth() - bounds.width()) / 2;
        int y = (bitmap.getHeight() + bounds.height()) / 2;

        canvas.drawText(gText, x, y, paint);

        return bitmap;
    }
}
