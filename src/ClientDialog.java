import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientDialog extends JDialog {
    static Lab1 lab1;
    JTextArea consoleField;
    JTextField jTextField = new JTextField(15);
    JButton btn1;

    ClientDialog() {
        super(lab1.jFrame, "Client");
        Dimension dimension = getSize();
        setBounds(dimension.width / 2 - 300 / 2 + lab1.jFrame.getX(), dimension.height / 2 - 200 / 2 + lab1.jFrame.getY(), 300, 300);
        consoleField = new JTextArea();
        consoleField.setEditable(false);
        var jPan1 = new JPanel();
        jPan1.setLayout(new BoxLayout(jPan1, 1));
        var scroll = new JScrollPane(consoleField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        DefaultCaret caret = (DefaultCaret) consoleField.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        jPan1.add(scroll);
        btn1 = new JButton("Send");
        JPanel jPan2 = new JPanel();
        jPan2.setLayout(new BoxLayout(jPan2, 0));
        scroll.setPreferredSize(new Dimension(250, 250));
        JPanel jpan3 = new JPanel();
        JPanel jpan4 = new JPanel();
        jpan3.add(jTextField);
        jpan4.add(btn1);
        jPan2.add(jpan3);
        jPan2.add(jpan4);
        jPan1.add(Box.createVerticalStrut(20));
        jPan1.add(jPan2);
        add(jPan1);
        setVisible(true);
    }
}
