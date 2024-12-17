package com.example.juicetracker.ui.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.juicetracker.R
import com.example.juicetracker.data.JuiceColor
import com.example.juicetracker.data.Product
import com.example.juicetracker.ui.AppViewModelProvider
import com.example.juicetracker.ui.JuiceTrackerViewModel
import java.util.Locale
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryBottomSheet(
    juiceTrackerViewModel: JuiceTrackerViewModel,
    sheetScaffoldState: BottomSheetScaffoldState,
    onCancel: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
    bottomSheetHide: () -> Unit,
    content: @Composable () -> Unit,
) {
    val juice by juiceTrackerViewModel.currentProductStream.collectAsState()

    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = sheetScaffoldState,
        sheetContent = {
            if (juiceTrackerViewModel.editButtonState.value) {
//                Edit Price Fun
                Column {
                    SheetHeader(
                        Modifier.padding(dimensionResource(R.dimen.padding_small)),
                        textHeader = "Edit Price"
                    )
                    SheetFormEditPrice(
                        juiceTrackerViewModel = juiceTrackerViewModel,
                        onCancel = onCancel,
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(R.dimen.padding_medium)
                        ),
                        sheetHide = { bottomSheetHide() }
                    )
                }
            } else {
//                Add Product fun
                Column {
                    SheetHeader(
                        Modifier.padding(dimensionResource(R.dimen.padding_small)),
                        textHeader = "Add Product"
                    )
                    SheetFormAddProduct(
                        product = juice,
                        onUpdateJuice = juiceTrackerViewModel::updateCurrentJuice,
                        onCancel = onCancel,
                        onSubmit = onSubmit,
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(R.dimen.padding_medium)
                        )
                    )
                }
            }
        }
    ) {
        content()
    }
}

@Composable
fun SheetHeader(
    modifier: Modifier = Modifier,
    textHeader: String
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
            text = textHeader,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )
        Divider()
    }
}

