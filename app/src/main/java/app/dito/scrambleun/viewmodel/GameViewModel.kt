package app.dito.scrambleun.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import app.dito.scrambleun.data.GameUiState
import app.dito.scrambleun.data.scrambleListWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {
    /*
    create _uiState MutableStateFlow with initial value GameUiState()
     */
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
    private lateinit var currentWord: String
    private var usedWord: MutableSet<String> = mutableSetOf()
    var userGuess by mutableStateOf("")
        private set

    private fun pickRandomWord(): String {
        currentWord = scrambleListWords().random()
        return if (usedWord.contains(currentWord)) {
            pickRandomWord()
        } else {
            usedWord.add(currentWord)
            shuffleWord(currentWord)
        }
    }

    private fun shuffleWord(word: String): String {
        val temp = word.toCharArray()
        temp.shuffle()
        while (String(temp) == word) {
            temp.shuffle()
        }
        return String(temp)
    }

    private fun resetGame() {
        usedWord.clear()
        _uiState.value = GameUiState(currentScrambleWord = pickRandomWord())
    }

    init {
        resetGame()
    }

    fun updateUserGuess(userGuessWord: String) {
        userGuess = userGuessWord
    }
}