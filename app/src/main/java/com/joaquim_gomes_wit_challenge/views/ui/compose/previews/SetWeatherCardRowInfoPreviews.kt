package com.joaquim_gomes_wit_challenge.views.ui.compose.previews

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.joaquim_gomes_wit_challenge.R
import com.joaquim_gomes_wit_challenge.views.ui.compose.components.SetWeatherCardRowInfo

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
fun setCardRowInfo_Light_Preview_No_Title() {
    SetWeatherCardRowInfo(
        text = "String test text",
        resource = R.drawable.ic_baseline_info_24,
        isTitle = false,
        textIconDescription = "String description text"
    )
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
fun setCardRowInfo_Light_Preview_Title() {
    SetWeatherCardRowInfo(
        text = "String test text",
        resource = R.drawable.ic_baseline_info_24,
        isTitle = true,
        textIconDescription = "String description text"
    )
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun setCardRowInfo_Night_Preview_No_Title() {
    SetWeatherCardRowInfo(
        text = "String test text",
        resource = R.drawable.ic_baseline_info_24,
        isTitle = false,
        textIconDescription = "String description text"
    )
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun setCardRowInfo_Night_Preview_Title() {
    SetWeatherCardRowInfo(
        text = "String test text",
        resource = R.drawable.ic_baseline_info_24,
        isTitle = true,
        textIconDescription = "String description text"
    )
}