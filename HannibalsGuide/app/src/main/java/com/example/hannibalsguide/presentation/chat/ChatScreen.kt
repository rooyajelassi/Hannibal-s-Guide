package com.example.hannibalsguide.presentation.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hannibalsguide.domain.model.ChatMessage

@Composable
fun ChatScreen(
    viewModel: ChatViewModel,
    landmarkId: String,
    onBack: () -> Unit
) {
    val messages by viewModel.messages.collectAsState()
    var input by remember { mutableStateOf("") }

    LaunchedEffect(landmarkId) { viewModel.init(landmarkId) }

    ChatScreenContent(
        messages = messages,
        input = input,
        onInputChange = { input = it },
        onSend = {
            if (input.isNotBlank()) {
                viewModel.sendMessage(input)
                input = ""
            }
        },
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreenContent(
    messages: List<ChatMessage>,
    input: String,
    onInputChange: (String) -> Unit,
    onSend: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat with Tarek") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Back") } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth().padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(messages) { msg ->
                    Card {
                        Text(
                            text = (if (msg.isUser) "You: " else "Tarek: ") + msg.text,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = input,
                    onValueChange = onInputChange,
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Ask about this place...") }
                )
                Button(onClick = onSend) { Text("Send") }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatScreenContentPreview() {
    ChatScreenContent(
        messages = listOf(
            ChatMessage("Tell me about El Jem", true),
            ChatMessage("El Jem is a Roman amphitheatre from the 3rd century AD.", false)
        ),
        input = "How old is it?",
        onInputChange = {},
        onSend = {},
        onBack = {}
    )
}