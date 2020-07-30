import java.awt.*;
import javax.swing.*;
import java.awt.print.PrinterException;
import java.lang.*;
import java.sql.*;
import java.text.MessageFormat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.itextpdf.*;

public class calculator extends JPanel implements ActionListener {
    private JButton b1, b2, b3, b4, b5;
    private JLabel l1 ,l2, l3, l4, l5;
    private JTextField tf1, tf2, tf3, tf4, tf5;
    private String username;
    private masterwindow pointer;
    private long tax=0, ec=0, hec=0, total=0;
    private String fn="", mn="", ln="";
    private String year="";

    public calculator(masterwindow parent, String uid) {
        username = uid;
        pointer = parent;
        l5 = new JLabel("Income");
        l5.setBounds(100,200,300,40);
        tf5 = new JTextField();
        tf5.setBounds(325,200,300,40);
        tf5.setEditable(false);

        l1 = new JLabel("General Tax");
        l1.setBounds(100,270,300,40);
        tf1 = new JTextField();
        tf1.setBounds(325,270,300,40);
        tf1.setEditable(false); 

        l2 = new JLabel("Health Tax");
        l2.setBounds(100,340,300,40);
        tf2 = new JTextField();
        tf2.setBounds(325,340,300,40);
        tf2.setEditable(false); 

        l3 = new JLabel("Education Tax");
        l3.setBounds(100,410,300,40);
        tf3 = new JTextField();
        tf3.setBounds(325,410,300,40);
        tf3.setEditable(false); 

        l4 = new JLabel("Total");
        l4.setBounds(100,480,300,40);
        tf4 = new JTextField();
        tf4.setBounds(325,480,300,40);
        tf4.setEditable(false);

        b1 = new JButton("Calculate");
        b1.setActionCommand("calculate");
        b1.addActionListener(this);
        b1.setBounds(87,760,150,30);

        b2 = new JButton("Back");
        b2.addActionListener(this);
        b2.setActionCommand("back");
        b2.setBounds(325,760,150,30);

        b3 = new JButton("Log out");
        b3.addActionListener((e -> parent.show("login")));
        b3.setBounds(562,760,150,30);

        b4 = new JButton("Get PDF");
        b4.addActionListener(this);
        b4.setActionCommand("pdf");
        b4.setBounds(87,720,150,30);
        b4.setEnabled(false);

        b5 = new JButton("Print");
        b5.setActionCommand("print");
        b5.addActionListener(this);
        b5.setBounds(562,720,150,30);
        b5.setEnabled(false);




        add(l5);add(tf5);
        add(l1);add(tf1);
        add(l2);add(tf2);
        add(l3);add(tf3);
        add(l4);add(tf4);
        add(b1);
        add(b2);
        add(b3);
        add(b4);
        add(b5);
        setLayout(null);

        
    }
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals("calculate")){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "admin");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select sencitizen, income, email, fname, mname, lname, assessyr from users where userid=\"" + username + "\"");
                if (rs.next()) {
                    String salary = rs.getString(2);
                    long sal = Integer.parseInt(salary);
                    if (rs.getString(1).equals("N")){
                        if (sal <= 250000) {
                            tax = sal * 0;
                            ec = sal * 0;
                            hec = sal * 0;
                            total = tax + ec + hec;
                        }
                        else if (sal >= 250001 && sal <= 500000) {
                            tax = (sal - 250000) * 5;
                            tax = tax/100;
                            ec = sal * 2;
                            ec = ec/100;
                            hec = sal * 4;
                            hec = hec/100;
                            total = tax + ec + hec;
                        }
                        else if (sal >= 500001 && sal <= 1000000) {
                            tax = (sal - 500000) * 2;
                            tax = tax/100;
                            tax += 12500;
                            ec = sal * 2;
                            ec = ec/100;
                            hec = sal * 4;
                            hec = hec/100;
                            total = tax + ec + hec;
                        }
                        else {
                            tax = (sal - 1000000) * 3;
                            tax = tax/100;
                            tax += 112500;
                            ec = sal * 2;
                            ec = ec/100;
                            hec = sal * 4;
                            hec = hec/100;
                            total = tax + ec + hec;
                        }

                    }
                    else{
                        if (sal <= 300000) {
                            tax = sal * 0;
                            ec = sal * 0;
                            hec = sal * 0;
                            total = tax + ec + hec;
                        }
                        else if (sal >= 300001 && sal <= 500000) {
                            tax = sal * 5;
                            tax = tax/100;
                            ec = sal * 2;
                            ec = ec/100;
                            hec = sal * 4;
                            hec = hec/100;
                            total = tax + ec + hec;
                        }
                        else if (sal >= 500001 && sal <= 1000000) {
                            tax = sal * 2;
                            tax = tax/100;
                            ec = sal * 2;
                            ec = ec/100;
                            hec = sal * 4;
                            hec = hec/100;
                            total = tax + ec + hec;
                        }
                        else {
                            tax = sal * 3;
                            tax = tax/100;
                            ec = sal * 2;
                            ec = ec/100;
                            hec = sal * 4;
                            hec = hec/100;
                            total = tax + ec + hec;
                        }
                    }
                    tf1.setText(String.valueOf(tax));
                    tf2.setText(String.valueOf(ec));
                    tf3.setText(String.valueOf(hec));
                    tf4.setText(String.valueOf(total));
                    tf5.setText(String.valueOf(sal));
                    String m = rs.getString(3);
                    fn = rs.getString(4);
                    mn = rs.getString(5);
                    ln = rs.getString(6);
                    year = rs.getString(7);

                    boolean update_flag = false;

                    try{
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb","root","admin");
                        Statement stmt2 = con2.createStatement();
                        ResultSet rs2 = stmt2.executeQuery("SELECT assessyr FROM statistics WHERE userid=\""+username+"\"");
                        while (rs2.next()) {
                            String db_year = rs2.getString(1);
                            System.out.println(db_year);
                            if (db_year.equals(year)){
                                update_flag = true;
                                break;
                            }

                        }
                        con.close();
                    }
                    catch (Exception e12){
                        System.out.println(e12);
                    }


                    /*System.out.println(update_flag);
                    if (update_flag){
                        System.out.println(salary+" "+total);
                    }*/


                    if (update_flag){
                        try{
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            Connection con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb","root","admin");
                            Statement stmt2 = con2.createStatement();
                            stmt2.executeUpdate("UPDATE statistics SET income =\"" + salary + "\", taxtopay =\"" + total + "\" WHERE userid = \"" + username + "\" AND assessyr = \"" + year + "\";");
                        }
                        catch (Exception e13){
                            System.out.println(e13);
                        }
                    }
                    else{
                        try{
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            Connection con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb","root","admin");
                            Statement stmt2 = con2.createStatement();
                            String query = "INSERT INTO statistics (userid, income, taxtopay, assessyr) VALUES (\""+ username +"\", \""+ sal +"\", \""+ total +"\",\""+ year +"\" );";
                            stmt2.executeUpdate(query);
                            con.close();

                        }
                        catch(Exception e2){
                        }
                    }

                    automailer aw = new automailer(m);
                    String sub = "Tax Summary";
                    String msg;
                    String sub2 = "User log on ";
                    String msg2;
                    if (mn.equals("") || mn==null){
                        msg = "Hello " + fn + " " + ln + ", thank you for using Tax Calculator. Your tax summary is provided in the pdf attached below." +
                                "\nDon't reply bot here";
                        msg2 = fn + " " + ln + " (userid : "+ this.username +") has used Tax Calculator. " +
                                "\nThe tax summary is :- \n"+
                                "\tGeneral Tax\t :      "+tax+"\n"+
                                "\tEducation Tax\t :    "+hec+"\n"+
                                "\tHealth Tax\t :       "+ec+"\n"+
                                "\tTotal Tax\t :        "+total+"\n"+
                                "\nDon't reply bot here";
                    }
                    else{
                        msg = "Hello " + fn + " " + mn + " " + ln + ", thank you for using Tax Calculator. Your tax summary is provided in the pdf attached below." +
                                "\nDon't reply bot here";
                        msg2 = "Hello " + fn + " " + mn + " " + ln + " (userid =" + this.username + ") has used Tax Calculator. " +
                                "\nThe tax summary is :- \n"+
                                "\tGeneral Tax\t :      "+tax+"\n"+
                                "\tEducation Tax\t :    "+hec+"\n"+
                                "\tHealth Tax\t :       "+ec+"\n"+
                                "\tTotal Tax\t :        "+total+"\n"+
                                "\nDon't reply bot here";
                    }

                    String name = "";
                    if (mn.equals("") || mn==null){
                        name = fn+" " +ln;
                    }
                    else{
                        name = fn+" "+mn+" " +ln;
                    }
                    String file = "C:\\Users\\Somnath\\Documents\\Project_TaxCalc_PDFs";
                    File directory = new File(file);
                    if (! directory.exists()){
                        directory.mkdir();
                    }
                    file += "\\"+username+".pdf";
                    pdfgen p1 = new pdfgen(name,username,year,file,tax,hec,ec,total);
                    p1.getPDF();
                    aw.send_user(sub,msg,true,file);
                    aw.send_admins(sub2,msg2);


                }
            }
            catch (Exception e4){
                System.out.println(e4);
            }
            b4.setEnabled(true);
            b5.setEnabled(true);

        }
        else if (cmd.equals("back")){
            tf1.setText("");
            tf2.setText("");
            tf3.setText("");
            tf4.setText("");
            tf5.setText("");
            pointer.setTitle("Welcome");
            pointer.show("welcome");
        }
        else if (cmd.equals("print")){

            String[] col = {"Fields","Amount (in INR)"};
            String[][] data = {{"General Tax",""+tax},{"Education Tax",""+ec},{"Health Tax",""+hec},{"Total",""+total}};
            JTable table = new JTable(data,col);
            table.setBackground(Color.LIGHT_GRAY);
            table.setForeground(Color.black);
            Font font = new Font("",1,22);
            table.setFont(font);
            table.setRowHeight(30);
            JScrollPane sp = new JScrollPane(table);
            JFrame jf = new JFrame();
            jf.add(sp);
            //System.out.println(tax+" "+ec+" "+hec+" "+total);
            jf.setSize(700, 700);
            // Frame Visible = true
            jf.setVisible(true);
            jf.setTitle("Preview");

            try {
                MessageFormat header = new  MessageFormat("Tax Summary");
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
        else if (cmd.equals("pdf")){
            String name = "";
            if (mn.equals("") || mn==null){
                name = fn+" " +ln;
            }
            else{
                name = fn+" "+mn+" " +ln;
            }

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
                pdfgen p1 = new pdfgen(name,username,year,file,tax,hec,ec,total);
                p1.getPDF();
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
}
