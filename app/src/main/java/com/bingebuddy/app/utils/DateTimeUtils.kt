package com.bingebuddy.app.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ParseAndFormatDate(dateString: String): String {

    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")

    val parsedDate = LocalDate.parse(dateString, inputFormatter)
    val formattedDate = parsedDate.format(outputFormatter)

    return formattedDate // Example: 27 Jan 2025
}