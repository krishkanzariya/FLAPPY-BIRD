import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

class ImageLabel extends Label {

    Image img;

    public ImageLabel(String imagePath) {
        try {
            img = ImageIO.read(new File("C:\\Users\\krish\\OneDrive\\java\\projects\\WORK\\gameover.png"));
        } catch (IOException e) {
            System.out.println("Image could not be loaded.");
            e.printStackTrace();
        }
        this.setSize(250, 100);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (img != null) {
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

class ImageButton extends Button {

    Image img;

    public ImageButton(String imagePath) {
        try {
            img = ImageIO.read(new File("C:\\Users\\krish\\OneDrive\\java\\projects\\WORK\\restart.png"));
        } catch (IOException e) {
            System.out.println("Image could not be loaded.");
        }

        this.setSize(50, 50);
        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Image button clicked!");
            }
        });
    }

    public void paint(Graphics g) {
        if (img != null) {
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

class ImageButton1 extends Button {

    Image img;

    public ImageButton1(String imagePath) {
        try {
            img = ImageIO.read(new File("C:\\Users\\krish\\OneDrive\\java\\projects\\WORK\\pause.png"));
        } catch (IOException e) {
            System.out.println("Image could not be loaded.");
        }

        this.setSize(50, 50);
        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Image button clicked!");
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        if (img != null) {
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

class ImageButton2 extends Button {

    Image img;

    public ImageButton2(String imagePath) {
        try {
            img = ImageIO.read(new File("C:\\Users\\krish\\OneDrive\\java\\projects\\WORK\\menu.png"));
        } catch (IOException e) {
            System.out.println("Image could not be loaded.");
        }

        this.setSize(50, 50);
        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Image button clicked!");
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        if (img != null) {
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

public class Flappy extends Frame implements Runnable, ActionListener {

    long scoreIncrementTime = System.currentTimeMillis();
    int scoreIncrementInterval = 1000;
    int scoreIncrementAmount = 1;
    static Thread t;
    boolean isPaused = false;
    int score = 0;
    Label l;
    ImageButton rs;
    ImageButton1 pauseButton;
    ImageButton2 menu;
    static Flappy f;
    int i = 500;
    int j = 700;
    int m = 0;
    boolean spacePressed = false;
    boolean isRunning = false;
    Image backgroundImage;
    Image bird;
    Image cdhipipe;
    Image undhi;
    Image offScreenImage;
    Image gameoverr;
    Graphics offScreenGraphics;
    int pipew = 180;
    int pipeHeight = 500;
    Random rdm = new Random();
    int randomY = rdm.nextInt(300) + 100;
    boolean gameover = false;

    public Flappy() throws HeadlessException {
        setSize(800, 600);
        setTitle("test Bird");
        setVisible(true);
        setLayout(null);

        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\krish\\OneDrive\\java\\projects\\WORK\\flappy_bird_orignial.jpg"));  // Change to the path of your image
            bird = ImageIO.read(new File("C:\\Users\\krish\\OneDrive\\java\\projects\\WORK\\krish.png"));  // Change to the path of your image
            cdhipipe = ImageIO.read(new File("C:\\Users\\krish\\OneDrive\\java\\projects\\WORK\\pipe.png"));
            undhi = ImageIO.read(new File("C:\\Users\\krish\\OneDrive\\java\\projects\\WORK\\undhiipipe.png"));
            gameoverr = ImageIO.read(new File("C:\\Users\\krish\\OneDrive\\java\\projects\\WORK\\gameover.png"));
            // rst = ImageIO.read(new File("C:\\Users\\krish\\OneDrive\\java\\projects\\WORK\\flappyBirdPlayButton.png"));

        } catch (IOException e) {
            System.out.println("Image not found!");
            e.printStackTrace();
        }
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    spacePressed = true;
                    if (!isRunning) {
                        t.start();
                        isRunning = true;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    isPaused = !isPaused;
                    if (isPaused) {
                        System.out.println("Game Paused");
                    } else {
                        synchronized (t) {
                            t.notify();
                        }
                        System.out.println("Game Resumed");
                    }
                }
            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    spacePressed = false;
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        pauseButton = new ImageButton1("C:\\Users\\krish\\OneDrive\\java\\projects\\WORK\\pause.png");
        pauseButton.setBounds(725, 25, 50, 50);
        pauseButton.addActionListener(this);
        add(pauseButton);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pauseButton) {
            isPaused = !isPaused;
            if (isPaused) {
                pauseButton.setLabel("Resume");
            } else {
                pauseButton.setLabel("Pause");
                synchronized (t) {
                    t.notify();
                }
            }
        }
        if (e.getSource() == rs) {
            i = 500;
            j = 700;
            score = 0; 
            gameover = false;
            spacePressed = false;
            randomY = rdm.nextInt(300) + 100;
            remove(l);
            remove(rs);
            remove(menu);
            revalidate();
            repaint();
            this.requestFocus();
            if (t != null && t.isAlive()) {
                try {
                    t.interrupt();
                    t.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            t = new Thread(this);
            isRunning = true;
            t.start();

        } else if (e.getSource() == menu) {
            System.exit(0);
        }
    }

    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = createImage(this.getWidth(), this.getHeight());
            offScreenGraphics = offScreenImage.getGraphics();
        }
        offScreenGraphics.setColor(getBackground());
        offScreenGraphics.fillRect(0, 0, getWidth(), getHeight());
        paint(offScreenGraphics);
        g.drawImage(offScreenImage, 0, 0, this);
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        g.drawImage(bird, 350, i, 50, 50, this);
        g.drawImage(cdhipipe, j, randomY, pipew, pipeHeight, this);
        g.drawImage(undhi, j, 0, pipew, randomY - 200, this);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Score: " + score, 50, 100);
    }

    public void gameEnd() {
        int birdWidth = 50;
        int birdHeight = 50;
        int margin = 5;

        if (350 + birdWidth >= j + margin && 350 <= j + pipew - margin) {
            if (i < randomY - 200 + margin - 10 || i + birdHeight > randomY - margin + 10) {
                gameover = true;
                isRunning = false;
                showGameOverScreen();
            } else {
                score++;
            }
        }
        if (i > 550 || i < 30) {
            gameover = true;
            isRunning = false;
            showGameOverScreen();
        }
    }

    public void showGameOverScreen() {
        if (!gameover) {
            return;
        }
        if (l != null) {
            remove(l);
        }
        if (rs != null) {
            remove(rs);
        }
        if (menu != null) {
            remove(menu);
        }
        l = new ImageLabel("C:\\path\\to\\your\\game_over_image.png");
        l.setBounds(220, 180, 350, 100);
        rs = new ImageButton("flappyBirdPlayButton.png");
        rs.setBounds(225, 300, 50, 50);
        rs.addActionListener(this);
        menu = new ImageButton2("C:\\Users\\krish\\OneDrive\\java\\projects\\WORK\\menu.png");
        menu.setBounds(425, 300, 150, 50);
        menu.addActionListener(this);
        add(l);
        add(rs);
        add(menu);
        repaint();
        this.requestFocus();
    }

    public void run() {
        try {
            while (true && isRunning) {
                if (isPaused) {
                    synchronized (t) {
                        t.wait();
                    }
                }
                if (System.currentTimeMillis() - scoreIncrementTime >= scoreIncrementInterval) {
                    score += scoreIncrementAmount; // Increase score
                    scoreIncrementTime = System.currentTimeMillis();
                }

                if (spacePressed && i > 0) {
                    i -= 35;
                } else if (!spacePressed && i < 500) {
                    i += 25;
                }

                if (j <= 800 && j >= -200) {
                    j -= 15;
                } else {
                    randomY = rdm.nextInt(200) + 300;
                    j = 800;

                }

                gameEnd();
                repaint();

                Thread.sleep(50);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        f = new Flappy();
        t = new Thread(f);
    }
}
