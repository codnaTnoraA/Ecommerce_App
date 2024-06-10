/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.juicetracker.ui.homescreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.juicetracker.R
import com.example.juicetracker.data.Product

@Composable
fun JuiceTrackerList(
    products: List<Product>,
    onDelete: (Product) -> Unit,
    onUpdate: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = dimensionResource(R.dimen.padding_small)),
    ) {
        items(items = products) { juice ->
            JuiceTrackerListItem(
                product = juice,
                onDelete = onDelete,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onUpdate(juice) }
                    .padding(
                        vertical = dimensionResource(R.dimen.padding_small),
                        horizontal = dimensionResource(R.dimen.padding_medium)
                    )
            )
        }
    }
}

@Composable
fun JuiceTrackerListItem(
    product: Product,
    onDelete: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        var checkJuiceCheckedState = remember { mutableStateOf(false) }
        Checkbox(checked = checkJuiceCheckedState.value, onCheckedChange = {checkJuiceCheckedState.value = it})
        JuiceDetails(product, Modifier.weight(1f))
        DeleteButton(
            onDelete = {
                onDelete(product)
            },
            modifier = Modifier.align(Alignment.Top)
        )
    }
}

@Composable
fun JuiceDetails(product: Product, modifier: Modifier = Modifier) {
    Column(modifier, verticalArrangement = Arrangement.Top) {
        Text(
            text = product.name,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
        )
        Text(product.description)
//        Text(product.minPrice.toString())
    }
}

@Composable
fun DeleteButton(onDelete: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        onClick = { onDelete() },
    ) {
        Icon(
            modifier = modifier,
            painter = painterResource(R.drawable.delete_icon),
            contentDescription = stringResource(R.string.delete)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun JuiceTrackerListPreview() {
    MaterialTheme {
        JuiceTrackerList(
            products = listOf(
                Product(1, "Mango", "Yummy!", "Yellow", 1),
            ),
            onDelete = {},
            onUpdate = {})
    }
}