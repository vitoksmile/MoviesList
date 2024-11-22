@file:OptIn(ExperimentalLayoutApi::class)

package com.vitoksmile.movieslist.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vitoksmile.movieslist.R
import com.vitoksmile.movieslist.domain.models.ProductionCompany

@Composable
fun ProductionCompaniesView(
    modifier: Modifier = Modifier,
    companies: List<ProductionCompany>,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.production_companies),
            style = MaterialTheme.typography.bodySmall,
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            companies.forEach { company ->
                AsyncImage(
                    modifier = Modifier
                        .height(32.dp),
                    model = company.logoUrl,
                    contentScale = ContentScale.FillHeight,
                    contentDescription = company.name,
                )
            }
        }
    }
}
