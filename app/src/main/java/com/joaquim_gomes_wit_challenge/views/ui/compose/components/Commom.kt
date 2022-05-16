package com.joaquim_gomes_wit_challenge.views.ui.compose.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun SetWeatherCardRowInfo(
    text: String,
    isTitle: Boolean,
    @DrawableRes resource: Int,
    textIconDescription: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {

        Icon(
            painter = painterResource(id = resource),
            contentDescription = textIconDescription,
            modifier = Modifier.padding(5.dp)
        )

        Text(
            modifier = Modifier.padding(10.dp),
            style = if (isTitle) {
                MaterialTheme.typography.h4
            } else {
                MaterialTheme.typography.body1
            },
            text = text,
            color = Color.Gray
        )
    }
}