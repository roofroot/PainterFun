package cn.yx.pfun

import android.Manifest
import android.R
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore.Audio.Media
import android.util.Log
import android.view.ViewGroup
import androidx.activity.ComponentActivity

import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable


import androidx.compose.ui.tooling.preview.Preview
import cn.yx.pfun.ui.theme.PainterFunTheme


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}





@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun Greeting() {




}


@Preview(showBackground = false)
@Composable
fun DefaultPreview() {
    PainterFunTheme {
//        Greeting()
    }
}