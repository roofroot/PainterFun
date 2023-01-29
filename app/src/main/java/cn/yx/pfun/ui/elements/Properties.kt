package cn.yx.pfun.ui.elements

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.Dp
import cn.yx.pfun.data.EditPageElement
import cn.yx.pfun.data.PairColors
import cn.yx.pfun.data.PanelSize
import cn.yx.pfun.data.TagData
import cn.yx.pfun.data.TipsEntity
import java.util.LinkedList


//窗口
lateinit var list: List<TagData>
lateinit var selectedPos: MutableState<Int>
var selectPicResult: MutableState<Int>? = null
lateinit var currentEditThemeColor: PairColors

//编辑
lateinit var scrollY: ScrollState
lateinit var scrollX: ScrollState
var editPageData = LinkedList<EditPageElement>()
var tipList = LinkedList<TipsEntity>()
lateinit var editSplash: MutableState<Boolean>
lateinit var isShowTips:MutableState<Boolean>

//重置窗口大小
val maxPreviewH = 200f
val maxPreviewW = 200f
val maxRealPx = 20000f
lateinit var previewHeight: MutableState<Dp>
lateinit var previewWidth: MutableState<Dp>
lateinit var resizePanelIsExpanded: MutableState<Boolean>
var resizePanelSize = PanelSize(panelSize.w, panelSize.h)