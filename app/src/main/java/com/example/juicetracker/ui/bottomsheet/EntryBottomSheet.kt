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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.juicetracker.R
import com.example.juicetracker.data.JuiceColor
import com.example.juicetracker.data.Product
import com.example.juicetracker.ui.JuiceTrackerViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryBottomSheet(
    juiceTrackerViewModel: JuiceTrackerViewModel,
    sheetScaffoldState: BottomSheetScaffoldState,
    onCancel: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val juice by juiceTrackerViewModel.currentProductStream.collectAsState()

    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = sheetScaffoldState,
        sheetContent = {
            Column {
                SheetHeader(Modifier.padding(dimensionResource(R.dimen.padding_small)))
                SheetForm(
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
    ) {
        content()
    }
}

@Composable
fun SheetHeader(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
            text = stringResource(R.string.bottom_sheet_headline),
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )
        Divider()
    }
}

@Composable
fun SheetForm(
    product: Product,
    onUpdateJuice: (Product) -> Unit,
    onCancel: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
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
            onValueChange = { minPrice -> onUpdateJuice(product.copy(minPrice = minPrice.toFloat())) },
            modifier = Modifier.fillMaxWidth()
        )

        // Maximum Price field
        IntInputRow(
            inputLabel = "Maximum Price",
            fieldValue = product.maxPrice.toString(),
            onValueChange = { maxPrice -> onUpdateJuice(product.copy(maxPrice = maxPrice.toFloat())) },
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
        
        Text(text = "Note: Price will be based on your keyword")
        
        ButtonRow(
            modifier = Modifier
                .align(Alignment.End)
                .padding(bottom = dimensionResource(R.dimen.padding_medium)),
            onCancel = onCancel,
            onSubmit = onSubmit,
            submitButtonEnabled = product.name.isNotEmpty()
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

private fun findColorIndex(color: String): Int {
    val juiceColor = JuiceColor.valueOf(color)
    return JuiceColor.entries.indexOf(juiceColor)
}