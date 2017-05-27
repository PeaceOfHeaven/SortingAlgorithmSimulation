package universe.sortalgorithmssimulation.utils;

import android.graphics.Path;

/**
 * Created by Nhat on 5/23/2017.
 */

public final class PathUtils {

    public static Path createQuadraticBezier(float startX, float startY,
                                             float ctrlPointX, float ctrlPointY,
                                             float endX, float endY) {
        // We only need 1 path for greater mBalls and then we can calculate lesser mBalls
        // depend on greater mBalls
        Path path = new Path();
        path.moveTo(startX, startY);

        // bezier curve
        path.quadTo(ctrlPointX
                , ctrlPointY
                , endX
                , endY);
        return path;
    }

    public static Path createCubicBezier(float startX, float startY,
                                         float ctrlPointX1, float ctrlPointY1,
                                         float ctrlPointX2, float ctrlPointY2,
                                         float endX, float endY) {
        // We only need 1 path for greater mBalls and then we can calculate lesser mBalls
        // depend on greater mBalls
        Path path = new Path();
        path.moveTo(startX, startY);

        // bezier curve
        path.cubicTo(ctrlPointX1
                , ctrlPointY1, ctrlPointX2
                , ctrlPointY2
                , endX
                , endY);
        return path;
    }
}
