package com.example.navdialog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.navdialog.ui.theme.NavDialogTheme
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppScreen : NavKey {
    @Serializable
    data object Menu : AppScreen

    @Serializable
    data object VariantOne : AppScreen

    @Serializable
    data object VariantTwo : AppScreen
    @Serializable
    data object VariantThree : AppScreen
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavDialogTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val backStack = rememberNavBackStack(AppScreen.Menu)

    NavDisplay(
        backStack = backStack,
        onBack = { if (backStack.size > 1) backStack.removeLast() },
        entryProvider = entryProvider {
            entry<AppScreen.Menu> {
                MenuScreen(
                    onSelectVariantOne = { backStack.add(AppScreen.VariantOne) },
                    onSelectVariantTwo = { backStack.add(AppScreen.VariantTwo) },
                    onSelectVariantThree = { backStack.add(AppScreen.VariantThree) }
                )
            }
            entry<AppScreen.VariantOne> {
                VariantOneScreen(
                    onNavigateBack = { if (backStack.size > 1) backStack.removeLast() }
                )
            }
            entry<AppScreen.VariantTwo> {
                VariantTwoScreen(
                    onNavigateBack = { if (backStack.size > 1) backStack.removeLast() }
                )
            }
            entry<AppScreen.VariantThree> {
                VariantThreeScreen(
                    onNavigateBack = { if (backStack.size > 1) backStack.removeLast() }
                )
            }
        }
    )
}

@Composable
fun MenuScreen(
    onSelectVariantOne: () -> Unit,
    onSelectVariantTwo: () -> Unit,
    onSelectVariantThree: () -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Главное меню",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onSelectVariantOne,
                modifier = Modifier.width(200.dp)
            ) {
                Text(text = "Вариант 1 (лаг)")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onSelectVariantTwo,
                modifier = Modifier.width(200.dp)
            ) {
                Text(text = "Вариант 2")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onSelectVariantThree,
                modifier = Modifier.width(200.dp)
            ) {
                Text(text = "Вариант 3 (кастом)")
            }
        }
    }
}

@Composable
fun VariantOneScreen(onNavigateBack: () -> Unit) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AgreementAlertDialog(onNavigateBack)
        }
    }
}

@Composable
fun VariantTwoScreen(onNavigateBack: () -> Unit) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AgreementAlertDialogWithState(onNavigateBack)
        }
    }
}

@Composable
fun VariantThreeScreen(onNavigateBack: () -> Unit) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomAgreementDialog(onNavigateBack)
        }
    }
}