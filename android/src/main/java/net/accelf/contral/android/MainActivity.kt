package net.accelf.contral.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import com.arkivanov.decompose.defaultComponentContext
import net.accelf.contral.android.config.AndroidConfigurationProvider
import net.accelf.contral.core.Main

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Main(
                ctx = defaultComponentContext(),
                configurationProvider = AndroidConfigurationProvider(LocalContext.current),
            )
        }
    }
}
