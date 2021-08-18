@file:JvmName("CommonDropdownKt")
package net.accelf.contral.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MenuDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset

@Composable
fun Dropdown(
    items: List<String>,
    modifier: Modifier = Modifier,
    selected: String? = null,
    onChange: (String?) -> Unit = {},
) = Dropdown(
    items = items,
    itemLabel = { it },
    modifier = modifier,
    selected = selected,
    onChange = onChange,
)

@Composable
fun <T> Dropdown(
    items: List<T>,
    itemLabel: (T) -> String,
    modifier: Modifier = Modifier,
    selected: T? = null,
    onChange: (T?) -> Unit = {},
    itemRenderer: @Composable (T) -> Unit = { Text(text = itemLabel(it)) },
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = selected?.let(itemLabel) ?: "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            enabled = false,
            readOnly = true,
            placeholder = { Text(text = "click to select") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
                disabledLeadingIconColor = MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.IconOpacity),
                disabledTrailingIconColor = MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.IconOpacity),
                disabledLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
                disabledPlaceholderColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
            ),
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            // FIXME: default args with expect fun do not works
            focusable = true,
            modifier = Modifier,
            offset = DpOffset.Zero,
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onChange(item)
                        expanded = false
                    },
                    // FIXME: default args with expect fun do not works
                    modifier = Modifier,
                    enabled = true,
                    contentPadding = MenuDefaults.DropdownMenuItemContentPadding,
                    interactionSource = remember { MutableInteractionSource() },
                ) {
                    itemRenderer(item)
                }
            }
        }
    }
}

// FIXME: https://github.com/JetBrains/compose-jb/issues/762
@Composable
expect fun DropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    focusable: Boolean = true,
    modifier: Modifier = Modifier,
    offset: DpOffset = DpOffset.Zero,
    content: @Composable ColumnScope.() -> Unit,
)

@Composable
expect fun DropdownMenuItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = MenuDefaults.DropdownMenuItemContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
)
