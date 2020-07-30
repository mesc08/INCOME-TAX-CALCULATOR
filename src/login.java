import java.awt.event.*;
import javax.swing.*;
import java.lang.*;
import java.sql.*;
import BCrypt.*;

public class login extends JFrame implements ActionListener {
    private JLabel l1, l2;
    private JButton b1, b2;
    private JTextField u1;
    private JPasswordField p1;
    private JRadioButton rb[];
    private ButtonGroup bg1;

    public login(){

        l1 = new JLabel("User ID");
        l1.setBounds(75,50,300,40);
        u1 = new JTextField();
        u1.setBounds(300,50,300,30);

        l2 = new JLabel("Password");
        l2.setBounds(75,100,300,40);
        p1 = new JPasswordField();
        p1.setBounds(300,100,300,30);

        rb = new JRadioButton[2];
        rb[0] = new JRadioButton("User");
        rb[0].setActionCommand("user");
        rb[0].setBounds(260,160,80,35);
        rb[0].setSelected(true);
        rb[1] = new JRadioButton("Administrator");
        rb[1].setActionCommand("admin");
        rb[1].setBounds(340,160,100,35);
        bg1 = new ButtonGroup();
        bg1.add(rb[0]);
        bg1.add(rb[1]);

        b1=new JButton("Login");
        b1.setBounds(133,250,150,30);
        b1.addActionListener(this);
        b1.setActionCommand("loggedin");

        b2=new JButton("Create A/c");
        b2.setBounds(417,250,150,30);
        b2.addActionListener(this);
        b2.setActionCommand("createac");

        add(rb[0]);add(rb[1]);
        add(l1);add(u1);
        add(l2);add(p1);
        add(b2);
        add(b1);

        setSize(700,380);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        setTitle("Login Page");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if(JOptionPane.showConfirmDialog(null, "Are you sure ?","Exit?",JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION){
                    setVisible(false);
                    dispose();
                }
            }
        });

    }

    public void actionPerformed(ActionEvent e){
        String cmd = e.getActionCommand();
        if (cmd.equals("loggedin") && rb[0].isSelected()){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb","root","admin");
                Statement stmt = con.createStatement();
                String pwd = new String(p1.getPassword());
                String uid = u1.getText();
                ResultSet rs=stmt.executeQuery("select password from users where userid=\""+uid+"\"");
                if (!rs.next()){
                    JOptionPane.showMessageDialog(null,"kindly check your username","Invalid username",JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    String flag = rs.getString(1);
                    if (BCrypt.checkpw(pwd, flag)) {
                        new masterwindow(uid);
                        con.close();
                        dispose();
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"kindly check your password","Invalid password",JOptionPane.INFORMATION_MESSAGE);
                    }

                }
                con.close();
            }
            catch(Exception e1){
                System.out.println(e1);
            }

        }
        else if (cmd.equals("createac") && rb[0].isSelected()){
            new createac();
            dispose();
        }
        else if (cmd.equals("loggedin") && rb[1].isSelected()){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb","root","admin");
                Statement stmt = con.createStatement();
                String pwd = new String(p1.getPassword());
                String uid = u1.getText();
                ResultSet rs=stmt.executeQuery("select password from admins where userid=\""+uid+"\"");
                if (!rs.next()){
                    JOptionPane.showMessageDialog(null,"kindly check your username","Invalid username",JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    String flag = rs.getString(1);
                    if (BCrypt.checkpw(pwd, flag)) {
                        new masterwindow_admin(uid);
                        con.close();
                        dispose();
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"kindly check your password","Invalid password",JOptionPane.INFORMATION_MESSAGE);
                    }

                }
                con.close();
            }
            catch(Exception e1){
                System.out.println(e1);
            }
        }
        else if (cmd.equals("createac") && rb[1].isSelected()){
            new createac_admin();
            dispose();
        }

    }

    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e5){
            System.out.println(e5);
        }
        login sw = new login();

    }
}