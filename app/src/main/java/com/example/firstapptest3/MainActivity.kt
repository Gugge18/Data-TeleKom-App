package com.example.firstapptest3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.firstapptest3.ui.theme.FirstAppTest3Theme
val questions = listOf(
    Question(
        questionText = "Använder IPV6 sig av en Checksum?",
        options = listOf("Ja", "Nej"),
        correctAnswer = "Nej"
    ),
    Question(
        questionText = "Vilket frekvensutrymme är störst på en ADSL ledning?",
        options = listOf("Uppströms data", "Tal", "Nedströms data", "Lika Stora"),
        correctAnswer = "Nedströms data"
    ),
    Question(
        questionText = "Vilket OSI lager använder sig av ramar'?",
        options = listOf(
            "Applikationslagret",
            "Presentationslagret ",
            "Sessionslagret",
            "Transportlagret",
            "Nätverkslagret",
            "Datalänklagret",
            "Fysiskalagret"),
        correctAnswer = "Datalänklagret"
    )
)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Lista med frågor och svar

        setContent {
            FirstAppTest3Theme {
                  FlashcardApp()
                }
            }
        }
    }

@Composable
fun FlashcardApp() {
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var isQuizStarted by remember { mutableStateOf(false) }

    // Kontrollera om quizet har startat
    if (!isQuizStarted) {
        StartScreen(onStartClicked = {
            isQuizStarted = true // När knappen trycks, starta quizet
            currentQuestionIndex = 0 // Återställ frågeindex
            score = 0 // Återställ poängen
        })
    } else {
        // Kontrollera om vi är klara med alla frågor
        if (currentQuestionIndex < questions.size) {
            FlashcardScreen(
                question = questions[currentQuestionIndex],
                onAnswerSelected = { selectedAnswer ->
                    if (selectedAnswer == questions[currentQuestionIndex].correctAnswer) {
                        score++  // Om rätt svar, öka poängen
                    }
                    currentQuestionIndex++  // Gå vidare till nästa fråga
                }
            )
        } else {
            // Om alla frågor är besvarade, visa poäng
            QuizResultScreen(score = score, totalQuestions = questions.size) {
                // När Reset Game-knappen trycks, återställ spelet
                currentQuestionIndex = 0 // Återställ frågeindex
                score = 0 // Återställ poängen
                isQuizStarted = false // Återställ till startskärmen
            }
        }
    }
}

@Composable
fun FlashcardScreen(question: Question, onAnswerSelected: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = question.questionText, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))
        // Visa svarsalternativen som knappar
        question.options.forEach { option ->
            Button(
                onClick = { onAnswerSelected(option) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(text = option)
            }
        }
    }
}
@Composable
fun QuizResultScreen(score: Int, totalQuestions: Int, onResetGame: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Quiz Completed!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "You scored $score out of $totalQuestions")
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onResetGame) {
            Text(text = "Reset Game")
        }
    }
}
@Composable
fun StartScreen(onStartClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to the Quiz!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onStartClicked) {
            Text(text = "Start Game")
        }
    }
}

data class Question(val questionText: String, val options: List<String>, val correctAnswer: String)
