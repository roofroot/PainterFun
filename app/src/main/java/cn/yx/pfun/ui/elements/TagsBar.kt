package cn.yx.pfun.widget
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cn.yx.pfun.SCREEN_DENSITY
import cn.yx.pfun.data.PairColors
import cn.yx.pfun.data.TagData
import cn.yx.pfun.ui.elements.list
import cn.yx.pfun.ui.elements.selectedPos
import cn.yx.pfun.ui.theme.TagBackgroundShape
import java.util.Random

@Composable
fun tabsBar(
    list:List<TagData>
) {
    Row(Modifier.horizontalScroll(rememberScrollState(0))) {
        list.forEachIndexed { index, it ->
            tagsView(data = it,index)
        }
    }
}
@Composable
fun tagsView(data: TagData, pos: Int) {
    Text(
        text = data.title, modifier = Modifier
            .background(data.pairColors.colorBg, shape = TagBackgroundShape(30f * SCREEN_DENSITY))
            .width(70.dp)
            .height(height = data.height.value)
            .clickable {
                selectedPos.value = pos
                list.forEachIndexed { index, tagData ->

                    if (pos == index) {
                        list[index].height.value = 70.dp
                    } else {
                        list[index].height.value = 50.dp
                    }
                }
            }, color = data.pairColors.colorFg
    )
}

fun getRandomColor(): PairColors {
    var random = Random()
    var b = random.nextInt(255)
    var r = random.nextInt(155) + 100
    var g = random.nextInt(155) + 100
    return PairColors(Color(255 - r, 255 - g, 255 - b), Color(r, g, b));
}
