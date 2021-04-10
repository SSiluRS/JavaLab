import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.Scanner;
import java.util.Timer;

public class Lab1 {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "Kirya3010t0";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/lab7";

    static public String[] getDBParams() {
        return new String[]{DB_URL, DB_USERNAME, DB_PASSWORD};
    }

    static JFrame jFrame = getFrame();
    static JPanel jPanel = new JPanel();
    static DrawClass drawPanel = new DrawClass();
    static JTextField bornSec1 = new JTextField("1000", 8);
    static JTextField lifeSec1 = new JTextField("1000", 8);
    static JTextField bornChance1 = new JTextField("30", 8);
    static JTextField bornSec2 = new JTextField("5000", 8);
    static JTextField lifeSec2 = new JTextField("1000", 8);
    static JTextField bornChance2 = new JTextField("50", 8);
    static JTextField antSpeed = new JTextField("1", 8);
    static JTextField workCount = new JTextField("0");
    static JTextField warCount = new JTextField("0");
    static JTextField timeElapsed = new JTextField("0");
    static JCheckBox isLab2 = new JCheckBox();
    static JCheckBox isMusic = new JCheckBox();
    static JRadioButton rDrag = new JRadioButton("Drag");
    static JRadioButton rStop = new JRadioButton("Stop");
    static JButton startBtn = new JButton("Start");
    static JButton stopBtn = new JButton("Stop");
    static ButtonGroup bg = new ButtonGroup();
    static JDialog optionDialog;
    static Timer tSize = new Timer();
    static boolean isTSizeOn = false;
    static boolean isHelp = false;
    static Moving antMoving = new Moving();
    private static int WHeight = 670;
    private static int WWidth = 1080;
    static boolean isMousePressed = false;
    static boolean isStart = false;
    static Ant clickedAnt;

    static ClientDialog clientDialog;
    static Client client;//=  new Client();
    static boolean isConnected = false;
    static double X, Y;

    public static void main(String[] args) {
        antMoving.start();

        //client.start();

        jFrame.add(jPanel);
        GridBagLayout gridBagLayout = new GridBagLayout();
        jPanel.setLayout(gridBagLayout);
        GridBagConstraints constraints = new GridBagConstraints();

        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new GridLayout(4, 1));
        jPanel1.add(new JLabel("Время появления рабочих:"));
        JPanel jp1 = new JPanel();
        jp1.add(bornSec1);
        bornSec1.setFont(new Font("Serif", Font.BOLD, 15));
        bornSec1.setForeground(Color.RED);
        bornSec1.setEditable(false);
        jPanel1.add(jp1);

        jPanel1.add(new JLabel("Шанс появления рабочих:"));
        JPanel jp2 = new JPanel();
        jp2.add(bornChance1);
        bornChance1.setFont(new Font("Serif", Font.BOLD, 15));
        bornChance1.setForeground(Color.RED);
        bornChance1.setEditable(false);
        jPanel1.add(jp2);

        JPanel jPanel2 = new JPanel();
        jPanel2.setLayout(new GridLayout(4, 1));
        jPanel2.add(new JLabel("Время появления военных:"));
        JPanel jp3 = new JPanel();
        jp3.add(bornSec2);
        bornSec2.setFont(new Font("Serif", Font.BOLD, 15));
        bornSec2.setForeground(Color.BLUE);
        bornSec2.setEditable(false);
        jPanel2.add(jp3);

        jPanel2.add(new JLabel("Шанс появления военных:"));
        JPanel jp4 = new JPanel();
        jp4.add(bornChance2);
        bornChance2.setFont(new Font("Serif", Font.BOLD, 15));
        bornChance2.setForeground(Color.BLUE);
        bornChance2.setEditable(false);
        jPanel2.add(jp4);

