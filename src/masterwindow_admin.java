import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.*;


public class masterwindow_admin extends JFrame {
    private CardLayout cardlayout;

    public masterwindow_admin(String uid) {
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
        add("welcome_admin", new welcome_admin(this,uid));
        add("update_admin", new update_admin(this,uid));
        add("stats", new get_statistics(this,uid));
        show("welcome_admin");
    }

    public void show(String name) {
        if (name.equals("login")){
            new login();
            dispose();
        }
        cardlayout.show(getContentPane(), name);
    }

}
