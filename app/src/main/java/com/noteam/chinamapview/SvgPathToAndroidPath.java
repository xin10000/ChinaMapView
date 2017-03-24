package com.noteam.chinamapview;

import android.graphics.Path;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明：将svg格式的path字符串转化为java的Path对象
 * Created by 李明杰 on 2017/3/24.
 */
public class SvgPathToAndroidPath {
    private static final String TAG = "SvgPathToAndroidPath";
    private int svgPathLenght = 0;
    private String svgPath = null;
    private int mIndex;
    private float scale=2.5f;
    private List<Integer> cmdPositions = new ArrayList<>();
    /**
     * M x,y
     * L x,y
     * H x
     * V y
     * C x1,y1,x2,y2,x,y
     * Q x1,y1,x,y
     * S x2,y2,x,y
     * T x,y
     * */
    public Path parser(String svgPath) {
        this.svgPath = svgPath;
        svgPathLenght = svgPath.length();
        mIndex = 0;
        Path lPath = new Path();
        
        lPath.setFillType(Path.FillType.WINDING);
        //记录最后一个操作点
        PointF lastPoint = new PointF();
        findCommand();
        for (int i = 0; i < cmdPositions.size(); i++) {
            Integer index = cmdPositions.get(i);

//            Log.e(TAG, "parser: " + svgPath.charAt(index));
            switch (svgPath.charAt(index)) {
                case 'm':
                case 'M': {
                    String ps[] = findPoints(i);
                    lastPoint.set(Float.parseFloat(ps[0]), Float.parseFloat(ps[1]));
                    lPath.moveTo(lastPoint.x*scale, lastPoint.y*scale);
                }
                break;
                case 'l':
                case 'L': {
                    String ps[] = findPoints(i);
                    lastPoint.set(Float.parseFloat(ps[0]), Float.parseFloat(ps[1]));
                    lPath.lineTo(lastPoint.x*scale, lastPoint.y*scale);
                }
                break;
                case 'h':
                case 'H': {//基于上个坐标在水平方向上划线，因此y轴不变
                    String ps[] = findPoints(i);
                    lastPoint.set(Float.parseFloat(ps[0]), lastPoint.y);
                    lPath.lineTo(lastPoint.x*scale, lastPoint.y*scale);
                }
                break;
                case 'v':
                case 'V': {//基于上个坐标在水平方向上划线，因此x轴不变
                    String ps[] = findPoints(i);
                    lastPoint.set(lastPoint.x, Float.parseFloat(ps[0]));
                    lPath.lineTo(lastPoint.x*scale, lastPoint.y*scale);
                }
                break;
                case 'c':
                case 'C': {//3次贝塞尔曲线
                    String ps[] = findPoints(i);
                    lastPoint.set(Float.parseFloat(ps[4]), Float.parseFloat(ps[5]));
                    lPath.cubicTo(Float.parseFloat(ps[0])*scale, Float.parseFloat(ps[1])*scale, Float.parseFloat(ps[2])*scale, Float.parseFloat(ps[3])*scale, Float.parseFloat(ps[4])*scale, Float.parseFloat(ps[5])*scale);
                }
                break;
                case 's':
                case 'S': {//一般S会跟在C或是S命令后面使用，用前一个点做起始控制点
                    String ps[] = findPoints(i);
                    lPath.cubicTo(lastPoint.x*scale,lastPoint.y*scale, Float.parseFloat(ps[0])*scale, Float.parseFloat(ps[1])*scale, Float.parseFloat(ps[2])*scale, Float.parseFloat(ps[3])*scale);
                    lastPoint.set(Float.parseFloat(ps[2]), Float.parseFloat(ps[3]));
                }
                break;
                case 'q':
                case 'Q': {//二次贝塞尔曲线
                    String ps[] = findPoints(i);
                    lastPoint.set(Float.parseFloat(ps[2]), Float.parseFloat(ps[3]));
                    lPath.quadTo(Float.parseFloat(ps[0])*scale, Float.parseFloat(ps[1])*scale, Float.parseFloat(ps[2])*scale, Float.parseFloat(ps[3])*scale);
                }
                break;
                case 't':
                case 'T': {//T命令会跟在Q后面使用，用Q的结束点做起始点
                    String ps[] = findPoints(i);
                    lPath.quadTo(lastPoint.x*scale,lastPoint.y*scale, Float.parseFloat(ps[0])*scale, Float.parseFloat(ps[1])*scale);
                    lastPoint.set(Float.parseFloat(ps[0]), Float.parseFloat(ps[1]));
                }
                break;
                case 'a':
                case 'A':{//画弧
                }
                break;
                case 'z':
                case 'Z': {//结束
                    lPath.close();
                }
                break;
            }
        }
        return lPath;
    }

    private String[] findPoints(int cmdIndexInPosition) {
        int cmdIndex = cmdPositions.get(cmdIndexInPosition);
        String pointString = svgPath.substring(cmdIndex + 1, cmdPositions.get(cmdIndexInPosition + 1));
        return pointString.split(",");
    }

    private void findCommand() {
        cmdPositions.clear();
        while (mIndex < svgPathLenght) {
            char c = svgPath.charAt(mIndex);
            if ('A' <= c && c <= 'Z') {
                cmdPositions.add(mIndex);
            }else if ('a' <= c && c <= 'z') {
                cmdPositions.add(mIndex);
            }
            ++mIndex;
        }
    }
}