        JPanel infPanel = new JPanel();
        JPanel infoPan = new JPanel();
        infoPan.setLayout(new GridLayout(6, 1));
        infoPan.add(new JLabel("Количество рабочих муравьев:"));
        workCount.setEditable(false);
        workCount.setFont(new Font("Serif", Font.BOLD, 20));
        workCount.setForeground(Color.RED);
        infoPan.add(workCount);
        infoPan.add(new JLabel("Количество военных муравьев:"));
        warCount.setEditable(false);
        warCount.setFont(new Font("Serif", Font.BOLD, 20));
        warCount.setForeground(Color.GREEN);
        infoPan.add(warCount);
        infoPan.add(new JLabel("Пройденное время:"));
        timeElapsed.setEditable(false);
        timeElapsed.setFont(new Font("Serif", Font.BOLD, 20));
        timeElapsed.setForeground(Color.BLUE);
        infoPan.add(timeElapsed);
        infPanel.add(infoPan);
        infPanel.setVisible(false);

        JPanel paramPan = new JPanel();
        paramPan.setLayout(new BoxLayout(paramPan, 1));
        JPanel buttonsPan = new JPanel();
        buttonsPan.add(startBtn);
        buttonsPan.add(stopBtn);


        JPanel oneMorePanel = new JPanel();
        JLabel islab2Label = new JLabel("Lab1 or Lab2");
        islab2Label.setToolTipText("Выбор между первой и второй лабой");
        isLab2.setToolTipText("Выбор между первой и второй лабой");
        oneMorePanel.add(islab2Label);
        oneMorePanel.add(isLab2);
        isLab2.setSelected(false);
        JPanel oneMoreBoxPanel = new JPanel();
        oneMoreBoxPanel.setLayout(new BoxLayout(oneMoreBoxPanel, 1));
        oneMoreBoxPanel.add(buttonsPan);
        oneMoreBoxPanel.add(oneMorePanel);

        JPanel morePanel = new JPanel();
        JLabel isMusicLabel = new JLabel("Turn music");
        morePanel.add(isMusicLabel);
        morePanel.add(isMusic);
        isMusic.setSelected(false);
        oneMoreBoxPanel.add(morePanel);

        JPanel moreBoxPanel = new JPanel();
        moreBoxPanel.setLayout(new BoxLayout(moreBoxPanel, BoxLayout.X_AXIS));
        JButton helpBtn = new JButton("?");
        moreBoxPanel.add(Box.createHorizontalStrut(200));
        helpBtn.setPreferredSize(new Dimension(15, 15));
        moreBoxPanel.add(helpBtn);

        paramPan.add(moreBoxPanel);
        paramPan.add(Box.createVerticalStrut(10));
        paramPan.add(oneMoreBoxPanel);
        paramPan.add(Box.createVerticalStrut(10));
        paramPan.add(jPanel1);
        paramPan.add(Box.createVerticalStrut(20));
        paramPan.add(jPanel2);
        paramPan.add(Box.createVerticalStrut(10));
        paramPan.add(infPanel);

        constraints.weighty = 0;
        constraints.gridy = 1;
        constraints.gridx = 6;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        jPanel.add(paramPan, constraints);

        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.gridwidth = 6;
        constraints.gridheight = 6;
        constraints.fill = GridBagConstraints.BOTH;
        drawPanel.setBackground(Color.gray);
        jPanel.add(drawPanel, constraints);

        var jMenuBar = new JMenuBar();
        var jMoreMenu = new JMenu("More");
        jMenuBar.add(jMoreMenu);
        var moreOptionsMenu = new JMenuItem("More options");
        jMoreMenu.add(moreOptionsMenu);
        moreOptionsMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionDialog = new JDialog(jFrame, "Options");
                Dimension dimension = drawPanel.getSize();
                optionDialog.setBounds(dimension.width / 2 - 300 / 2 + jFrame.getX(), dimension.height / 2 - 200 / 2 + jFrame.getY(), 300, 300);
                JPanel dialogPanel = new JPanel();
                dialogPanel.setLayout(new BoxLayout(dialogPanel, 1));

                dialogPanel.add(Box.createVerticalStrut(10));

                dialogPanel.add(new JLabel("Время жизни рабочих:"));
                JPanel jpp = new JPanel();
                jpp.add(lifeSec1);
                lifeSec1.setText(String.valueOf(WorkAnt.lifeTime));
                lifeSec1.setFont(new Font("Serif", Font.BOLD, 15));
                lifeSec1.setForeground(Color.BLUE);
                lifeSec1.setEditable(true);
                dialogPanel.add(jpp);

