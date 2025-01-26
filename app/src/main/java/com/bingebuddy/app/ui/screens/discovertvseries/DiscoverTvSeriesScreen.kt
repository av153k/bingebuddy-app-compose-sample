package com.bingebuddy.app.ui.screens.discovertvseries

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bingebuddy.app.ui.screens.discovertvseries.airingtodaytvseries.AiringTodayTvSeriesSection
import com.bingebuddy.app.ui.screens.discovertvseries.ontheairtvseries.OnTheAirTvSeriesSection
import com.bingebuddy.app.ui.screens.discovertvseries.populartvseries.PopularTvSeriesSection
import com.bingebuddy.app.ui.screens.discovertvseries.topratedtvseries.TopRatedTvSeriesSection

@Composable
fun DiscoverTvSeriesScreen(
    onTvSeriesClicked: (tvSeriesId: String) -> Unit,

    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.padding(10.dp)) {
        item {
            AiringTodayTvSeriesSection(onTvSeriesClicked = onTvSeriesClicked)
        }
        item {
            Spacer(Modifier.height(10.dp))
        }
        item {
            PopularTvSeriesSection(onTvSeriesClicked = onTvSeriesClicked)
        }
        item {
            Spacer(Modifier.height(10.dp))
        }
        item {
            TopRatedTvSeriesSection(onTvSeriesClicked = onTvSeriesClicked)
        }
        item {
            Spacer(Modifier.height(10.dp))
        }
        item {
            OnTheAirTvSeriesSection(onTvSeriesClicked = onTvSeriesClicked)
        }
        item {
            Spacer(Modifier.height(10.dp))
        }


    }
}