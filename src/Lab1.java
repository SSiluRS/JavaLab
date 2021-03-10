import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class Lab1 {
    static JFrame jFrame = getFrame();
    static JPanel jPanel = new JPanel();
    static DrawClass drawPanel = new DrawClass();
    static JTextField bornSec1 = new JTextField("1000",8);
    static JTextField bornChance1 = new JTextField("30",8);
    static JTextField bornSec2 = new JTextField("5000",8);
    static JTextField bornChance2 = new JTextField("50",8);
    static JTextField workCount = new JTextField("0");
    static JTextField warCount = new JTextField("0");
    static JTextField timeElapsed = new JTextField("0");
    static Timer tSize = new Timer();
    static boolean isTSizeOn = false;
    static boolean isHelp = false;

    private static int WHeight = 670;
    private static int WWidth = 1080;

    static  boolean isStart = false;

    public static void main(String[] args){

        jFrame.add(jPanel);
        GridBagLayout gridBagLayout = new GridBagLayout();
        jPanel.setLayout(gridBagLayout);
        GridBagConstraints constraints = new GridBagConstraints();

        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new GridLayout(4,1));
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
        jPanel2.setLayout(new GridLayout(4,1));
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
        infoPan.setLayout(new GridLayout(6,1));
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
        paramPan.setLayout(new BoxLayout(paramPan,1));
        JPanel buttonsPan = new JPanel();
        JButton startBtn = new JButton("Start");
        JButton stopBtn = new JButton("Stop");
        buttonsPan.add(startBtn);
        buttonsPan.add(stopBtn);


        JPanel oneMorePanel = new JPanel();
        JCheckBox isLab2 = new JCheckBox();
        JLabel islab2Label = new JLabel("Lab1 or Lab2");
        islab2Label.setToolTipText("Выбор между первой и второй лабой");
        isLab2.setToolTipText("Выбор между первой и второй лабой");
        oneMorePanel.add(islab2Label);
        oneMorePanel.add(isLab2);
        isLab2.setSelected(false);
        JPanel oneMoreBoxPanel = new JPanel();
        oneMoreBoxPanel.setLayout(new BoxLayout(oneMoreBoxPanel,1));
        oneMoreBoxPanel.add(buttonsPan);
        oneMoreBoxPanel.add(oneMorePanel);

        JPanel morePanel = new JPanel();
        JCheckBox isMusic = new JCheckBox();
        JLabel isMusicLabel = new JLabel("Turn music");
        morePanel.add(isMusicLabel);
        morePanel.add(isMusic);
        isMusic.setSelected(false);
        oneMoreBoxPanel.add(morePanel);

        JPanel moreBoxPanel = new JPanel();
        moreBoxPanel.setLayout(new BoxLayout(moreBoxPanel,BoxLayout.X_AXIS));
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
        jPanel.add(paramPan,constraints);

        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.gridwidth = 6;
        constraints.gridheight = 6;
        constraints.fill = GridBagConstraints.BOTH;
        drawPanel.setBackground(Color.gray);
        jPanel.add(drawPanel,constraints);

        helpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                helpBtn.setEnabled(false);
                JDialog dialog = new JDialog(jFrame, "About");
                Dimension dimension = drawPanel.getSize();
                dialog.setBounds(dimension.width / 2 - 300 / 2 + jFrame.getX(), dimension.height / 2 - 200 / 2 + jFrame.getY(), 300, 200);
                JTextArea helpT = new JTextArea("Данное приложение разработано студентами   НГТУ АВТФ группы АВТ-943:",20,5);
                JPanel textP = new JPanel();
                BoxLayout layout = new BoxLayout(textP,BoxLayout.X_AXIS);
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
                if (e.getKeyChar() == 'b' || e.getKeyChar() == 'и'){
                    startBtn.doClick();
                }
                if (e.getKeyChar() == 'e' || e.getKeyChar() == 'у'){
                    stopBtn.doClick();
                }
                if(e.getKeyChar() == 't' || e.getKeyChar() == 'е') {
                    infPanel.setVisible(infPanel.isVisible() ? false : true);

                    if (infPanel.isVisible()) {
                        jFrame.setMinimumSize(new Dimension(500, 670));
                        jFrame.setSize(jFrame.getWidth(), 670);
                    }
                    else{
                        jFrame.setMinimumSize(new Dimension(500, 470));
                        jFrame.setSize(jFrame.getWidth(), 470);
                    }
                }

                if(e.getKeyChar() == 'p' || e.getKeyChar() == 'з'){
                    paramPan.setVisible(paramPan.isVisible()?false:true);
                }
            }
        });

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isStart) {
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

        stopBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isStart) {
                    isStart = false;
                    drawPanel.timerStop();
                    if(isMusic.isSelected()) stopSound();
                    isMusic.setEnabled(true);
                    isMusicLabel.setForeground(Color.black);
                    drawPanel.stopDraw();
                    if(isLab2.isSelected()) {
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

        isLab2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isLab2.isSelected()) {
                    if (!isStart) {
                        bornSec1.setEditable(true);
                        bornSec2.setEditable(true);
                    }
                    bornChance1.setEditable(true);
                    bornChance2.setEditable(true);

                }
                else {
                    bornSec1.setEditable(false);
                    bornSec2.setEditable(false);
                    bornChance1.setEditable(false);
                    bornChance2.setEditable(false);
                }
            }
        });

        drawPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if(isLab2.isSelected()) {
                    int n = drawPanel.ants.size();
                    for(int i = 0; i<n;i++){
                        if(drawPanel.ants.elementAt(i).x> drawPanel.getWidth() || drawPanel.ants.elementAt(i).y > drawPanel.getHeight()){
                            if(drawPanel.ants.elementAt(i).type == 1){
                                workCount.setText(String.valueOf(Integer.parseInt(workCount.getText())-1));
                                WorkAnt.count--;
                            }
                            else{
                                warCount.setText(String.valueOf(Integer.parseInt(warCount.getText())-1));
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

    static JFrame getFrame(){
        JFrame jFrame = new JFrame(){};
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("Lab1");
        int WWidth = 1080;
        int WHeight = 480;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        jFrame.setBounds(dimension.width/2-WWidth/2,dimension.height/2-WHeight/2,WWidth,WHeight);
        jFrame.setMinimumSize(new Dimension(500,WHeight));
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

    static void stopSound(){
        clip.stop();
        clip.close();
    }
}