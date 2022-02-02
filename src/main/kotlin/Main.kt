// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Preview
fun App() {

    val textFieldValue = remember { mutableStateOf(TextFieldValue("")) }

    var pgn: PGN? by remember { mutableStateOf(null) }

    MaterialTheme {
        Column {
            Text("Paste your PGN in here and hit \"View\":")
            Row {
                TextField(
                    textFieldValue.value,
                    onValueChange = { textFieldValue.value = it },
                    modifier = Modifier.size(200.dp)
                )
                Button(onClick = {
                    try {
                        pgn = PGN(textFieldValue.value.text)
                    }
                    catch (exception: Exception) {
                        println(exception.message)
                    }
                }) {
                    Text("View")
                }
            }

            pgn?.let { pgn ->
                pgn["Event"]?.let {
                    Text("Event: $it")
                }
                pgn["Site"]?.let {
                    Text("Site: $it")
                }
                pgn["Date"]?.let {
                    Text("Date: $it")
                }
                pgn["Round"]?.let {
                    Text("Round: $it")
                }
                pgn["White"]?.let {
                    Text("White: $it")
                }
                pgn["Black"]?.let {
                    Text("Black: $it")
                }
                pgn["Result"]?.let {
                    Text("Result: $it")
                }
                
                Text("Moves: ${pgn.moves}")
                
                pgn["Link"]?.let { 
                    Text("Link: $it")
                }
            }
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,

        ) {
        App()
    }
}
