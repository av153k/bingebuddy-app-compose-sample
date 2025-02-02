package com.bingebuddy.app.ui.components

import android.icu.text.DecimalFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RatingChip(voteAverage: Double?, voteCount: Long?) {


    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            Icons.Rounded.Star,
            contentDescription = "rating icon",
            tint = Color(0xffC99200),
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            if (voteAverage != 0.0)

                "${DecimalFormat("#.0").format(voteAverage)} (${voteCount})" else "Not rated",
            style = MaterialTheme.typography.labelMedium
        )
    }


}