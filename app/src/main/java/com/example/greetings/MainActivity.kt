package com.example.greetings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.*
import java.util.Calendar
import kotlinx.coroutines.launch
import androidx.compose.ui.tooling.preview.Preview
import com.example.greetings.ui.theme.GreetingsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GreetingsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greetings()
                }
            }
        }
    }
}

@Composable
fun Greetings(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Column (modifier = modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter your name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        Button(onClick = {
            if (name.isNotEmpty()) {
                coroutineScope.launch {
                    val additionalGreeting = getAdditionalGreetingMassage()
                    snackbarHostState.showSnackbar("$additionalGreeting $name!")
                }
            }
        }
        ){
            Text(text = "Confirmed")
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.padding(top = 200.dp)
        )
    }
}

private fun getAdditionalGreetingMassage(): String{
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    return when (hour) {
        in 0..4 -> "It's the middle of the night. Sleep well!"
        in 5..11 -> "Good morning!"
        in 12..16 -> "Good afternoon!"
        in 17..20 -> "Good evening!"
        in  21..23 -> "Good night!"
        else -> "Hello!"
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GreetingsTheme {
        Greetings()
    }
}