package lv.rtu.mystudentid7574.tictactoe

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class GameActivity : AppCompatActivity() {

    private lateinit var manager: GameManager
    private lateinit var one: TextView
    private lateinit var two: TextView
    private lateinit var three: TextView
    private lateinit var four: TextView
    private lateinit var five: TextView
    private lateinit var six: TextView
    private lateinit var seven: TextView
    private lateinit var eight: TextView
    private lateinit var nine: TextView
    private lateinit var startNewGameButton: Button
    private lateinit var player1Points: TextView
    private lateinit var player2Points: TextView
    private lateinit var name1EditText: EditText
    private lateinit var name2EditText: EditText
    private lateinit var returnToStart : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        manager = GameManager()

        one = findViewById(R.id.one)
        two = findViewById(R.id.two)
        three = findViewById(R.id.three)
        four = findViewById(R.id.four)
        five = findViewById(R.id.five)
        six = findViewById(R.id.six)
        seven = findViewById(R.id.seven)
        eight = findViewById(R.id.eight)
        nine = findViewById(R.id.nine)
        startNewGameButton = findViewById(R.id.start_new_game_button)
        player1Points = findViewById(R.id.player_one_score)
        player2Points = findViewById(R.id.player_two_score)
        name1EditText = findViewById(R.id.editTextTextPersonName1)
        name2EditText = findViewById(R.id.editTextTextPersonName2)
        returnToStart = findViewById(R.id.return1)

        one.setOnClickListener { onBoxClicked(one, Position(0, 0)) }
        two.setOnClickListener { onBoxClicked(two, Position(0, 1)) }
        three.setOnClickListener { onBoxClicked(three, Position(0, 2)) }
        four.setOnClickListener { onBoxClicked(four, Position(1, 0)) }
        five.setOnClickListener { onBoxClicked(five, Position(1, 1)) }
        six.setOnClickListener { onBoxClicked(six, Position(1, 2)) }
        seven.setOnClickListener { onBoxClicked(seven, Position(2, 0)) }
        eight.setOnClickListener { onBoxClicked(eight, Position(2, 1)) }
        nine.setOnClickListener { onBoxClicked(nine, Position(2, 2)) }

        startNewGameButton.setOnClickListener {
            manager.reset()
            resetboxes()
        }
        updatePt()

        returnToStart.setOnClickListener{
            val intent2 = Intent(GameActivity@this, MainActivity::class.java)
            startActivity(intent2)
        }
    }

    private fun resetboxes() {
        one.text = ""
        two.text = ""
        three.text = ""
        four.text = ""
        five.text = ""
        six.text = ""
        seven.text = ""
        eight.text = ""
        nine.text = ""
        one.background = null
        two.background = null
        three.background = null
        four.background = null
        five.background = null
        six.background = null
        seven.background = null
        eight.background = null
        nine.background = null
        one.isEnabled = true
        two.isEnabled = true
        three.isEnabled = true
        four.isEnabled = true
        five.isEnabled = true
        six.isEnabled = true
        seven.isEnabled = true
        eight.isEnabled = true
        nine.isEnabled = true
    }

    private fun disableBoxes() {
        one.isEnabled = false
        two.isEnabled = false
        three.isEnabled = false
        four.isEnabled = false
        five.isEnabled = false
        six.isEnabled = false
        seven.isEnabled = false
        eight.isEnabled = false
        nine.isEnabled = false
    }

    private fun updatePt() {
        player1Points.text = "Player 1 points: ${manager.player1Points}"
        player2Points.text = "Player 2 points: ${manager.player2Points}"
    }

    private fun onBoxClicked(box: TextView, position: Position) {
        if (box.text.isEmpty()) {
            box.text = manager.currentPlayerMark
            box.setBackgroundColor(Color.parseColor("#FFBB86FC"))
            val winLine = manager.makeMove(position)
            if (winLine != null) {
                updatePt()
                disableBoxes()
                startNewGameButton.visibility = View.VISIBLE
                showWinner(winLine)
                Toast.makeText(applicationContext, "Win!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showWinner(winLine: Win) {
        val (winBoxes, background) = when (winLine) {
            Win.ROW_0 -> Pair(listOf(one, two, three), R.drawable.horizontal)
            Win.ROW_1 -> Pair(listOf(four, five, six), R.drawable.horizontal)
            Win.ROW_2 -> Pair(listOf(seven, eight, nine), R.drawable.horizontal)
            Win.COLUMN_0 -> Pair(listOf(one, four, seven), R.drawable.vertical)
            Win.COLUMN_1 -> Pair(listOf(two, five, eight), R.drawable.vertical)
            Win.COLUMN_2 -> Pair(listOf(three, six, nine), R.drawable.vertical)
            Win.DIAGONAL_LEFT -> Pair(listOf(one, five, nine),
                R.drawable.left_diagonal
            )
            Win.DIAGONAL_RIGHT -> Pair(listOf(three, five, seven),
                R.drawable.right_diagonal
            )
        }

        winBoxes.forEach { box ->
            box.background = ContextCompat.getDrawable(GameActivity@ this, background)
        }
    }
}