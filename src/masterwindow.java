import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.*;


public class masterwindow extends JFrame {
    private CardLayout cardlayout;
    public masterwindow(String uid) {
        setVisible(true);
        setSize(800,900);
        setResizable(false);
        setTitle("Welcome");
        setLocationRelativeTo(null);
        cardlayout = new CardLayout();
        setLayout(cardlayout);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                JOptionPane.showMessageDialog(null,"Log out first");
            }
        });
        add("welcome", new welcome(this,uid));
        add("update", new update(this,uid));
        add("calculator", new calculator(this,uid));
        show("welcome");
    }

    public void show(String name) {
        if (name.equals("login")){
            new login();
            dispose();
        }
        cardlayout.show(getContentPane(), name);
    }

}
