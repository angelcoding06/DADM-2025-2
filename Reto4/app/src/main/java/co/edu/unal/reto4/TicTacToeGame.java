package co.edu.unal.reto4;

import java.util.Random;

public class TicTacToeGame {
    public static final char HUMAN_PLAYER = 'X';
    public static final char COMPUTER_PLAYER = 'O';
    public static final char OPEN_SPOT = ' ';
    public static final int BOARD_SIZE = 9;

    private char[] mBoard;
    private Random mRand;

    public enum DifficultyLevel { Easy, Harder, Expert }
    private DifficultyLevel mDifficultyLevel = DifficultyLevel.Expert;

    public TicTacToeGame() {
        mBoard = new char[BOARD_SIZE];
        mRand = new Random();
    }

    public void clearBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            mBoard[i] = OPEN_SPOT;
        }
    }

    public void setMove(char player, int location) {
        if (mBoard[location] == OPEN_SPOT) {
            mBoard[location] = player;
        }
    }

    public DifficultyLevel getDifficultyLevel() {
        return mDifficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        mDifficultyLevel = difficultyLevel;
    }

    public int getComputerMove() {
        int move = -1;

        if (mDifficultyLevel == DifficultyLevel.Easy) {
            move = getRandomMove();
        } else if (mDifficultyLevel == DifficultyLevel.Harder) {
            move = getWinningMove();
            if (move == -1) move = getRandomMove();
        } else if (mDifficultyLevel == DifficultyLevel.Expert) {
            move = getWinningMove();
            if (move == -1) move = getBlockingMove();
            if (move == -1) move = getRandomMove();
        }

        return move;
    }

    private int getRandomMove() {
        int move;
        do {
            move = mRand.nextInt(BOARD_SIZE);
        } while (mBoard[move] != OPEN_SPOT);
        return move;
    }

    private int getWinningMove() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mBoard[i] == OPEN_SPOT) {
                mBoard[i] = COMPUTER_PLAYER;
                if (checkForWinner() == 3) {
                    mBoard[i] = OPEN_SPOT;
                    return i;
                }
                mBoard[i] = OPEN_SPOT;
            }
        }
        return -1;
    }

    private int getBlockingMove() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mBoard[i] == OPEN_SPOT) {
                mBoard[i] = HUMAN_PLAYER;
                if (checkForWinner() == 2) {
                    mBoard[i] = OPEN_SPOT;
                    return i;
                }
                mBoard[i] = OPEN_SPOT;
            }
        }
        return -1;
    }

    // 0 = no winner, 1 = tie, 2 = human, 3 = computer
    public int checkForWinner() {
        for (int i = 0; i <= 6; i += 3) {
            if (mBoard[i] == mBoard[i+1] && mBoard[i+1] == mBoard[i+2] && mBoard[i] != OPEN_SPOT)
                return (mBoard[i] == HUMAN_PLAYER ? 2 : 3);
        }
        for (int i = 0; i < 3; i++) {
            if (mBoard[i] == mBoard[i+3] && mBoard[i+3] == mBoard[i+6] && mBoard[i] != OPEN_SPOT)
                return (mBoard[i] == HUMAN_PLAYER ? 2 : 3);
        }
        if ((mBoard[0] == mBoard[4] && mBoard[4] == mBoard[8] && mBoard[0] != OPEN_SPOT) ||
                (mBoard[2] == mBoard[4] && mBoard[4] == mBoard[6] && mBoard[2] != OPEN_SPOT)) {
            return (mBoard[4] == HUMAN_PLAYER ? 2 : 3);
        }
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mBoard[i] == OPEN_SPOT) return 0;
        }
        return 1;
    }
}