@Composable
fun SheetFormEditPrice(
    juiceTrackerViewModel: JuiceTrackerViewModel,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
    sheetHide: () -> Unit,
) {
    var subButtonEnabled by remember {
        mutableStateOf(false)
    }

    var minPrice by remember {
        mutableFloatStateOf(0.0F)
    }

    var maxPrice by remember {
        mutableFloatStateOf(0.0F)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Minimum Price field
        IntInputRow(
            inputLabel = "Minimum Price",
            fieldValue = minPrice.toString(),
            onValueChange = { minimumPrice ->
                if (minimumPrice.toFloat() <= 99999) {
                    minPrice = minimumPrice.toFloat()
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Maximum Price field
        LastIntInputRow(
            inputLabel = "Maximum Price",
            fieldValue = maxPrice.toString(),
            onValueChange = { maximumPrice ->
                if (maximumPrice.toFloat() <= 99999) {
                    maxPrice = maximumPrice.toFloat()
                }

            },
            modifier = Modifier.fillMaxWidth()
        )
//      logic for making sure minPrice < maxPrice
        subButtonEnabled = minPrice < maxPrice

        ButtonRow(
            modifier = Modifier
                .align(Alignment.End)
                .padding(bottom = dimensionResource(R.dimen.padding_medium)),
            onCancel = onCancel,
            onSubmit = {
                juiceTrackerViewModel.editPrice(minPrice, maxPrice)
                sheetHide()
           },
            submitButtonEnabled = subButtonEnabled
        )

    }
}

@Composable
fun SheetFormAddProduct(
    product: Product,
    onUpdateJuice: (Product) -> Unit,
    onCancel: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var subButtonEnabled by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        // Product Name field
        TextInputRow(
            inputLabel = stringResource(R.string.product_name),
            fieldValue = product.name,
            onValueChange = { name -> onUpdateJuice(product.copy(name = name)) },
            modifier = Modifier.fillMaxWidth(),
            placeholderText = "Product Name"
        )

        // Minimum Price field
        IntInputRow(
            inputLabel = "Minimum Price",
            fieldValue = product.minPrice.toString(),
            onValueChange = { minPrice ->
                if (minPrice.toFloat() <= 99999) {
                    onUpdateJuice(product.copy(minPrice = minPrice.toFloat()))
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Maximum Price field
        IntInputRow(
            inputLabel = "Maximum Price",
            fieldValue = product.maxPrice.toString(),
            onValueChange = { maxPrice ->
                if (maxPrice.toFloat() <= 99999) {
                    onUpdateJuice(product.copy(maxPrice = maxPrice.toFloat()))
                }

            },
            modifier = Modifier.fillMaxWidth()
        )
        // Keyword field
        LastTextInputRow(
            inputLabel = "Keyword",
            fieldValue = product.keyword,
            onValueChange = { keyword -> onUpdateJuice(product.copy(keyword = keyword)) },
            modifier = Modifier.fillMaxWidth(),
            placeholderText = "Keyword"
        )
        
        Text(text = "Note: Price will be based on your keyword. \n" +
                "To get price info, use Google Finance to find the ticker symbol of the company. Example: AAPL = Apple Inc")

//      logic for making sure minPrice < maxPrice
        subButtonEnabled = product.minPrice!! < product.maxPrice!!
                && product.name.isNotEmpty()

        ButtonRow(
            modifier = Modifier
                .align(Alignment.End)
                .padding(bottom = dimensionResource(R.dimen.padding_medium)),
            onCancel = onCancel,
            onSubmit = onSubmit,
            submitButtonEnabled = subButtonEnabled
        )

    }
}

@Composable
fun ButtonRow(
    onCancel: () -> Unit,
    onSubmit: () -> Unit,
    submitButtonEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        OutlinedButton(
            onClick = onCancel,
            border = null
        ) {
            Text(stringResource(android.R.string.cancel).uppercase(Locale.getDefault()))
        }
        Button(
            onClick = onSubmit,
            enabled = submitButtonEnabled
        ) {
            Text(stringResource(R.string.save).uppercase(Locale.getDefault()))
        }
    }
}

@Composable
fun TextInputRow(
    inputLabel: String,
    fieldValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String
) {
    InputRow(inputLabel, modifier) {
        TextField(
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small)),
            value = fieldValue,
            onValueChange = onValueChange,
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorScheme.surface,
                unfocusedContainerColor = colorScheme.surface,
                disabledContainerColor = colorScheme.surface,
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            placeholder = { Text(text = placeholderText)}
        )
    }
}

@Composable
fun LastTextInputRow(
    inputLabel: String,
    fieldValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String
) {
    InputRow(inputLabel, modifier) {
        TextField(
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small)),
            value = fieldValue,
            onValueChange = onValueChange,
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorScheme.surface,
                unfocusedContainerColor = colorScheme.surface,
                disabledContainerColor = colorScheme.surface,
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            placeholder = { Text(text = placeholderText)}
        )
    }
}

@Composable
fun IntInputRow(
    inputLabel: String,
    fieldValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    InputRow(inputLabel, modifier) {
        TextField(
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small)),
            value = fieldValue,
            onValueChange = onValueChange,
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorScheme.surface,
                unfocusedContainerColor = colorScheme.surface,
                disabledContainerColor = colorScheme.surface,
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Decimal
            )
        )
    }
}

@Composable
fun LastIntInputRow(
    inputLabel: String,
    fieldValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    InputRow(inputLabel, modifier) {
        TextField(
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small)),
            value = fieldValue,
            onValueChange = onValueChange,
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorScheme.surface,
                unfocusedContainerColor = colorScheme.surface,
                disabledContainerColor = colorScheme.surface,
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Decimal
            )
        )
    }
}

@Composable
fun InputRow(
    inputLabel: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = inputLabel,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier.weight(1f)
        )
        Box(modifier = Modifier.weight(2f)) {
            content()
        }
    }
}