                dialogPanel.add(new JLabel("Время жизни военных:"));
                JPanel jpp1 = new JPanel();
                jpp1.add(lifeSec2);
                lifeSec2.setText(String.valueOf(WarAnt.lifeTime));
                lifeSec2.setFont(new Font("Serif", Font.BOLD, 15));
                lifeSec2.setForeground(Color.BLUE);
                lifeSec2.setEditable(true);
                dialogPanel.add(jpp1);

                dialogPanel.add(new JLabel("Скорость движения муравьев:"));
                JPanel jpp2 = new JPanel();
                jpp2.add(antSpeed);
                antSpeed.setText(String.valueOf(Ant.speed));
                antSpeed.setFont(new Font("Serif", Font.BOLD, 15));
                antSpeed.setForeground(Color.BLUE);
                antSpeed.setEditable(true);
                dialogPanel.add(jpp2);

                bg.add(rDrag);
                bg.add(rStop);
                dialogPanel.add(rDrag);
                dialogPanel.add(rStop);

                optionDialog.add(dialogPanel);
                optionDialog.setVisible(true);

                optionDialog.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        super.windowClosing(e);
                        int lifeTime = 0;
                        try {
                            lifeTime = Integer.parseInt(lifeSec1.getText());
                        } catch (Exception ee) {
                            lifeTime = 0;
                        }
                        WorkAnt.lifeTime = lifeTime;

                        try {
                            lifeTime = Integer.parseInt(lifeSec2.getText());
                        } catch (Exception ee) {
                            lifeTime = 0;
                        }
                        WarAnt.lifeTime = lifeTime;

