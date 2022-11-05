package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.lang.StringBuilder
import kotlin.random.Random

data class WheelOfFortuneUiState(
    val wheelImage : Int = R.drawable.wheel_1000,
    val wheelResult : String = "",
    val playerBalance : Int = 0,
    val spinnable : Boolean = true,
    var wordProgress: String = "____",
    val lives: Int = 5,
    val started: Boolean = false,
    val won: Boolean = false,
    val lost: Boolean= false,
    val wordDrawn: String = "hi",
    val categoryDrawn: String = "hi"
)

class WheelOfFortuneViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(WheelOfFortuneUiState())
    val uiState = _uiState.asStateFlow()
var imageResource: Int = 0
    var result: Int = 0
    fun spinWheel() {
        result = (1..6).random();
        imageResource=when(result){
            1->R.drawable.wheel_200
            2->R.drawable.wheel_100
            3-> R.drawable.wheel_400
            4->R.drawable.wheel_1000
            5->R.drawable.wheel_500
            else -> R.drawable.wheel_bankrupt
        }
        val displayText: String = when(result){
            1->  "$200"
            2->  "$100"
            3-> "$400"
            4-> "$1000"
            5-> "$500"
            else-> "Bankrupt"
        }
        _uiState.update {
            it.copy(
                wheelImage = imageResource,
                wheelResult = displayText,
                playerBalance = 0,
                spinnable = false
            )
        }
    }
    var wordDrawn: String = ""
    var wordProgress: java.lang.StringBuilder = StringBuilder("")
    fun DrawWord(){
        val started: Boolean = true
        val Index: Int = Random.nextInt(0, Words.places.size)
        val category: String
        if(Index<=16){
            category="places"
        }
        else {
            category="names"
        }
       wordDrawn = Words.places[Index]
        for(i in 1..wordDrawn.length){
            wordProgress.append("_ ")}

        _uiState.update { it.copy(started=started,
            wordDrawn = wordDrawn,
            categoryDrawn = category,
            wordProgress = wordProgress.toString()) }
    }
    var playerBalance: Int = 0
    fun UpdateBalance() : Int{
        var increment = when(result){
            1->200
            2->100
            3->400
            4->1000
            5->500
            else->0
        }
if(increment!=0){
    playerBalance=playerBalance+increment
}
        return playerBalance
    }
    var lives: Int =5
    fun CheckWord(guess: String){
        if (guess.equals(wordDrawn)){
            val  wordProgress = wordDrawn
            _uiState.update{it.copy(wordProgress = wordProgress)}
            CheckWin()
        }
    else {
        lives=lives-1
            CheckLose()
    }
    }
    var won: Boolean=false
    fun CheckWin(){
if(wordProgress.toString()==wordDrawn){
won=true
    _uiState.update { it.copy(won=won) }
}
        }
    var lost: Boolean=false
    fun CheckLose(){
       if(lives<=0){
           lost=true
           _uiState.update { it.copy(lost=lost) }
       }
    }
}
