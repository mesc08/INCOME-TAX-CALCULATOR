import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import com.itextpdf.*;
import java.awt.print.*;
import java.text.MessageFormat;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class get_statistics  extends JPanel implements ActionListener {
    private JTable table;
    private JButton b1, b2, b3, b4, b5;
    private JLabel l1, l2, l3, l4;
    private JTextField tf1, tf2, tf3, tf4, tf5;
    private masterwindow_admin pointer;
    private String username;

    public get_statistics(masterwindow_admin parent, String uid) {

        pointer = parent;
        username = uid;
        table = new JTable();
        Object[] col = {"User-ID","Income","Total Tax","Year"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(col);
        table.setModel(model);
        //table.setBackground(Color.LIGHT_GRAY);
        table.setForeground(Color.black);
        Font font = new Font("",1,22);
        table.setFont(font);
        table.setRowHeight(30);
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0,0,800,600);

        l1 = new JLabel("Name");
        l1.setBounds(87,620,150,30);
        tf1 = new JTextField();
        tf1.setBounds(281,620,431,30);
        tf1.setEditable(false);
        l2 = new JLabel("Phone");
        l2.setBounds(87,660,150,30);
        tf2 = new JTextField();
        tf2.setBounds(281,660,431,30);
        tf2.setEditable(false);
        l3 = new JLabel("Address");
        l3.setBounds(87,700,150,30);
        tf3 = new JTextField();
        tf3.setBounds(281,700,431,30);
        tf3.setEditable(false);
        l4 = new JLabel("E-mail");
        l4.setBounds(87,740,150,30);
        tf4 = new JTextField();
        tf4.setBounds(281,740,431,30);
        tf4.setEditable(false);


        b1 = new JButton("Get PDF");
        b1.addActionListener(this);
        b1.setActionCommand("pdf");
        b1.setBounds(87,820,150,30);

        b2 = new JButton("Back");
        b2.addActionListener(this);
        b2.setActionCommand("back");
        b2.setBounds(325,820,150,30);

        b3 = new JButton("Log out");
        b3.addActionListener((e -> parent.show("login")));
        b3.setBounds(562,820,150,30);

        b4 = new JButton("Search info");
        b4.setBounds(87,780,150,30);
        b4.setActionCommand("search");
        b4.addActionListener(this);
        tf5 = new JTextField("enter user-id");
        tf5.setBounds(281,780,238,30);

        b5 = new JButton("Print");
        b5.setActionCommand("print");
        b5.addActionListener(this);
        b5.setBounds(562,780,150,30);

        Object[] row = new Object[4];

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb","root","admin");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select userid, income, taxtopay, assessyr from statistics");
            while (rs.next()){
                row[0]=rs.getString(1);
                row[1]=rs.getString(2);
                row[2]=rs.getString(3);
                row[3]=rs.getString(4);
                model.addRow(row);

            }

            con.close();
        }
        catch(Exception e1){
            System.out.println(e1);
        }

        add(pane);
        add(tf1);add(tf2);add(tf3);add(tf4);add(tf5);
        add(l1);add(l2);add(l3);add(l4);
        add(b1);add(b2);add(b3);add(b4);add(b5);
        setLayout(null);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("back")){
            pointer.setTitle("Welcome");
            pointer.show("welcome_admin");

        }
        else if (cmd.equals("print")){
            try {
                MessageFormat header = new  MessageFormat("Record Table");
                boolean complete = table.print(JTable.PrintMode.FIT_WIDTH, header, null);
                if (complete) {
                    System.out.println("Successful print");
                }
                else {
                    System.out.println("Unsuccessful print");
                }
            }
            catch (PrinterException pe) {

            }
        }
        else if (cmd.equals("search")){
            try{
                String username2 = tf5.getText();
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb","root","admin");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select fname, mname, lname, address, phone, email from users where userid = \""+ username2 +"\"");
                if (!rs.next()){
                    JOptionPane.showMessageDialog(null,"username does not exists","Invalid username",JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    String name="";
                    String fname = rs.getString(1);
                    String mname = rs.getString(2);
                    String lname = rs.getString(3);
                    String phno=rs.getString(5);
                    String mail=rs.getString(6);
                    String add=rs.getString(4);
                    if (mname.equals("") || mname==null){
                        name = fname +" "+lname;
                    }
                    else{
                        name = fname + " " + mname + " " + lname;
                    }
                    tf1.setText(name);
                    tf2.setText(phno);
                    tf3.setText(add);
                    tf4.setText(mail);
                }
                con.close();
            }
            catch(Exception e1){
                System.out.println(e1);
            }
        }
        else if (cmd.equals("pdf")){
            String file = "";

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save As");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PDF File", "pdf"));
            fileChooser.setAcceptAllFileFilterUsed(true);

            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                File file2 = checkFileName(fileToSave);
                System.out.println("Save as file: " + file2.getAbsolutePath());

                file = file2.getAbsolutePath();
                pdfgen2 admin = new pdfgen2(this.username,file);
                admin.getPDF();
                JOptionPane.showMessageDialog(null,"PDF successfully generated","Success",JOptionPane.INFORMATION_MESSAGE);
            }

        }

    }
    public File checkFileName(File file) {
        if (!file.getAbsolutePath().toLowerCase().endsWith(".pdf")) {
            return new File(file.getAbsolutePath() + ".pdf");
        }
        else {
            return file;
        }
    }

    /*public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e5){
            System.out.println(e5);
        }
        get_statistics gs = new get_statistics();
        gs.setVisible(true);
        gs.setSize(800,900);
        gs.setResizable(false);
        gs.setLocationRelativeTo(null);
    }*/

}
