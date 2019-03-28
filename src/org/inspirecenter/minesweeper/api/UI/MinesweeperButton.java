package org.inspirecenter.minesweeper.api.UI;

import org.inspirecenter.minesweeper.api.Model.CellState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MinesweeperButton extends JButton {

    public static final String RESOURCE_FOLDER = "Resources/";
    public static final ImageIcon MINE;
    public static final ImageIcon FLAG;
    public static final ImageIcon ONE;
    public static final ImageIcon TWO;
    public static final ImageIcon THREE;
    public static final ImageIcon FOUR;
    public static final ImageIcon FIVE;
    public static final ImageIcon SIX;
    public static final ImageIcon SEVEN;
    public static final ImageIcon EIGHT;

    public static final int ICON_SIZE = 64;

    static {
        MINE = new ImageIcon(RESOURCE_FOLDER + "mine.png");
        MINE.setImage(MINE.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));

        FLAG = new ImageIcon(RESOURCE_FOLDER + "flag.png");
        FLAG.setImage(FLAG.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));

        ONE = new ImageIcon(RESOURCE_FOLDER + "one.png");
        ONE.setImage(ONE.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));

        TWO = new ImageIcon(RESOURCE_FOLDER + "two.png");
        TWO.setImage(TWO.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));

        THREE = new ImageIcon(RESOURCE_FOLDER + "three.png");
        THREE.setImage(THREE.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));

        FOUR = new ImageIcon(RESOURCE_FOLDER + "four.png");
        FOUR.setImage(FOUR.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));

        FIVE = new ImageIcon(RESOURCE_FOLDER + "five.png");
        FIVE.setImage(FIVE.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));

        SIX = new ImageIcon(RESOURCE_FOLDER + "six.png");
        SIX.setImage(SIX.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));

        SEVEN = new ImageIcon(RESOURCE_FOLDER + "seven.png");
        SEVEN.setImage(SEVEN.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));

        EIGHT = new ImageIcon(RESOURCE_FOLDER + "eight.png");
        EIGHT.setImage(EIGHT.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));

    }

    private CellState cellState;

    public MinesweeperButton(CellState cellState) {

        this.cellState = cellState;
        switch (cellState.getRevealState()) {
            case COVERED:
                setIcon(null);
                break;
            case FLAGGED:
                setIcon(FLAG);
                setEnabled(false);
                break;
            case REVEALED_0:
                setIcon(null);
                setEnabled(false);
                break;
            case REVEALED_1:
                setIcon(ONE);
                setEnabled(false);
                break;
            case REVEALED_2:
                setIcon(TWO);
                setEnabled(false);
                break;
            case REVEALED_3:
                setIcon(THREE);
                setEnabled(false);
                break;
            case REVEALED_4:
                setIcon(FOUR);
                setEnabled(false);
                break;
            case REVEALED_5:
                setIcon(FIVE);
                setEnabled(false);
                break;
            case REVEALED_6:
                setIcon(SIX);
                setEnabled(false);
                break;
            case REVEALED_7:
                setIcon(SEVEN);
                setEnabled(false);
                break;
            case REVEALED_8:
                setIcon(EIGHT);
                setEnabled(false);
                break;
            case REVEALED_MINE:
                setIcon(MINE);
                setEnabled(false);
                break;
        }
    }

    public CellState getCellState() {
        return cellState;
    }

}
