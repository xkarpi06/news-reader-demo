package com.example.elongaassignmentapp.ui.component

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.elongaassignmentapp.ui.theme.AppTheme

@Composable
fun ShareButton(
    textToShare: String,
    context: Context,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, textToShare)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)

    Button(
        enabled = enabled,
        onClick = { startActivity(context, shareIntent, null) },
        modifier = modifier,
    ) {
        Icon(imageVector = Icons.Default.Share, contentDescription = null)
        Text("Share", modifier = Modifier.padding(start = 8.dp))
    }
}

@Preview
@Composable
private fun ComponentPreview() {
    AppTheme {
        ShareButton(textToShare = "", context = LocalContext.current)
    }
}
