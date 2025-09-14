package co.edu.unal.reto3;

import java.util.Random;

public class TicTacToeGame {

    public static final char HUMAN_PLAYER = 'X';
    public static final char COMPUTER_PLAYER = 'O';
    public static final char OPEN_SPOT = ' ';

    private char mBoard[];
    public static final int BOARD_SIZE = 9;

    private Random mRand;

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

    public int getComputerMove() {
        // Estrategia muy básica: elige un espacio vacío aleatorio
        int move;
        do {
            move = mRand.nextInt(BOARD_SIZE);
        } while (mBoard[move] != OPEN_SPOT);
        return move;
    }

    /**
     * @return 0 = sin ganador, 1 = empate, 2 = gana humano, 3 = gana computadora
     */
    public int checkForWinner() {
        // Combinaciones ganadoras
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

        // Empate
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER)
                return 0;
        }
        return 1;
    }
}
