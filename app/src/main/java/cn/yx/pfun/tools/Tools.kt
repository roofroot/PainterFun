package cn.yx.pfun.tools

import cn.yx.pfun.data.Point

fun getGCD(a: Int, b: Int): Int {
    val min = Math.min(a, b)
    for (i in min downTo 1) {
        if (a % i == 0 && b % i == 0) {
            return i
        }
    }
    return 1
}

fun getPicRectRBPoint(
    angle: Int,
    rotateRandians: Double,
    width: Float,
    height: Float,
    rx: Float,
    ry: Float
): Point {
    /**
     *  这段是计算右下角与中心点y轴距离
     *  分两种情况 一种情况 是temp等于这个距离
     *  另一种情况是temp小于这个距离
     */
    if (angle == 0) {

        return Point(0f, 0f)

    } else {

        var temp = (width / (Math.sin(rotateRandians) * 2)).toFloat()
        var tempb =
            (((height / 2 - Math.cos(rotateRandians) * temp)) * Math.cos(
                rotateRandians
            )).toFloat()

        if (rotateRandians < 30) {
            var xtemp =
                (width / (2 * Math.tan(rotateRandians)) - height / 2).toFloat()
            var temp = (width / (Math.sin(rotateRandians) * 2)).toFloat()
            return Point(
                (rx + xtemp * Math.sin(rotateRandians)).toFloat(),
                (ry + temp - xtemp * Math.cos(rotateRandians)).toFloat()
            )
        } else {
            if (temp * temp >= (width / 2 * width / 2 + height / 2 * height / 2)) {
                var tempc =
                    ((height / 2 - Math.cos(rotateRandians) * tempb) / Math.cos(
                        rotateRandians
                    )).toFloat()
                return Point(rx - tempc, ry + temp)
            } else {
                var tempc =
                    (((height / 2 - Math.cos(rotateRandians) * temp)) * Math.sin(
                        rotateRandians
                    )).toFloat()
                return Point(rx - tempc, ry + temp + tempb)
            }
        }
    }
}

fun fingerArcbyCenter(dragAmount: Point, center: Point): Double {
    var pointR =
        Math.sqrt((dragAmount.x * dragAmount.x + dragAmount.y * dragAmount.y).toDouble())//当前手指与图片中间点半径
    var xdistance = Math.abs(dragAmount.x - center.x)
    var ydistance = Math.abs(dragAmount.y - center.y)
    //2*Pi*R/360 * angel
    var offsetAngle = fingerAngleCenter(dragAmount, center) //反余弦函数求出弧度值
    return (Math.PI * offsetAngle) / 180
}

fun fingerAngleCenter(dragAmount: Point, center: Point): Int {
    var pointR =
        Math.sqrt((dragAmount.x * dragAmount.x + dragAmount.y * dragAmount.y).toDouble())//当前手指与图片中间点半径
    var xdistance = Math.abs(dragAmount.x - center.x)
    var ydistance = Math.abs(dragAmount.y - center.y)
    var result = (Math.atan((ydistance / xdistance).toDouble()) * 180 / Math.PI).toInt() //反余弦函数求出角度
    if (dragAmount.x >= center.x && dragAmount.y <= center.y) {
        return result
    } else if (dragAmount.x < center.x && dragAmount.y <= center.y) {
        return 180 - result

    } else if (dragAmount.x < center.x && dragAmount.y > center.y) {
        return 180 + result
    } else if (dragAmount.x >= center.x && dragAmount.y >= center.y) {
        return 360 - result
    }
    return result
}