                        try {
                            Ant.speed = Integer.parseInt(antSpeed.getText());
                        } catch (Exception ee) {
                            Ant.speed = 0;
                        }
                    }
                });

                KeyListener checkText = new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        super.keyReleased(e);

                        int lifeTime = 0;
                        try {
                            lifeTime = Integer.parseInt(((JTextField) e.getSource()).getText());
                            if (lifeTime < 0) {
                                lifeTime = 0;
                                ((JTextField) e.getSource()).setText("0");
                            }

                        } catch (Exception ee) {
                            ((JTextField) e.getSource()).setText("");
                        }
                        System.out.println(((JTextField) e.getSource()).getText());
                    }
                };

                lifeSec1.addKeyListener(checkText);
                lifeSec2.addKeyListener(checkText);
                antSpeed.addKeyListener(checkText);

            }

        });
        var consoleMenu = new JMenuItem("Console");
        jMoreMenu.add(consoleMenu);
        consoleMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var consoleDialog = new JDialog(jFrame, "Console");
                Dimension dimension = drawPanel.getSize();
                consoleDialog.setBounds(dimension.width / 2 - 300 / 2 + jFrame.getX(), dimension.height / 2 - 200 / 2 + jFrame.getY(), 300, 300);
                var consoleField = new JTextArea();
                consoleField.setText("start - start simulation\nstop - stop simulation\nhelp window - open window about creators\nhelp - show available commands\nclear - clear console\n");
                var scroll = new JScrollPane(consoleField, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                consoleDialog.add(scroll);
                //consoleDialog.add(consoleField);
                consoleDialog.setVisible(true);
                consoleField.requestFocus();
                consoleField.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        super.keyReleased(e);
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            var lines = consoleField.getText().split("\\n");
                            if (lines.length != 0) {
                                for (var line : lines) {
                                    System.out.println(line);
                                }
                                switch (lines[lines.length - 1]) {
                                    case "start": {
                                        if (!isStart) {
                                            startBtn.doClick();
                                            consoleField.append("Simulation started\n");
                                        } else {
                                            consoleField.append("Simulation already runnin\n");
                                        }
                                        break;
                                    }
                                    case "stop": {
                                        if (isStart) {
                                            stopBtn.doClick();
                                            consoleField.append("Simulation stoped\n");
                                        } else {
                                            consoleField.append("Simulation already stoped\n");
                                        }
                                        break;
                                    }
                                    case "help window": {
                                        helpBtn.doClick();
                                        break;
                                    }
                                    case "help": {
                                        consoleField.append("start - start simulation\nstop - stop simulation\nhelp window - open window about creators\nhelp - show available commands\nclear - clear console\n");
                                        break;
                                    }
                                    case "clear": {
                                        consoleField.setText(null);
                                        consoleField.setCaretPosition(0);
                                        break;
                                    }
                                    default: {
                                        consoleField.append("Unknown command\n");
                                        break;
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });
        var serMenu = new JMenu("Serialization");
        jMenuBar.add(serMenu);
        var saveItem = new JMenuItem("Save");
        var loadItem = new JMenuItem("Load");
        serMenu.add(saveItem);
        serMenu.add(loadItem);
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveToFile();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        loadItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    loadFromFile();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        var saveWork = new JMenuItem("Save Work ants");
        var saveWar = new JMenuItem("Save War ants");
        serMenu.add(saveWork);
        serMenu.add(saveWar);
        saveWork.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveAntsByType(1);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        saveWar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveAntsByType(2);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        var server = new JMenuItem("Server");
        jMoreMenu.add(server);
        server.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientDialog = new ClientDialog();
                if (!isConnected) {
                    client = new Client();
                    client.enable();
                    client.start();
                }
                clientDialog.btn1.addActionListener(client.ButtonAction);
            }
        });

        jFrame.setJMenuBar(jMenuBar);

        helpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                helpBtn.setEnabled(false);
                JDialog dialog = new JDialog(jFrame, "About");
                Dimension dimension = drawPanel.getSize();
                dialog.setBounds(dimension.width / 2 - 300 / 2 + jFrame.getX(), dimension.height / 2 - 200 / 2 + jFrame.getY(), 300, 200);
                JTextArea helpT = new JTextArea("Данное приложение разработано командой\n\"Мы пришли за едой\", состоящей из студентов   НГТУ АВТФ группы АВТ-943:\nСлепченко Вадим; \nЖирнов Никита", 20, 5);
                JPanel textP = new JPanel();
                BoxLayout layout = new BoxLayout(textP, BoxLayout.X_AXIS);
                textP.setLayout(layout);

                textP.add(Box.createHorizontalStrut(2));
                textP.add(helpT);

                helpT.setLineWrap(true);
                helpT.setEditable(false);
                dialog.add(textP);
                dialog.setVisible(true);
                dialog.setResizable(false);
                dialog.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        super.windowClosing(e);
                        helpBtn.setEnabled(true);
                    }
                });
            }
        });

        islab2Label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                islab2Label.getToolTipText();
            }
        });

        isLab2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                isLab2.getToolTipText();
            }
        });

        bornChance1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                bornChance1.selectAll();
            }
        });
        bornChance2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                bornChance2.selectAll();
            }
        });
        bornSec1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                bornSec1.selectAll();
            }
        });
        bornSec2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                bornSec2.selectAll();
            }
        });


        MouseAdapter jFrameFocus = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                jFrame.requestFocus();
            }
        };

        jPanel.addMouseListener(jFrameFocus);

        jFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == 'b' || e.getKeyChar() == 'и') {
                    startBtn.doClick();
                }
                if (e.getKeyChar() == 'e' || e.getKeyChar() == 'у') {
                    stopBtn.doClick();
                }
                if (e.getKeyChar() == 't' || e.getKeyChar() == 'е') {
                    infPanel.setVisible(infPanel.isVisible() ? false : true);

                    if (infPanel.isVisible()) {
                        jFrame.setMinimumSize(new Dimension(500, 700));

                        jFrame.setSize(jFrame.getWidth(), jFrame.getHeight() < 700 ? 700 : jFrame.getHeight());
                    } else {
                        jFrame.setMinimumSize(new Dimension(500, 500));
                    }
                }

                if (e.getKeyChar() == 'p' || e.getKeyChar() == 'з') {
                    paramPan.setVisible(paramPan.isVisible() ? false : true);
                    jMenuBar.setVisible(jMenuBar.isVisible() ? false : true);
                }
                if (e.getKeyChar() == 'q' || e.getKeyChar() == 'й') {
                    jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                }
            }
        });

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isStart) {

                    antMoving.enable();

                    isStart = true;
                    drawPanel.ants.clear();
                    WorkAnt.count = 0;
                    WarAnt.count = 0;
                    warCount.setText("0");
                    workCount.setText("0");
                    drawPanel.timerOn();
                    if (isMusic.isSelected()) {
                        try {
                            playSound("resources/Муравьи.wav");
                        } catch (LineUnavailableException lineUnavailableException) {
                            lineUnavailableException.printStackTrace();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
                            unsupportedAudioFileException.printStackTrace();
                        }
                    }
                    isMusic.setEnabled(false);
                    isMusicLabel.setForeground(Color.gray);
                    drawPanel.validate();
                    drawPanel.repaint();
                    bornSec1.setEditable(false);
                    bornSec2.setEditable(false);
                    drawPanel.draw();
                }
            }
        });

        drawPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (/*isStart &&*/ rStop.isSelected()) {
                    int n = drawPanel.ants.size();
                    Ant ant;
                    for (int i = 0; i < n; i++) {
                        ant = drawPanel.ants.elementAt(i >= drawPanel.ants.size() ? i-- : i);
                        if (ant.x1 < e.getX() && ant.x1 + 100 > e.getX() && ant.y1 < e.getY() && ant.y1 + 100 > e.getY()) {
                            clickedAnt = ant;
                            clickedAnt.isDragging = !clickedAnt.isDragging;
                            System.out.println("Clicked");
                            break;
                        }
                    }
                }
            }
        });


        drawPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (/*isStart && */rDrag.isSelected()) {
                    int n = drawPanel.ants.size();
                    Ant ant;
                    for (int i = 0; i < n; i++) {
                        ant = drawPanel.ants.elementAt(i >= drawPanel.ants.size() ? i-- : i);
                        if (ant.x1 < e.getX() && ant.x1 + 100 > e.getX() && ant.y1 < e.getY() && ant.y1 + 100 > e.getY()) {
                            clickedAnt = ant;
                            clickedAnt.isDragging = true;
                            isMousePressed = true;
                            X = e.getX();
                            Y = e.getY();
                            System.out.println("Pressed");
                            break;
                        }
                    }
                }
            }
        });

        drawPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (isMousePressed) {
                    clickedAnt.x1 += e.getX() - X;
                    clickedAnt.y1 += e.getY() - Y;
                    if (clickedAnt.x1 < 0) clickedAnt.x1 = 0;
                    if (clickedAnt.y1 < 0) clickedAnt.y1 = 0;
                    X = e.getX();
                    Y = e.getY();
                    drawPanel.repaint();
                }
            }
        });


        drawPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (isMousePressed) {
                    isMousePressed = false;
                    clickedAnt.x = clickedAnt.x1;
                    clickedAnt.y = clickedAnt.y1;
                    clickedAnt.isDragging = false;
                }
            }
        });


        stopBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isStart) {
                    antMoving.disable();
                    isStart = false;
                    drawPanel.timerStop();
                    if (isMusic.isSelected()) stopSound();
                    isMusic.setEnabled(true);
                    isMusicLabel.setForeground(Color.black);
                    drawPanel.stopDraw();
                    if (isLab2.isSelected()) {
                        bornSec1.setEditable(true);
                        bornSec2.setEditable(true);
                    }
                }
            }
        });

        isMusicLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                isMusic.doClick();
            }
        });

        islab2Label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                isLab2.doClick();
            }
        });

        isLab2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Lab1OrLab2Check();
            }
        });

        isLab2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Lab1OrLab2Check();
            }
        });

        drawPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if (isLab2.isSelected()) {
                    int n = drawPanel.ants.size();
                    for (int i = 0; i < n; i++) {
                        if (drawPanel.ants.elementAt(i).x1 > drawPanel.getWidth() || drawPanel.ants.elementAt(i).y1 > drawPanel.getHeight()) {
                            if (drawPanel.ants.elementAt(i).type == 1) {
                                workCount.setText(String.valueOf(Integer.parseInt(workCount.getText()) - 1));
                                WorkAnt.count--;
                            } else {
                                warCount.setText(String.valueOf(Integer.parseInt(warCount.getText()) - 1));
                                WarAnt.count--;
                            }
                            drawPanel.ants.remove(drawPanel.ants.elementAt(i));
                            n--;
                        }
                    }

                }
            }
        });

        jFrame.revalidate();
    }

    static private void saveAntsByType(int antType) throws SQLException {
        stopBtn.doClick();
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        Statement statement = connection.createStatement();
        String sqlClear = "truncate ants, params;";
        statement.executeUpdate(sqlClear);

        var ants = drawPanel.ants;
        String str = "" + ants.size() + " ";
        if (ants.size() != 0) {
            for (var ant : ants) {
                if (ant.type == antType)
                    ant.toTxt(connection);
            }
        }
    }

    private static void Lab1OrLab2Check() {
        if (isLab2.isSelected()) {
            if (!isStart) {
                bornSec1.setEditable(true);
                bornSec2.setEditable(true);
            }
            bornChance1.setEditable(true);
            bornChance2.setEditable(true);

        } else {
            bornSec1.setEditable(false);
            bornSec2.setEditable(false);
            bornChance1.setEditable(false);
            bornChance2.setEditable(false);
        }
    }

    static private void saveToFile() throws SQLException {
        stopBtn.doClick();
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
//        try {
//            FileWriter f = new FileWriter("save.txt");
        Statement statement = connection.createStatement();
        String sqlClear = "truncate ants, params;";
        statement.executeUpdate(sqlClear);

        var ants = drawPanel.ants;
        String str = "" + ants.size() + " ";
        if (ants.size() != 0) {
            for (var ant : ants) {
                ant.toTxt(connection);
            }
        }

        String sql = "insert into params (WorkAnt_LifeSec, WorkAnt_BornSec, " +
                "WorkAnt_BornChance, WarAnt_LifeSec, " +
                "WarAnt_BornSec, WarAnt_BornChance, " +
                "Ant_Speed, rBox_state, isLab2_state, Music_state) values (?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement preparedStatementstatement = connection.prepareStatement(sql);
        preparedStatementstatement.setString(1, "" + WorkAnt.lifeTime);
        preparedStatementstatement.setString(2, bornSec1.getText());
        preparedStatementstatement.setString(3, bornChance1.getText());
        preparedStatementstatement.setString(4, WarAnt.lifeTime + "");
        preparedStatementstatement.setString(5, bornSec2.getText());
        preparedStatementstatement.setString(6, bornChance2.getText());
        preparedStatementstatement.setString(7, Ant.speed + "");
        String rstate;
        if (rStop.isSelected()) rstate = "0";
        else if (rDrag.isSelected()) rstate = "1";
        else rstate = "-1";
        preparedStatementstatement.setString(8, rstate);
        preparedStatementstatement.setString(9, isLab2.isSelected() ? "1" : "0");
        preparedStatementstatement.setString(10, isMusic.isSelected() ? "1" : "0");
        preparedStatementstatement.executeUpdate();
//            str += WorkAnt.lifeTime + " ";
//            str += WarAnt.lifeTime + " ";
//            str += bornSec1.getText() + " " + bornChance1.getText() + " " + lifeSec1.getText() + " " + bornSec2.getText() + " " + bornChance2.getText() + " " + lifeSec2.getText() + " " + Ant.speed + " ";
//            if (rStop.isSelected()) str += "0 ";
//            else if (rDrag.isSelected()) str += "1 ";
//            else str += "-1 ";
//            str += isLab2.isSelected() ? "1 " : "0 ";
//            str += isMusic.isSelected() ? "1 " : "0 ";
//
//            System.out.println(str);
//            f.write(str);
//            f.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    static private void loadFromFile() throws SQLException {
        stopBtn.doClick();
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        Statement statement = connection.createStatement();
        String sql = "select * from ants";
        ResultSet result = statement.executeQuery(sql);
        drawPanel.ants.clear();
        while (result.next()) {
            Ant ant;
            if (result.getInt("type") == 1) {
                ant = new WorkAnt(result.getInt("x"), result.getInt("y"), result.getInt("birth"));
                drawPanel.ants.add(ant);
            } else if (result.getInt("type") == 2) {
                ant = new WarAnt(result.getInt("x"), result.getInt("y"), result.getInt("birth"));
                drawPanel.ants.add(ant);
            }
        }
        sql = "select * from params";
        result = statement.executeQuery(sql);

        while (result.next()) {
            WorkAnt.lifeTime = Integer.parseInt(result.getString("WorkAnt_LifeSec"));
            bornSec1.setText(result.getString("WorkAnt_BornSec"));
            bornChance1.setText(result.getString("WorkAnt_BornChance"));
            WarAnt.lifeTime = Integer.parseInt(result.getString("WarAnt_LifeSec"));
            bornSec2.setText(result.getString("WarAnt_BornSec"));
            bornChance2.setText(result.getString("WarAnt_BornChance"));
            Ant.speed = Integer.parseInt(result.getString("Ant_Speed"));

            String rState = result.getString("rBox_state");
            if (rState.equals("0")) rStop.setSelected(true);
            else if (rState.equals("1")) rDrag.setSelected(true);

            String isLab2State = result.getString("isLab2_state");
            if (isLab2State.equals("1")) isLab2.setSelected(true);
            else if (isLab2State.equals("0")) isLab2.setSelected(false);

            String musicState = result.getString("Music_state");
            if (musicState.equals("1")) isMusic.setSelected(true);
            else if (musicState.equals("0")) isMusic.setSelected(false);
        }
        drawPanel.repaint();

//        File f = new File("save.txt");
//        String str = "";
//        try {
//            Scanner scanner = new Scanner(f);
//            while (scanner.hasNextLine()) {
//                str = scanner.nextLine();
//            }
//            var info = str.split(" ");
//            System.out.println(str);
//            drawPanel.ants.clear();
//            int j = 1;
//            for (int i = 0; i < Integer.parseInt(info[0]); i++) {
//                Ant ant;
//                if (info[j].equals("1")) {
//                    j++;
//                    ant = new WorkAnt(Double.parseDouble(info[j++]), Double.parseDouble(info[j++]), Integer.parseInt(info[j++]));
//                    drawPanel.ants.add(ant);
//                } else if (info[j].equals("2")) {
//                    j++;
//                    ant = new WarAnt(Double.parseDouble(info[j++]), Double.parseDouble(info[j++]), Integer.parseInt(info[j++]));
//                    drawPanel.ants.add(ant);
//                }
//            }
//            WorkAnt.lifeTime = Integer.parseInt(info[j++]);
//            WarAnt.lifeTime = Integer.parseInt(info[j++]);
//            bornSec1.setText(info[j++]);
//            bornChance1.setText(info[j++]);
//            WorkAnt.lifeTime = Integer.parseInt(info[j++]);
//            bornSec2.setText(info[j++]);
//            bornChance2.setText(info[j++]);
//            WarAnt.lifeTime = Integer.parseInt(info[j++]);
//            Ant.speed = Integer.parseInt(info[j++]);
//            if (info[j].equals("0")) rStop.setSelected(true);
//            else if (info[j].equals("1")) rDrag.setSelected(true);
//            j++;
//            if (info[j].equals("0")) isLab2.setSelected(false);
//            else if (info[j].equals("1")) isLab2.setSelected(true);
//            j++;
//            if (info[j].equals("0")) isMusic.setSelected(false);
//            else if (info[j].equals("1")) isMusic.setSelected(true);
//            scanner.close();
//            drawPanel.repaint();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    static JFrame getFrame() {
        JFrame jFrame = new JFrame() {
        };
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("Lab1-4");
        int WWidth = 1080;
        int WHeight = 500;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        jFrame.setBounds(dimension.width / 2 - WWidth / 2, dimension.height / 2 - WHeight / 2, WWidth, WHeight);
        jFrame.setMinimumSize(new Dimension(500, WHeight));
        return jFrame;
    }

    static Clip clip;

    static {
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    static void playSound(String soundFile) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        URL url = Lab1.class.getResource(soundFile);
        System.out.println(url);
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
        clip.open(audioIn);
        clip.loop(100);
        clip.start();
    }

    static void stopSound() {
        clip.stop();
        clip.close();
    }
}