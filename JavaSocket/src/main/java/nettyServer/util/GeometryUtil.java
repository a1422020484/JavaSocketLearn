package nettyServer.util;


/**
 * @author xiezuojie
 */
public class GeometryUtil {

    /**
     * @param degree 角度
     * @param r      半径
     * @return 边长, 0为角A的对边, 1为角B的对边
     */
    public static float[] squarelyTriangleSide(float degree, float r) {
        float radian = (float) (degree / 180.0F * Math.PI);
        float[] rs = new float[2];
        rs[0] = (float) (r * Math.sin(radian));
        rs[1] = (float) (r * Math.cos(radian));
        return rs;
    }

    /**
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return 坐标1与坐标2的距离
     */
    public static int distance(int x1, int y1, int x2, int y2) {
        int dx = x1 - x2;
        int dy = y1 - y2;
        return (int) Math.pow(dx * dx + dy * dy, 0.5);
    }

    /**
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return 坐标1与坐标2的距离
     */
    public static float distance(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        return (float) Math.pow(dx * dx + dy * dy, 0.5);
    }

    /**
     * 检测点p是否在以点c为圆心的圆形内
     *
     * @param cx       点c的X
     * @param cy       点c的Y
     * @param squaredR 半径的平方
     * @param px       点p的X
     * @param py       点p的Y
     * @return 点p是否在以点c为圆心的圆形内
     */
    public static boolean isPointInCircular(float cx, float cy, float squaredR,
                                            float px, float py) {
        float dx = px - cx;
        float dy = py - cy;
        float squaredLength = dx * dx + dy * dy;
        return squaredLength <= squaredR;
    }

    /**
     * 检测点p是否在扇形(circular sector)内，设扇形的顶点为c，半径为r，从u方向两边展开角度<br>
     * 源码来自<a href="http://www.cnblogs.com/miloyip/archive/2013/04/19/3029852.html">http://www.cnblogs.com/miloyip/archive/2013/04/19/3029852.html</a>
     *
     * @param cx       顶点 c
     * @param cy       顶点 c
     * @param degree   角度
     * @param squaredR 半径的平方
     * @param cosTheta
     * @param px       点p
     * @param py       点p
     * @return 点p是否在扇形内
     */
    public static boolean isPointInCircularSector(float cx, float cy,
                                                  float degree, float squaredR, float cosTheta, float px,
                                                  float py) {
        float[] side = GeometryUtil.squarelyTriangleSide(degree, squaredR);
        return isPointInCircularSector(cx, cy, side[0], side[1], squaredR, cosTheta, px, py);
    }

    /**
     * 检测点p是否在扇形(circular sector)内，设扇形的顶点为c，半径为r，从u方向两边展开角度<br>
     * 源码来自<a href="http://www.cnblogs.com/miloyip/archive/2013/04/19/3029852.html">http://www.cnblogs.com/miloyip/archive/2013/04/19/3029852.html</a>
     *
     * @param cx       顶点 c
     * @param cy       顶点 c
     * @param ux       角度u
     * @param uy       角度u
     * @param squaredR 半径的平方
     * @param cosTheta
     * @param px       点p
     * @param py       点p
     * @return 点p是否在扇形内
     */
    public static boolean isPointInCircularSector(float cx, float cy,
                                                  float ux, float uy, float squaredR, float cosTheta, float px,
                                                  float py) {
        // D = P - C
        float dx = px - cx;
        float dy = py - cy;

        // |D|^2 = (dx^2 + dy^2)
        float squaredLength = dx * dx + dy * dy;

        // |D|^2 > r^2
        if (squaredLength > squaredR)
            return false;

        // D dot U
        float dDotU = dx * ux + dy * uy;

        // D dot U > |D| cos(theta)
        // <=>
        // (D dot U)^2 > |D|^2 (cos(theta))^2 if D dot U >= 0 and cos(theta) >=
        // 0
        // (D dot U)^2 < |D|^2 (cos(theta))^2 if D dot U < 0 and cos(theta) < 0
        // true if D dot U >= 0 and cos(theta) < 0
        // false if D dot U < 0 and cos(theta) >= 0
        if (dDotU >= 0 && cosTheta >= 0)
            return dDotU * dDotU > squaredLength * cosTheta * cosTheta;
        else if (dDotU < 0 && cosTheta < 0)
            return dDotU * dDotU < squaredLength * cosTheta * cosTheta;
        else
            return dDotU >= 0;
    }
}
