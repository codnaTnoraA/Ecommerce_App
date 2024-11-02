package com.example.juicetracker.ui.homescreen

import android.annotation.SuppressLint
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.juicetracker.R
import com.example.juicetracker.data.Product
import com.example.juicetracker.ui.AppViewModelProvider
import com.example.juicetracker.ui.JuiceTrackerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("UnrememberedMutableState", "CoroutineCreationDuringComposition")
@Composable
fun JuiceTrackerList(
    juiceTrackerViewModel: JuiceTrackerViewModel,
    products: List<Product>,
    onDelete: (Product) -> Unit,
    onUpdate: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    juiceTrackerViewModel.getDate()
    val _day = juiceTrackerViewModel.day
    val day = _day.value

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = dimensionResource(R.dimen.padding_small)),
    ) {
        items(items = products) { product ->

            val _stockPrice: MutableState<String> = mutableStateOf("Loading Stock Price...")
//            TODO fix bug where when any action on Composable is started, stock resets
            rememberCoroutineScope().launch(Dispatchers.IO) {
                val _stock = juiceTrackerViewModel.getStockPrices(product.keyword).await()
                _stockPrice.value = _stock
                println("The function has executed")
                println("stockPrice(Mutable) = $_stockPrice")
            }

            JuiceTrackerListItem(
                product = product,
                stockPrice = _stockPrice,
                day = day,
                onDelete = onDelete,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onUpdate(product) }
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
    modifier: Modifier = Modifier,
    product: Product,
    stockPrice: MutableState<String>,
    day: String,
    juiceTrackerViewModel: JuiceTrackerViewModel  = viewModel(factory = AppViewModelProvider.Factory),
    onDelete: (Product) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        val productID = product.id

        Checkbox(
            checked = (product.checkState),
            onCheckedChange = {
                juiceTrackerViewModel.updateCheckState(it, productID)
            }
        )

        JuiceDetails(product, stockPrice, day, Modifier.weight(1f))
        DeleteButton(
            onDelete = {
                juiceTrackerViewModel.updateDeleteState(true, productID)
                onDelete(product)
            },
            modifier = Modifier.align(Alignment.Top)
        )
    }
}

@SuppressLint("UnrememberedMutableState", "CoroutineCreationDuringComposition")
@Composable
fun JuiceDetails(
    product: Product,
    stockPrice: MutableState<String>,
    day: String,
    modifier: Modifier = Modifier
) {
    Column(modifier, verticalArrangement = Arrangement.Top) {
        Text(
            text = product.name,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
        )
        Text("Minimum Price : ₱${product.minPrice.toString()} ")
        Text("Maximum Price : ₱${product.maxPrice.toString()} ")
        Text(text = "\n Market price as of $day:")
        Text(stockPrice.value,  fontWeight = FontWeight.Bold) // It uses data from yesterday as data is sourced from the US which is behind in time.
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