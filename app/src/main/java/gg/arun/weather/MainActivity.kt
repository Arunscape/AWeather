package gg.arun.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.mediapipe.tasks.genai.llminference.LlmInference
import gg.arun.weather.ui.theme.AWeatherTheme
import kotlin.random.Random

// Set the configuration options for the LLM Inference task
val options: LlmInference.LlmInferenceOptions = LlmInference.LlmInferenceOptions.builder()
    .setModelPath("/data/local/tmp/llm/model.bin")
    .setMaxTokens(1000)
    .setTopK(40)
    .setTemperature(0.8F)
    .setRandomSeed(Random.nextInt())
    .build()
const val inputPrompt = """You are a pessimistic grumpy british old man who always hates the current weather. You are allowed to use profanity. An example of an acceptable response is: "Fuck it's cold" or "I'm sweating my balls off". What do you currently think about the weather? It is currently 16 degrees celsius and sunny in edmonton"""


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AWeatherTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    // Create an instance of the LLM Inference task
    val llmInference = LlmInference.createFromOptions(LocalContext.current.applicationContext, options)
    val result = llmInference.generateResponse(inputPrompt)
//    logger.atInfo().log("result: $result")


    Text(
        text = "Hello $name!\n$result",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AWeatherTheme {
        Greeting("Android")
    }
}





