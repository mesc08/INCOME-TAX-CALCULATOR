import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.lang.*;
import java.sql.*;
import BCrypt.*;
import java.util.Random;

public class createac extends JFrame implements ActionListener {
    private JLabel l[];
    private JLabel l2[];
    private JLabel l3;
    private JTextField tf[];
    private JPasswordField pf1, pf2;
    private JRadioButton rb[];
    private ButtonGroup bg1;
    private JCheckBox cb1;
    private JButton b1, b2;
    private JComboBox<String> db1;
    public createac(){
        l = new JLabel[13];
        l2 = new JLabel[13];
        int y2 = 50;
        for (int i = 0; i<13; i++){
            l2[i] = new JLabel("*");
            l2[i].setBounds(610,y2,15,35);
            y2 += 50;
            l2[i].setVisible(false);
            add(l2[i]);
        }
        tf = new JTextField[8];
        for (int i=0; i<tf.length; i++) {
            tf[i] = new JTextField();
        }

        l[0] = new JLabel("First Name");
        tf[0].setBounds(300,50,300,35);


        l[1] = new JLabel("Middle Name (optional)");
        tf[1].setBounds(300,100,300,35);

        l[2] = new JLabel("Last Name");
        tf[2].setBounds(300,150,300,35);

        l[3] = new JLabel("Gender");
        rb = new JRadioButton[3];
        rb[0] = new JRadioButton("M");
        rb[0].setActionCommand("Male");
        rb[0].setBounds(300,200,40,35);
        rb[1] = new JRadioButton("F");
        rb[1].setActionCommand("Female");
        rb[1].setBounds(340,200,40,35);
        rb[2] = new JRadioButton("Other");
        rb[2].setActionCommand("Other");
        rb[2].setBounds(380,200,80,35);
        bg1 = new ButtonGroup();
        bg1.add(rb[0]);
        bg1.add(rb[1]);
        bg1.add(rb[2]);

        l[4] = new JLabel("Senior citizen (60 years and above)");
        cb1 = new JCheckBox("Yes");
        cb1.setBounds(300,250,100,35);

        l[5] = new JLabel("Address");
        tf[3].setBounds(300,300,300,35);

        l[6] = new JLabel("Phone no. (10 digit)");
        tf[4].setBounds(300,350,300,35);

        l[7] = new JLabel("E-mail ID");
        tf[5].setBounds(300,400,300,35);

        l[8] = new JLabel("Assessment Year");
        String[] years = new String[]{"2020","2019","2018","2017","2016","2015","2014","2013","2012","2011","2010","2009"};
        db1 = new JComboBox<>(years);
        db1.setSelectedIndex(1);
        db1.setBounds(300,450,300,35);

        l[9] = new JLabel("Net Taxable Income");
        tf[6].setBounds(300,500,300,35);

        l[10] = new JLabel("User ID");
        tf[7].setBounds(300,550,300,35);

        l[11] = new JLabel("Set password");
        pf1 = new JPasswordField();
        pf1.setBounds(300,600,300,35);

        l[12] = new JLabel("Confirm password");
        pf2 = new JPasswordField();
        pf2.setBounds(300,650,300,35);

        l3 = new JLabel("* necessary field");
        l3.setBounds(300,700,300,40);
        l3.setForeground(Color.red);
        l3.setVisible(false);
        add(l3);

        b1 = new JButton("Create A/c");
        b1.setBounds(135,760,150,30);
        b1.addActionListener(this);
        b1.setActionCommand("createac");
        b2 = new JButton("Back");
        b2.setBounds(385,760,150,30);
        b2.addActionListener(this);
        b2.setActionCommand("login");

        int y = 50;
        for (JLabel jLabel : l) {
            jLabel.setBounds(75, y, 300, 40);
            y += 50;
        }
        for (JLabel jLabel : l) {
            add(jLabel);
        }
        for (JTextField jTextField : tf) {
            add(jTextField);
        }

        add(rb[0]);add(rb[1]);add(rb[2]);
        add(db1);
        add(pf1);
        add(pf2);
        add(cb1);
        add(b1);
        add(b2);
        setSize(710,900);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        setTitle("Create Account");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if(JOptionPane.showConfirmDialog(null, "Are you sure? All progress will be lost.","Exit?",JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION){
                    setVisible(false);
                    dispose();
                }
            }
        });
    }
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if ("createac".equals(cmd)){

            for (int i = 0; i<13; i++){
                l2[i].setVisible(false);
            }
            l3.setVisible(false);

            boolean flag = true;

            /*password validation*/
            String pwd1 = new String(pf1.getPassword()) ;
            String pwd2 = new String(pf2.getPassword());
            String pwd = "";
            if ( (pwd1.equals("") || pwd1==null) || (pwd2.equals("") || pwd2==null) ) {
                flag = false;
                if (pwd1.equals("") || pwd1 == null) {
                    l2[11].setForeground (Color.red);
                    l2[11].setVisible(true);
                    l3.setText("* necessary field");
                    l3.setVisible(true);
                }
                if (pwd2.equals("") || pwd2 == null) {
                    l2[12].setForeground (Color.red);
                    l2[12].setVisible(true);
                    l3.setText("* necessary field");
                    l3.setVisible(true);
                }
            }
            else if (!pwd1.equals(pwd2)){
                l2[11].setForeground (Color.red);
                l2[12].setForeground (Color.red);
                l2[11].setVisible(true);
                l2[12].setVisible(true);
                l3.setText("* password not same");
                l3.setVisible(true);
                flag = false;
            }
            else{
                l2[11].setVisible(true);
                l2[12].setVisible(true);
                l2[11].setForeground (Color.green);
                l2[12].setForeground (Color.green);
                pwd = BCrypt.hashpw(pwd1, BCrypt.gensalt());
            }

            /*phone number*/
            String pno = tf[4].getText();
            if (pno.equals("") || pno==null){
                l2[6].setForeground (Color.red);
                l2[6].setVisible(true);
                l3.setText("* necessary field");
                l3.setVisible(true);
                flag = false;
            }
            else if (!pno.matches("\\d+")){
                l2[6].setForeground (Color.red);
                l2[6].setVisible(true);
                l3.setText("* enter digits");
                l3.setVisible(true);
                flag = false;
            }
            else if (pno.length()!=10){
                l2[6].setForeground (Color.red);
                l2[6].setVisible(true);
                l3.setText("* phone no. should be 10 digit");
                l3.setVisible(true);
                flag = false;
            }
            else {
                l2[6].setForeground(Color.green);
                l2[6].setVisible(true);
            }


            /*user-id*/
            String uid = tf[7].getText();
            if (uid.equals("") || uid==null){
                l2[10].setForeground (Color.red);
                l2[10].setVisible(true);
                l3.setText("* necessary field");
                l3.setVisible(true);
                flag = false;
            }
            else{
                l2[10].setForeground (Color.green);
                l2[10].setVisible(true);
            }

            /*income*/
            String inc = tf[6].getText();
            if (inc.equals("") || inc==null){
                l2[9].setForeground (Color.red);
                l2[9].setVisible(true);
                l3.setText("* necessary field");
                l3.setVisible(true);
                flag = false;
            }
            else if (!inc.matches("\\d+")){
                l2[9].setForeground (Color.red);
                l2[9].setVisible(true);
                l3.setText("* enter digits");
                l3.setVisible(true);
                flag = false;
            }
            else{
                l2[9].setForeground (Color.green);
                l2[9].setVisible(true);
            }

            /*gender*/
            String gender = null;
            boolean flag2 = true;
            int option =-1;
            for(int i=0;i<3;i++){
                if (rb[i].isSelected()){
                    flag2 = false;
                    option = i;
                    break;
                }
            }
            if (flag2){
                l2[3].setForeground (Color.red);
                l2[3].setVisible(true);
                l3.setText("* necessary field");
                l3.setVisible(true);
                flag = false;
            }
            else{
                l2[3].setForeground (Color.green);
                l2[3].setVisible(true);
                gender = rb[option].getText();
            }

            /*first name*/
            String fname2 = tf[0].getText();
            String fname = "";
            if (fname2.equals("") || fname2==null){
                l2[0].setForeground (Color.red);
                l2[0].setVisible(true);
                l3.setText("* necessary field");
                l3.setVisible(true);
                flag = false;
            }
            else if (!fname2.matches("^[a-zA-Z]*$")){
                l2[0].setForeground (Color.red);
                l2[0].setVisible(true);
                l3.setText("* first name should contain only alphabets");
                l3.setVisible(true);
                flag = false;
            }
            else{
                l2[0].setForeground (Color.green);
                l2[0].setVisible(true);
                String fname3 = fname2.toLowerCase();
                String s1 = fname3.substring(0, 1).toUpperCase();
                fname = s1 + fname3.substring(1);
            }

            /*last name*/
            String lname2  = tf[2].getText();
            String lname = "";
            if (lname2.equals("") || lname2==null){
                l2[2].setForeground (Color.red);
                l2[2].setVisible(true);
                l3.setText("* necessary field");
                l3.setVisible(true);
                flag = false;
            }
            else if (!lname2.matches("^[a-zA-Z]*$")){
                l3.setText("* last name should contain only alphabets");
                l3.setVisible(true);
                flag = false;
            }
            else{
                l2[2].setForeground (Color.green);
                l2[2].setVisible(true);
                String lname3 = lname2.toLowerCase();
                String s2 = lname3.substring(0, 1).toUpperCase();
                lname = s2 + lname3.substring(1);
            }

            /*middle name*/
            String mname2 = tf[1].getText();
            String mname = "";
            if (mname2.equals("") || mname2==null){
                mname = "";
            }
            else{
                if (!mname2.matches("^[a-zA-Z]*$")){
                    l2[1].setForeground (Color.red);
                    l2[1].setVisible(true);
                    l3.setText("* middle name should contain only alphabets");
                    l3.setVisible(true);
                    flag = false;
                }
                else{
                    l2[1].setForeground (Color.green);
                    l2[1].setVisible(true);
                    String mname3 = mname2.toLowerCase();
                    String s3 = mname3.substring(0, 1).toUpperCase();
                    mname = s3 + mname3.substring(1);
                }
            }

            /*address validation*/
            String add = tf[3].getText();
            if (add.equals("") || add==null){
                l2[5].setForeground (Color.red);
                l2[5].setVisible(true);
                l3.setText("* necessary field");
                l3.setVisible(true);
                flag = false;
            }
            else{
                l2[5].setForeground (Color.green);
                l2[5].setVisible(true);
            }
            /*assessment year*/
            String assessyr = db1.getItemAt(db1.getSelectedIndex());

            /*senior citizen*/
            String sencitizen;
            if (cb1.isSelected()){
                sencitizen = "Y";
            }
            else{
                sencitizen = "N";
            }

            /*email validation*/
            String email = tf[5].getText();
            if (email.equals("") || email==null){
                l2[7].setForeground (Color.red);
                l2[7].setVisible(true);
                l3.setText("* necessary field");
                l3.setVisible(true);
                flag = false;
            }
            else if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")){
                l2[7].setForeground (Color.red);
                l2[7].setVisible(true);
                l3.setText("* invalid e-mail");
                l3.setVisible(true);
                flag = false;
            }
            else{
                l2[7].setForeground (Color.green);
                l2[7].setVisible(true);
            }

            if (flag){
                try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb","root","admin");
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("select userid from users where userid=\""+uid+"\"");
                    if (rs.next()) {
                        l3.setText("* username already exists");
                        l3.setVisible(true);
                        l2[10].setForeground (Color.red);
                        l2[10].setVisible(true);
                        flag = false;
                    }
                    con.close();
                }
                catch(Exception e2){
                    System.out.println(e2);
                }
            }

            if (flag){
                Random rand = new Random();
                int rand_int1 = rand.nextInt(1000000);
                if (rand_int1 < 100000){
                    rand_int1 += 100000;
                }
                //System.out.println(rand_int1);
                //System.out.println(email);
                String msg = "Your six digit verification code is " + rand_int1 + ", don't share your verification code with anyone";
                String sub = "Verify your e-mail address";
                automailer verify = new automailer(email);
                verify.send_user(sub,msg,false,null);

                JPanel panel = new JPanel();
                JLabel label = new JLabel("Enter the 6 digit verification code sent to your e-mail id : ");
                JPasswordField pass = new JPasswordField(10);
                panel.add(label);
                panel.add(pass);
                String[] options = new String[]{"ok"};
                JOptionPane.showOptionDialog(null, panel, "E-mail verification",
                           JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options, options[0]);

                int result = Integer.parseInt(String.valueOf(pass.getPassword()));
                if (result!=rand_int1){
                    JOptionPane.showMessageDialog(null,"Try again","Verification failed",JOptionPane.INFORMATION_MESSAGE);
                    l2[7].setForeground (Color.red);
                    l2[7].setVisible(true);
                    l3.setText("* e-mail not verified");
                    l3.setVisible(true);
                    flag=false;
                }
            }

            if (flag){
                try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb","root","admin");
                    Statement stmt = con.createStatement();
                    String query = "insert into users values(\""+uid+"\",\""+fname+"\",\""+mname+"\",\""+lname+"\",\""+gender+"\",\""+sencitizen+"\",\""+add+"\",\""+pno+"\",\""+email+"\",\""+assessyr+"\",\""+inc+"\",\""+pwd+"\")";
                    stmt.executeUpdate(query);
                    con.close();
                    new login();
                    dispose();

                }
                catch(Exception e2){
                    System.out.println(e2);
                }
            }

        }
        if ("login".equals(cmd)){
            new login();
            dispose();
        }

    }
    public static void main(String[] args) {
        createac se = new createac();
    }
}
