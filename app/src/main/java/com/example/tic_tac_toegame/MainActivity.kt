package com.example.tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tic_tac_toegame.R

class MainActivity : AppCompatActivity() {

    private lateinit var buttons: Array<Array<Button>>
    private lateinit var statusText: TextView
    private lateinit var resetButton: Button

    private var isPlayerX = true
    private var board = Array(3) { Array(3) { "" } }
    private var gameActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusText = findViewById(R.id.statusText)
        resetButton = findViewById(R.id.resetButton)

        buttons = arrayOf(
            arrayOf(findViewById(R.id.button00), findViewById(R.id.button01), findViewById(R.id.button02)),
            arrayOf(findViewById(R.id.button10), findViewById(R.id.button11), findViewById(R.id.button12)),
            arrayOf(findViewById(R.id.button20), findViewById(R.id.button21), findViewById(R.id.button22))
        )

        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].setOnClickListener {
                    if (gameActive && buttons[i][j].text == "") {
                        buttons[i][j].text = if (isPlayerX) "X" else "O"
                        board[i][j] = if (isPlayerX) "X" else "O"
                        if (checkWinner()) {
                            statusText.text = "Player ${if (isPlayerX) "X" else "O"} Wins!"
                            gameActive = false
                        } else if (isDraw()) {
                            statusText.text = "It's a Draw!"
                            gameActive = false
                        } else {
                            isPlayerX = !isPlayerX
                            statusText.text = "Player ${if (isPlayerX) "X" else "O"}'s Turn"
                        }
                    }
                }
            }
        }

        resetButton.setOnClickListener {
            resetGame()
        }
    }

    private fun checkWinner(): Boolean {
        val lines = listOf(
            listOf(Pair(0, 0), Pair(0, 1), Pair(0, 2)), // rows
            listOf(Pair(1, 0), Pair(1, 1), Pair(1, 2)),
            listOf(Pair(2, 0), Pair(2, 1), Pair(2, 2)),
            listOf(Pair(0, 0), Pair(1, 0), Pair(2, 0)), // columns
            listOf(Pair(0, 1), Pair(1, 1), Pair(2, 1)),
            listOf(Pair(0, 2), Pair(1, 2), Pair(2, 2)),
            listOf(Pair(0, 0), Pair(1, 1), Pair(2, 2)), // diagonals
            listOf(Pair(0, 2), Pair(1, 1), Pair(2, 0))
        )

        for (line in lines) {
            val (a, b, c) = line
            val value = board[a.first][a.second]
            if (value != "" && value == board[b.first][b.second] && value == board[c.first][c.second]) {
                return true
            }
        }
        return false
    }

    private fun isDraw(): Boolean {
        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j] == "") return false
            }
        }
        return true
    }

    private fun resetGame() {
        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j] = ""
                buttons[i][j].text = ""
            }
        }
        isPlayerX = true
        gameActive = true
        statusText.text = "Player X's Turn"
    }
}
