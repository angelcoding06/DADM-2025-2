package co.edu.unal.reto4;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Random;

public class AndroidTicTacToeActivity extends AppCompatActivity {

    private TicTacToeGame mGame;
    private Button[] mBoardButtons;
    private TextView mInfoTextView;
    private Button resetButton;
    private boolean gameOver;
    private Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mBoardButtons = new Button[9];
        mBoardButtons[0] = findViewById(R.id.one);
        mBoardButtons[1] = findViewById(R.id.two);
        mBoardButtons[2] = findViewById(R.id.three);
        mBoardButtons[3] = findViewById(R.id.four);
        mBoardButtons[4] = findViewById(R.id.five);
        mBoardButtons[5] = findViewById(R.id.six);
        mBoardButtons[6] = findViewById(R.id.seven);
        mBoardButtons[7] = findViewById(R.id.eight);
        mBoardButtons[8] = findViewById(R.id.nine);

        mInfoTextView = findViewById(R.id.information);
        resetButton = findViewById(R.id.resetButton);

        mGame = new TicTacToeGame();
        rand = new Random();

        resetButton.setOnClickListener(v -> startNewGame());

        startNewGame();
    }

    private void startNewGame() {
        mGame.clearBoard();
        gameOver = false;

        for (int i = 0; i < mBoardButtons.length; i++) {
            int finalI = i;
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(view -> {
                if (gameOver) return;

                setMove(TicTacToeGame.HUMAN_PLAYER, finalI);
                checkGameState();
            });
        }

        // 🔹 Aleatorio: ¿quién empieza?
        if (rand.nextBoolean()) {
            int compMove = mGame.getComputerMove();
            setMove(TicTacToeGame.COMPUTER_PLAYER, compMove);
            mInfoTextView.setText("Your turn.");
        } else {
            mInfoTextView.setText("You go first.");
        }
    }

    private void setMove(char player, int location) {
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));

        // Colores distintos para X y O
        if (player == TicTacToeGame.HUMAN_PLAYER) {
            mBoardButtons[location].setTextColor(Color.parseColor("#81C784")); // Verde claro para X
        } else {
            mBoardButtons[location].setTextColor(Color.parseColor("#FFEB3B")); // Amarillo verdoso para O
        }
    }

    private void checkGameState() {
        int winner = mGame.checkForWinner();
        if (winner == 0) {
            int compMove = mGame.getComputerMove();
            setMove(TicTacToeGame.COMPUTER_PLAYER, compMove);
            winner = mGame.checkForWinner();
        }

        if (winner == 0) {
            mInfoTextView.setText("Your turn.");
        } else if (winner == 1) {
            mInfoTextView.setText("It's a tie!");
            gameOver = true;
            disableBoard();
        } else if (winner == 2) {
            mInfoTextView.setText("You won!");
            gameOver = true;
            disableBoard();
        } else if (winner == 3) {
            mInfoTextView.setText("Android won!");
            gameOver = true;
            disableBoard();
        }
    }

    private void disableBoard() {
        for (Button b : mBoardButtons) {
            b.setEnabled(false);
        }
    }

    // Menú
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.new_game) {
            startNewGame();
            return true;
        } else if (id == R.id.ai_difficulty) {
            showDifficultyDialog();
            return true;
        } else if (id == R.id.quit) {
            showQuitDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDifficultyDialog() {
        final CharSequence[] levels = {"Easy", "Harder", "Expert"};
        int selected = mGame.getDifficultyLevel().ordinal();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose difficulty");
        builder.setSingleChoiceItems(levels, selected, (dialog, which) -> {
            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.values()[which]);
            Toast.makeText(getApplicationContext(),
                    "Difficulty: " + levels[which],
                    Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        builder.show();
    }

    private void showQuitDialog() {
        new AlertDialog.Builder(this)
                .setMessage("Do you really want to quit?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> finish())
                .setNegativeButton("No", null)
                .show();
    }
}
