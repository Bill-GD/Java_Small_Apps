package simple_doodling;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SimpleDoodling extends Frame {
    static int width = 700, height = 600;

    boolean isDrawing = false, clearAll = false, changeColor = true;
    Image buffer = null;
    int mouseX, mouseY;
    int gap = 5;

    int r = 110, gr = 0, b = 0;

    SimpleDoodling() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
        addMouseWheelListener(new MouseAdapter() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                r = new Random().nextInt(256);
                gr = new Random().nextInt(256);
                b = new Random().nextInt(256);
                changeColor = true;
                repaint();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent event) {
                mouseX = event.getX();
                mouseY = event.getY();
                repaint();
            }

            public void mouseMoved(MouseEvent event) {
                mouseX = event.getX();
                mouseY = event.getY();
            }
        });
        addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                switch (e.getKeyChar()) {
                    case 'c':
                        clearAll = changeColor = true;
                        repaint();
                        break;
                    case 'q':
                        isDrawing = !isDrawing;
                        if (isDrawing)
                            setCursor(new Cursor(Cursor.HAND_CURSOR));
                        else
                            setCursor(Cursor.getDefaultCursor());
                        break;
                }
            }
        });
    }

    public void paint(Graphics g) {
        if (isDrawing) {
            g.setColor(new Color(r, gr, b));
            if (!changeColor)
                g.fillOval(mouseX - gap, mouseY - gap, gap * 2 + 1, gap * 2 + 1);
        }

        if (clearAll) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, 5000, 5000);
            clearAll = false;
        }
        if (changeColor) {
            g.setColor(new Color(r, gr, b));
            g.fillRect(20, 40, 25, 25);
            changeColor = false;
        }
        g.setColor(new Color(0, 0, 0));
        g.drawString("C: clear", 55, 47);
        g.drawString("Q: toggle drawing", 55, 63);
        g.drawString("Wheel: random color", 55, 79);
    }

    public void update(Graphics g) {
        paint(g);
    }

    public static void main(String[] args) {
        SimpleDoodling demo = new SimpleDoodling();
        demo.setSize(width, height);
        demo.setTitle("Image Demo");
        demo.setLocationRelativeTo(null);
        demo.setVisible(true);
    }
}
