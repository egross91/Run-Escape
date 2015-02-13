package org.escaperun.game.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ConsolePanel extends JPanel {

    // we're using IBM's Codepage437 VGA font.. this is ONLY ascii
    public static final int CHAR_WIDTH = 9;
    public static final int CHAR_HEIGHT = 16;
    public static final Image[] FONT_GLYPHS = new BufferedImage[256];
    public static final Color DEFAULT_BACKGROUND = Color.BLACK;
    public static final Color DEFAULT_FOREGROUND = Color.WHITE;

    static {
        // load font glyphs:
        BufferedImage cp437 = null;
        try {
            cp437 = ImageIO.read(new File(System.getProperty("user.dir") + "/assets/codepage437.png"));
            for (int i = 0; i < FONT_GLYPHS.length; i++) {
                // i = 32*row+column (ignoring offsets)
                // we recover row and column
                int x = 8+(i%32)*CHAR_WIDTH; // column start at
                int y = 8+(i/32)*CHAR_HEIGHT; // width start at
                BufferedImage glyph = cp437.getSubimage(x, y, CHAR_WIDTH, CHAR_HEIGHT);

                FONT_GLYPHS[i] = filter(glyph);
                //ImageIO.write(glyph, "png", new File(System.getProperty("user.dir") + "/assets/" + (i) + ".png"));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage filter(BufferedImage img) {
        // makes black transparent
        BufferedImage retval = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int black = Color.BLACK.getRGB();
        for (int i = 0; i < CHAR_WIDTH; i++) {
            for (int j = 0; j < CHAR_HEIGHT; j++) {
                int rgb = img.getRGB(i, j);
                if (rgb == black) {
                    retval.setRGB(i, j, 0);
                } else {
                    retval.setRGB(i, j, Color.BLACK.getRGB());
                }
            }
        }
        return retval;
    }

    private int rows, columns;
    private char[][] grid; // characters to be drawn; '\u0000' is no draw
    private Color[][] background; // color of background of each character slot
    private Color[][] foreground; // color of each individual characters

    public ConsolePanel(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.grid = new char[rows][columns];
        this.background = new Color[rows][columns];
        this.foreground = new Color[rows][columns];
        setPreferredSize(new Dimension(CHAR_WIDTH * columns, CHAR_HEIGHT * rows));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                background[i][j] = DEFAULT_BACKGROUND;
                foreground[i][j] = DEFAULT_FOREGROUND;
            }
        }
    }

    public void clear() throws IllegalArgumentException {
        clear(0, rows-1, 0, columns-1);
    }

    public void clear(int row, int column) throws IllegalArgumentException {
        verifyBounds(row, column);
        grid[row][column] = '\u0000';
    }

    public void clear(int rowLow, int rowHigh, int columnLow, int columnHigh) throws IllegalArgumentException {
        for (int i = rowLow; i <= rowHigh; i++) {
            for (int j = columnLow; j <= columnHigh; j++) {
                clear(i, j);
            }
        }
    }

    public void setForegroundColor(Color color, int row, int column) throws IllegalArgumentException {
        verifyBounds(row, column);
        foreground[row][column] = color;
    }

    public void setBackgroundColor(Color color, int row, int column) throws IllegalArgumentException {
        verifyBounds(row, column);
        background[row][column] = color;
    }

    public void setChar(char ch, int row, int column) throws IllegalArgumentException {
        verifyBounds(row, column);
        grid[row][column] = ch;
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage offscreenImage = new BufferedImage(CHAR_WIDTH*columns, CHAR_HEIGHT*rows, BufferedImage.TYPE_INT_ARGB);
        Graphics2D offscreen = offscreenImage.createGraphics();
        offscreen.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        // draw to offscreen
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int x = j*CHAR_WIDTH;
                int y = i*CHAR_HEIGHT;

                /* draw background */
                offscreen.setColor(background[i][j]);
                offscreen.fillRect(x, y, CHAR_WIDTH, CHAR_HEIGHT);

                /* draw foreground */
                offscreen.setXORMode(new Color(foreground[i][j].getRGB()^background[i][j].getRGB()));
                offscreen.drawImage(FONT_GLYPHS[grid[i][j]], x, y, this);
                offscreen.setPaintMode();
            }
        }

        // paint offscreen image (double buffering)
        g.drawImage(offscreenImage, 0, 0, CHAR_WIDTH*columns, CHAR_HEIGHT*rows, this);
    }

    private void verifyBounds(int row, int column) throws IllegalArgumentException {
        if (row < 0 || row >= rows || column < 0 || column >= columns)
            throw new IllegalArgumentException("Invalid grid position (row=" + row + ", column=" + column + ").");
    }
}
