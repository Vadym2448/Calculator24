import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends javax.swing.JFrame {


    public Main() {
        initComponents();
        jMenuItem_ON.setEnabled(false);//as
    }

    double num , ans;
    String calculation;


   public static boolean isDelim(char c) {
        return c == ' ';
    }
  public  boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^'
                || c == '√' || c == 't' || c == 's' || c == 'c' || c == 'l'
                || c == '!' || c == 'T' || c == 'S' || c == 'C' || c == 'L';
    }
    static int priority(char op) {
        switch (op) {

            case '+':
            case '-':

                return 1;

            case '*':
            case '/':

                return 2;
            case 's':
            case 'c':
            case 't':
            case 'l':
            case 'T':
            case 'S':
            case 'C':
            case 'L':
            case '√':
            case '!':

                return 3;
            case '^':
                return 4;
            default:
                return -1;
        }
    }
    static void processOperator(LinkedList<Double> st, char op) {
        double l;
        double t;
        switch (op) {
            case '+':
                l = st.removeLast();
                t = st.removeLast();
                st.add(t + l);
                break;

            case '-':
                l = st.removeLast();
                t = st.removeLast();
                st.add(t - l);
                break;

            case '*':
                l = st.removeLast();
                t = st.removeLast();
                st.add(t * l);
                break;

    case '/':
        l = st.removeLast();
        t = st.removeLast();
        try {
        st.add(t / l);
        break;
}catch(Exception e){

}
            case '^':
                l = st.removeLast();
                t = st.removeLast();
                st.add(Math.pow(t ,l));
                break;

            case '!':
                double ret = 1.0;
                t = st.removeLast();

                for (int i = 1; i <= t; ++i) ret *= i;
                st.add(ret);
                break;

            case '√':
                t = st.removeLast();
                st.add(Math.sqrt(t));
                break;

            case ('s'):
                t = st.removeLast();
                st.add(Math.sin(t));
                break;

            case ('S'):
                t = st.removeLast();
                st.add(Math.sinh(t));
                break;

            case ('c'):
                t = st.removeLast();
                st.add(Math.cos(t));
                break;

            case ('C'):
                t = st.removeLast();
                st.add(Math.cosh(t));
                break;

            case ('t'):
                t = st.removeLast();
                st.add(Math.tan(t));
                break;

            case ('T'):
                t = st.removeLast();
                st.add(Math.tanh(t));
                break;

            case ('l'):
                t = st.removeLast();
                st.add(Math.log(t));
                break;

            case ('L'):
                t = st.removeLast();
                st.add(Math.log10(t));
                break;

        }

    }

    public Double eval(StringBuilder s, Double indexOf) {
        LinkedList<Double> st = new LinkedList<Double>();
        LinkedList<Character> op = new LinkedList<Character>();
        String operand = "";
        char y,x,x2;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            y = ' ';
            x = ' ';
            x2 = ' ' ;
            if (i !=s.length()-1){
                i++;
                y =s.charAt(i);
                i--;
            }
             if ( i >= 0) {

                x = s.charAt(i);
                System.out.println("i =" + i +  "x = " + x);

            }
            if (i >= 1){

                i--;
                x2 = s.charAt(i);
                System.out.println("i = "+ i+ "x2 = " + x2);

                i++;
            }
            String t = String.valueOf(c);
            Pattern p = Pattern.compile("^[0-9]*[,.-]?+$");
            Matcher m = p.matcher(t);
            if (isDelim(c))
                continue;
            if (c == '(')
                op.add('(');
            else if (c == ')') {
                while (op.getLast() != '(') processOperator(st, op.removeLast());
                op.removeLast();

            }
            else if (Character.isDigit(c) && (x2 == '+'  || x2 == '*' || x2 == '/') && x == '-') {
                operand += s.charAt(i);
            }
            else if (x2 == '-' && x == '-' ){
             //   operand += '-';
                System.out.println("Два мінуси підряд");
            }

             else if (isOperator(c) && x != '-') {
                  if (c == '!' && Character.isDigit(y)){
                      jLabel1.setForeground(Color.red);
                      jLabel1.setText("Помилка");
                    break;
                }
                if (c == '/' && y == '0'){
                    jLabel1.setForeground(Color.red);
                    jLabel1.setText("Помилка");
                    break;
                }
                while (!op.isEmpty() && priority(op.getLast()) >= priority(c))
                {
                    processOperator(st, op.removeLast());
                }

                op.add(c);

            }

            else if (i == s.length()-1  ){
                operand += s.charAt(i++);
                st.add(Double.parseDouble(operand));
            }
            else  {
                if (( x =='-' && Character.isDigit(x2)  ))
                {
                    op.add('+');
                }
                if (x == '-' && isOperator(x2))
                {

                }
                    while (i < s.length() && m.find() )
                    operand += s.charAt(i++);
                --i;

              /*
              if (y == '+' || y == '-' || y == '*' || y == '/'  || y == '^' || y == '(' || y == ')' || y == '!' ) {
                    st.add(Double.parseDouble(operand));
                    operand="";
                }
                */

                if (!isOperator(c) && isOperator(y) || y =='('|| y ==')' )
                {
                st.add(Double.parseDouble(operand));
                operand="";
                }


            }
        }
        while (!op.isEmpty())
            processOperator(st, op.removeLast());
        return st.get(0);
    }


    public int livesum(){

        Scanner s = new Scanner(jTextField1.getText());
        String b = s.next();
        double x;
        do{

            StringBuilder a = new StringBuilder(b);
            Double mn =eval(a,Double.valueOf(a.indexOf(""+'(')));
            String formattedDouble = String.format("%.1f", mn);
            x = mn;

            if (x % 1 == 0){
                jLabel1.setText(""+eval(a,Double.valueOf(a.indexOf(""+'('))) ) ;
            }

            else {
                jLabel1.setText(formattedDouble);
            }

        }
        while ( (b = s.next()) != "null");

return 0;
    }

    public int mathematic()
    {
        switch (calculation)
        {
            case "1/x":
                ans = 1 / num;
                jLabel1.setText(Double.toString(ans));
                break;

            case "negative_number":
                ans =num * -1;
                jLabel1.setText(Double.toString(ans));
                break;

            case "round":
                ans = Math.round(num);
                jLabel1.setText(Double.toString(ans));
                break;

            case "bin":
                jLabel1.setText(Integer.toString(Integer.parseInt(jTextField1.getText()),2));
                break;

            case "hex":
                jLabel1.setText(Integer.toString(Integer.parseInt(jTextField1.getText()),16));
                break;


            case "octal":
                jLabel1.setText(Integer.toString(Integer.parseInt(jTextField1.getText()),8));
                break;

        }
        return 0;

    }
    public void vkl()
    {
        jTextField1.setEnabled(true);
        jMenu_infa.setEnabled(true);

        jMenuItem_ON.setEnabled(false);
        jMenuItem_OFF.setEnabled(true);

        jButton_clean.setEnabled(true);
        jButton_C.setEnabled(true);
        jButton_1.setEnabled(true);
        jButton_plus.setEnabled(true);
        jButton_2.setEnabled(true);
        jButton_3.setEnabled(true);
        jButton_minus.setEnabled(true);
        jButton_4.setEnabled(true);
        jButton_5.setEnabled(true);
        jButton_6.setEnabled(true);
        jButton_multiplication.setEnabled(true);
        jButton_7.setEnabled(true);
        jButton_8.setEnabled(true);
        jButton_9.setEnabled(true);
        jButton_division.setEnabled(true);
        jButton_0.setEnabled(true);
        jButton_point.setEnabled(true);
        jButton_sum.setEnabled(true);
        jButton_power.setEnabled(true);
        jButton_korin.setEnabled(true);
        jButton_reverse.setEnabled(true);
        jButton_1x.setEnabled(true);
        jButton_P.setEnabled(true);
        jButton_PRC.setEnabled(true);
        jButton_Octal.setEnabled(true);
        jButton_E.setEnabled(true);
        jButton_Log10.setEnabled(true);
        jButton_Hex.setEnabled(true);
        jButton_sinh.setEnabled(true);
        jButton_cos.setEnabled(true);
        jButton_sin.setEnabled(true);
        jButton_cosh.setEnabled(true);
        jButton_tan.setEnabled(true);
        jButton_tanh.setEnabled(true);
        jButton_log.setEnabled(true);
        jButton_Bracket_front.setEnabled(true);
        jButton_Bracket_end.setEnabled(true);
        jButton_Round.setEnabled(true);
        jButton_Bin.setEnabled(true);
        JButton_Factorial.setEnabled(true);
    }

    public void vykl()
    {
        jTextField1.setEnabled(false);
        jMenu_infa.setEnabled(false);

        jMenuItem_OFF.setEnabled(false);
        jMenuItem_ON.setEnabled(true);

        jButton_clean.setEnabled(false);
        jButton_C.setEnabled(false);
        jButton_1.setEnabled(false);
        jButton_plus.setEnabled(false);
        jButton_2.setEnabled(false);
        jButton_3.setEnabled(false);
        jButton_minus.setEnabled(false);
        jButton_4.setEnabled(false);
        jButton_5.setEnabled(false);
        jButton_6.setEnabled(false);
        jButton_multiplication.setEnabled(false);
        jButton_7.setEnabled(false);
        jButton_8.setEnabled(false);
        jButton_9.setEnabled(false);
        jButton_division.setEnabled(false);
        jButton_0.setEnabled(false);
        jButton_point.setEnabled(false);
        jButton_sum.setEnabled(false);
        jButton_power.setEnabled(false);
        jButton_korin.setEnabled(false);
        jButton_reverse.setEnabled(false);
        jButton_1x.setEnabled(false);
        jButton_P.setEnabled(false);
        jButton_PRC.setEnabled(false);
        jButton_Octal.setEnabled(false);
        jButton_E.setEnabled(false);
        jButton_Log10.setEnabled(false);
        jButton_Hex.setEnabled(false);
        jButton_sinh.setEnabled(false);
        jButton_cos.setEnabled(false);
        jButton_sin.setEnabled(false);
        jButton_cosh.setEnabled(false);
        jButton_tan.setEnabled(false);
        jButton_tanh.setEnabled(false);
        jButton_log.setEnabled(false);
        jButton_Bracket_front.setEnabled(false);
        jButton_Bracket_end.setEnabled(false);
        jButton_Round.setEnabled(false);
        jButton_Bin.setEnabled(false);
        JButton_Factorial.setEnabled(false);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jTextField1 = new javax.swing.JTextField();
        jButton_clean = new javax.swing.JButton();
        jButton_C = new javax.swing.JButton();
        jButton_1 = new javax.swing.JButton();
        jButton_plus = new javax.swing.JButton();
        jButton_2 = new javax.swing.JButton();
        jButton_3 = new javax.swing.JButton();
        jButton_minus = new javax.swing.JButton();
        jButton_4 = new javax.swing.JButton();
        jButton_5 = new javax.swing.JButton();
        jButton_6 = new javax.swing.JButton();
        jButton_multiplication = new javax.swing.JButton();
        jButton_7 = new javax.swing.JButton();
        jButton_8 = new javax.swing.JButton();
        jButton_9 = new javax.swing.JButton();
        jButton_division = new javax.swing.JButton();
        jButton_0 = new javax.swing.JButton();
        jButton_point = new javax.swing.JButton();
        jButton_sum = new javax.swing.JButton();

        jLabel1 = new javax.swing.JLabel();
        //jLabel1 label = new javax.swing.JLabel();

        jButton_power = new javax.swing.JButton();
        jButton_korin = new javax.swing.JButton();
        jButton_reverse = new javax.swing.JButton();
        jButton_1x = new javax.swing.JButton();
        jButton_P = new javax.swing.JButton();
        jButton_cos = new javax.swing.JButton();
        jButton_sinh = new javax.swing.JButton();
        jButton_log = new javax.swing.JButton();
        jButton_tan = new javax.swing.JButton();
        jButton_tanh = new javax.swing.JButton();
        jButton_sin = new javax.swing.JButton();
        jButton_cosh = new javax.swing.JButton();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jTextField2 = new javax.swing.JTextField();
        jButton_E = new javax.swing.JButton();
        jButton_Bracket_front = new javax.swing.JButton();
        jButton_Bracket_end = new javax.swing.JButton();
        jButton_Round = new javax.swing.JButton();
        jButton_PRC = new javax.swing.JButton();
        jButton_Log10 = new javax.swing.JButton();
        jButton_Bin = new javax.swing.JButton();
        jButton_Octal = new javax.swing.JButton();
        jButton_Hex = new javax.swing.JButton();
        JButton_Factorial = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu_Kalculator = new javax.swing.JMenu();
        jMenuItem_ON = new javax.swing.JMenuItem();
        jMenuItem_OFF = new javax.swing.JMenuItem();
        jMenuItem_close = new javax.swing.JMenuItem();
        jMenu_view = new javax.swing.JMenu();
        jMenuItem_standart = new javax.swing.JMenuItem();
        jMenuItem_sientific = new javax.swing.JMenuItem();
        jMenu_infa = new javax.swing.JMenu();
        jMenuItem_info = new javax.swing.JMenuItem();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Калькулятор");
        setFocusable(false);
        setLocation(new java.awt.Point(500, 250));
        setType(java.awt.Window.Type.UTILITY);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        getContentPane().setLayout(null);

        jTextField1.setFont(new java.awt.Font("Times New Roman", 3, 23)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField1.setMaximumSize(new java.awt.Dimension(488, 45));
        jTextField1.setMinimumSize(new java.awt.Dimension(221, 45));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1_ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1_KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1_KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1_KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField1);
        jTextField1.setBounds(10, 11, 220, 45);

        jButton_clean.setFont(new java.awt.Font("Thames", 1, 16)); // NOI18N
        jButton_clean.setText("<--");
        jButton_clean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_clean_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_clean);
        jButton_clean.setBounds(67, 90, 51, 56);

        jButton_C.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton_C.setText("C");
        jButton_C.setToolTipText("CTRL+C");
        jButton_C.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_C_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_C);
        jButton_C.setBounds(124, 90, 51, 56);

        jButton_1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton_1.setText("1");
        jButton_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_1_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_1);
        jButton_1.setBounds(10, 152, 51, 33);

        jButton_plus.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton_plus.setText("+");
        jButton_plus.setToolTipText("");
        jButton_plus.setRequestFocusEnabled(false);
        jButton_plus.setRolloverEnabled(false);
        jButton_plus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_plus_ActionPerformed(evt);
            }
        });
        jButton_plus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton_plus_KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jButton_plus_KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jButton_plus_KeyTyped(evt);
            }
        });
        getContentPane().add(jButton_plus);
        jButton_plus.setBounds(181, 90, 51, 56);

        jButton_2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton_2.setText("2");
        jButton_2.setToolTipText("");
        jButton_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_2_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_2);
        jButton_2.setBounds(67, 152, 51, 33);

        jButton_3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton_3.setText("3");
        jButton_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_3_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_3);
        jButton_3.setBounds(124, 152, 51, 33);

        jButton_minus.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton_minus.setText("-");
        jButton_minus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_minus_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_minus);
        jButton_minus.setBounds(181, 152, 51, 33);

        jButton_4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton_4.setText("4");
        jButton_4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_4_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_4);
        jButton_4.setBounds(10, 191, 51, 33);

        jButton_5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton_5.setText("5");
        jButton_5.setToolTipText("");
        jButton_5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_5_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_5);
        jButton_5.setBounds(67, 191, 51, 33);

        jButton_6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton_6.setText("6");
        jButton_6.setToolTipText("");
        jButton_6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_6_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_6);
        jButton_6.setBounds(124, 191, 51, 33);

        jButton_multiplication.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton_multiplication.setText("*");
        jButton_multiplication.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_multiplication_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_multiplication);
        jButton_multiplication.setBounds(181, 191, 51, 33);

        jButton_7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton_7.setText("7");
        jButton_7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_7_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_7);
        jButton_7.setBounds(10, 230, 51, 33);

        jButton_8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton_8.setText("8");
        jButton_8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_8_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_8);
        jButton_8.setBounds(67, 230, 51, 33);

        jButton_9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton_9.setText("9");
        jButton_9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_9_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_9);
        jButton_9.setBounds(124, 230, 51, 33);

        jButton_division.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton_division.setText("/");
        jButton_division.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_division_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_division);
        jButton_division.setBounds(181, 230, 51, 33);

        jButton_0.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton_0.setText("0");
        jButton_0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_0_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_0);
        jButton_0.setBounds(10, 269, 51, 33);

        jButton_point.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton_point.setText(".");
        jButton_point.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_point_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_point);
        jButton_point.setBounds(67, 269, 51, 33);

        jButton_sum.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton_sum.setText("=");
        jButton_sum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_sum_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_sum);
        jButton_sum.setBounds(124, 269, 109, 33);
        jLabel1.setForeground(Color.black);
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("0");
        jLabel1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jLabel1_AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 62, 220, 22);

        jButton_power.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton_power.setText("^");
        jButton_power.setToolTipText("Shift+S");
        jButton_power.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_power_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_power);
        jButton_power.setBounds(376, 222, 70, 37);

        jButton_korin.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton_korin.setText("√");
        jButton_korin.setToolTipText("Shift+K");
        jButton_korin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_korin_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_korin);
        jButton_korin.setBounds(450, 222, 61, 37);

        jButton_reverse.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton_reverse.setText("±");
        jButton_reverse.setToolTipText("Shift+F");
        jButton_reverse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_reverse_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_reverse);
        jButton_reverse.setBounds(376, 265, 70, 37);

        jButton_1x.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton_1x.setText("1/x");
        jButton_1x.setToolTipText("Shift+D");
        jButton_1x.setAlignmentY(0.0F);
        jButton_1x.setMaximumSize(new java.awt.Dimension(49, 33));
        jButton_1x.setMinimumSize(new java.awt.Dimension(49, 33));
        jButton_1x.setPreferredSize(new java.awt.Dimension(49, 33));
        jButton_1x.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_1x_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_1x);
        jButton_1x.setBounds(450, 265, 61, 37);

        jButton_P.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton_P.setText("П");
        jButton_P.setToolTipText("Ctrl+P");
        jButton_P.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_P_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_P);
        jButton_P.setBounds(450, 179, 61, 37);

        jButton_cos.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton_cos.setText("cos");
        jButton_cos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_cos_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_cos);
        jButton_cos.setBounds(239, 222, 65, 37);

        jButton_sinh.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton_sinh.setText("sinh");
        jButton_sinh.setMaximumSize(new java.awt.Dimension(60, 23));
        jButton_sinh.setMinimumSize(new java.awt.Dimension(60, 23));
        jButton_sinh.setPreferredSize(new java.awt.Dimension(51, 23));
        jButton_sinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_sinh_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_sinh);
        jButton_sinh.setBounds(310, 179, 60, 37);

        jButton_log.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton_log.setText("log");
        jButton_log.setPreferredSize(new java.awt.Dimension(51, 23));
        jButton_log.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_log_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_log);
        jButton_log.setBounds(310, 131, 60, 42);

        jButton_tan.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton_tan.setText("tan");
        jButton_tan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_tan_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_tan);
        jButton_tan.setBounds(239, 265, 65, 37);

        jButton_tanh.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton_tanh.setText("tanh");
        jButton_tanh.setPreferredSize(new java.awt.Dimension(60, 23));
        jButton_tanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_tanh_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_tanh);
        jButton_tanh.setBounds(310, 265, 60, 37);

        jButton_sin.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton_sin.setText("sin");
        jButton_sin.setPreferredSize(new java.awt.Dimension(51, 23));
        jButton_sin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_sin_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_sin);
        jButton_sin.setBounds(239, 179, 65, 37);

        jButton_cosh.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton_cosh.setText("cosh");
        jButton_cosh.setMaximumSize(new java.awt.Dimension(60, 23));
        jButton_cosh.setPreferredSize(new java.awt.Dimension(51, 23));
        jButton_cosh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_cosh_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_cosh);
        jButton_cosh.setBounds(310, 222, 60, 37);

        jInternalFrame1.setVisible(true);
        getContentPane().add(jInternalFrame1);
        jInternalFrame1.setBounds(0, 0, 0, 0);

        jButton_E.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton_E.setText("e");
        jButton_E.setToolTipText("Ctrl+E");
        jButton_E.setMaximumSize(new java.awt.Dimension(49, 33));
        jButton_E.setMinimumSize(new java.awt.Dimension(49, 33));
        jButton_E.setPreferredSize(new java.awt.Dimension(49, 33));
        jButton_E.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_E_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_E);
        jButton_E.setBounds(376, 179, 70, 37);

        jButton_Bracket_front.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton_Bracket_front.setText("(");
        jButton_Bracket_front.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Bracket_front_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_Bracket_front);
        jButton_Bracket_front.setBounds(10, 90, 51, 27);

        jButton_Bracket_end.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton_Bracket_end.setText(")");
        jButton_Bracket_end.setMinimumSize(new java.awt.Dimension(37, 27));
        jButton_Bracket_end.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Bracket_endActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_Bracket_end);
        jButton_Bracket_end.setBounds(10, 123, 51, 23);

        jButton_Round.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton_Round.setText("round");
        jButton_Round.setPreferredSize(new java.awt.Dimension(51, 23));
        jButton_Round.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_log3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_Round);
        jButton_Round.setBounds(239, 131, 65, 42);

        jButton_PRC.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton_PRC.setText("PRC");
        jButton_PRC.setToolTipText("Точність суми");
        jButton_PRC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_PRC_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_PRC);
        jButton_PRC.setBounds(450, 131, 61, 42);

        jButton_Log10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton_Log10.setText("log10");
        jButton_Log10.setToolTipText("");
        jButton_Log10.setMaximumSize(new java.awt.Dimension(49, 33));
        jButton_Log10.setMinimumSize(new java.awt.Dimension(49, 33));
        jButton_Log10.setPreferredSize(new java.awt.Dimension(49, 33));
        jButton_Log10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Log10_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_Log10);
        jButton_Log10.setBounds(376, 131, 70, 42);

        jButton_Bin.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton_Bin.setText("bin");
        jButton_Bin.setPreferredSize(new java.awt.Dimension(51, 23));
        jButton_Bin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Bin_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_Bin);
        jButton_Bin.setBounds(310, 90, 60, 35);

        jButton_Octal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton_Octal.setText("octal");
        jButton_Octal.setToolTipText("");
        jButton_Octal.setMaximumSize(new java.awt.Dimension(53, 23));
        jButton_Octal.setMinimumSize(new java.awt.Dimension(53, 23));
        jButton_Octal.setPreferredSize(new java.awt.Dimension(53, 23));
        jButton_Octal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Octal_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_Octal);
        jButton_Octal.setBounds(380, 90, 60, 35);

        jButton_Hex.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton_Hex.setText("hex");
        jButton_Hex.setToolTipText("");
        jButton_Hex.setMaximumSize(new java.awt.Dimension(53, 33));
        jButton_Hex.setMinimumSize(new java.awt.Dimension(53, 33));
        jButton_Hex.setPreferredSize(new java.awt.Dimension(53, 33));
        jButton_Hex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Hex_ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_Hex);
        jButton_Hex.setBounds(450, 90, 60, 33);

        JButton_Factorial.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        JButton_Factorial.setText("!n");
        JButton_Factorial.setPreferredSize(new java.awt.Dimension(51, 23));
        JButton_Factorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Factorial_ActionPerformed(evt);
            }
        });
        getContentPane().add(JButton_Factorial);
        JButton_Factorial.setBounds(239, 90, 65, 35);

        jMenu_Kalculator.setText("Калькулятор");

        jMenuItem_ON.setText("Вкл");
        jMenuItem_ON.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_ON_ActionPerformed(evt);
            }
        });
        jMenu_Kalculator.add(jMenuItem_ON);

        jMenuItem_OFF.setText("Викл");
        jMenuItem_OFF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_OFF_ActionPerformed(evt);
            }
        });
        jMenu_Kalculator.add(jMenuItem_OFF);

        jMenuItem_close.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem_close.setText("Закрити");
        jMenuItem_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_close_ActionPerformed(evt);
            }
        });
        jMenu_Kalculator.add(jMenuItem_close);

        jMenuBar1.add(jMenu_Kalculator);

        jMenu_view.setText("Вид");

        jMenuItem_standart.setText("Стандартний");
        jMenuItem_standart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_standart_ActionPerformed(evt);
            }
        });
        jMenu_view.add(jMenuItem_standart);

        jMenuItem_sientific.setText("Науковий");
        jMenuItem_sientific.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_sientific_ActionPerformed(evt);
            }
        });
        jMenu_view.add(jMenuItem_sientific);

        jMenuBar1.add(jMenu_view);

        jMenu_infa.setText("Інфо");

        jMenuItem_info.setText("Про програму");
        jMenuItem_info.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_info_ActionPerformed(evt);
            }
        });
        jMenu_infa.add(jMenuItem_info);

        jMenuBar1.add(jMenu_infa);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_1_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_1ActionPerformed
        jTextField1.setText(jTextField1.getText() + "1");
        livesum();
    }//GEN-LAST:event_jButton_1ActionPerformed

    private void jButton_2_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_2ActionPerformed
        jTextField1.setText(jTextField1.getText() + "2");
        livesum();
    }//GEN-LAST:event_jButton_2ActionPerformed

    private void jButton_3_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_3ActionPerformed
        jTextField1.setText(jTextField1.getText() + "3");
        livesum();
    }//GEN-LAST:event_jButton_3ActionPerformed

    private void jButton_4_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_4ActionPerformed
        jTextField1.setText(jTextField1.getText() + "4");
        livesum();
    }//GEN-LAST:event_jButton_4ActionPerformed

    private void jButton_5_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_5ActionPerformed
        jTextField1.setText(jTextField1.getText() + "5");
        livesum();
    }//GEN-LAST:event_jButton_5ActionPerformed

    private void jButton_6_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_6ActionPerformed
        jTextField1.setText(jTextField1.getText() + "6");
        livesum();
    }//GEN-LAST:event_jButton_6ActionPerformed

    private void jButton_7_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_7ActionPerformed
        jTextField1.setText(jTextField1.getText() + "7");
        livesum();
    }//GEN-LAST:event_jButton_7ActionPerformed

    private void jButton_8_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_8ActionPerformed
        jTextField1.setText(jTextField1.getText() + "8");
        livesum();
    }//GEN-LAST:event_jButton_8ActionPerformed

    private void jButton_9_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_9ActionPerformed
        jTextField1.setText(jTextField1.getText() + "9");
        livesum();
    }//GEN-LAST:event_jButton_9ActionPerformed

    private void jButton_0_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_0ActionPerformed
        jTextField1.setText(jTextField1.getText() + "0");
        livesum();
    }//GEN-LAST:event_jButton_0ActionPerformed

    private void jButton_point_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_pointActionPerformed
        jTextField1.setText(jTextField1.getText() + ".");
    }//GEN-LAST:event_jButton_pointActionPerformed

    private void jButton_division_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_divisionActionPerformed
        jTextField1.setText(jTextField1.getText() + "/");
    }//GEN-LAST:event_jButton_divisionActionPerformed

    private void jButton_multiplication_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_multiplicationActionPerformed
        jTextField1.setText(jTextField1.getText() + "*");
    }//GEN-LAST:event_jButton_multiplicationActionPerformed

    private void jButton_minus_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_minusActionPerformed
        jTextField1.setText(jTextField1.getText() + "-");
    }//GEN-LAST:event_jButton_minusActionPerformed

    private void jButton_plus_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_plusActionPerformed
        jTextField1.setText(jTextField1.getText() + "+");
    }//GEN-LAST:event_jButton_plusActionPerformed

    private void jButton_C_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CActionPerformed
        jTextField1.setText("");
        jLabel1.setText("");
        jLabel1.setForeground(Color.black);
    }//GEN-LAST:event_jButton_CActionPerformed

    private void jButton_clean_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_cleanActionPerformed
        int length = jTextField1.getText().length();
        int number = jTextField1.getText().length() - 1;
        if (jTextField1.getText().length() == 1)
        {
            jLabel1.setText("");
            jLabel1.setForeground(Color.black);
        }
        String store;

        if(length > 0)
        {
            StringBuilder back = new StringBuilder(jTextField1.getText());
            back.deleteCharAt(number);
            store = back.toString();
            jTextField1.setText(store);
        }
    }//GEN-LAST:event_jButton_cleanActionPerformed

    @SuppressWarnings("empty-statement")
    private void jButton_sum_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_sumActionPerformed

        Scanner s = new Scanner(jTextField1.getText());
        String b = s.next();
        double x;
        do{
            StringBuilder a = new StringBuilder(b);
            Double mn =eval(a,Double.valueOf(a.indexOf(""+'(')));
            String formattedDouble = String.format("%.1f", mn);
            x = mn;

            if (x % 1 == 0)
            {
                jTextField1.setText(""+eval(a,Double.valueOf(a.indexOf(""+'('))) ) ;
                jLabel1.setText("");
            }

            else{
                jTextField1.setText(formattedDouble);
                jLabel1.setText("");
            }

        }
        while ( (b = s.next()) != "null");
    }//GEN-LAST:event_jButton_sumActionPerformed

    private void jTextField1_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton_power_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_powerActionPerformed
        jTextField1.setText(jTextField1.getText() + "^");
    }//GEN-LAST:event_jButton_powerActionPerformed

    private void jButton_korin_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_korinActionPerformed
        jTextField1.setText(jTextField1.getText() + "√");
    }//GEN-LAST:event_jButton_korinActionPerformed

    private void jButton_Factorial_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_log5ActionPerformed
        jTextField1.setText(jTextField1.getText() + "!");
        livesum();
    }//GEN-LAST:event_jButton_log5ActionPerformed

    private void jButton_reverse_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_reverseActionPerformed
        num = Double.parseDouble(jTextField1.getText());
        calculation = "negative_number";
        mathematic();
    }//GEN-LAST:event_jButton_reverseActionPerformed

    private void jButton_1x_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_1xActionPerformed
        num = Double.parseDouble(jTextField1.getText());
        calculation = "1/x";
        mathematic();
    }//GEN-LAST:event_jButton_1xActionPerformed

    private void jButton_P_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_PActionPerformed
        jTextField1.setText(jTextField1.getText() + Math.PI);
    }//GEN-LAST:event_jButton_PActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        this.setResizable(true);
        this.setSize(255, 380);

    }//GEN-LAST:event_formWindowActivated

    private void jButton_log_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_logActionPerformed
        jTextField1.setText(jTextField1.getText() + "l");
    }//GEN-LAST:event_jButton_logActionPerformed

    private void jButton_tan_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_tanActionPerformed
        jTextField1.setText(jTextField1.getText() + "t");
    }//GEN-LAST:event_jButton_tanActionPerformed

    private void jButton_tanh_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_tanhActionPerformed
        jTextField1.setText(jTextField1.getText() + "T");
    }//GEN-LAST:event_jButton_tanhActionPerformed

    private void jButton_sin_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_sinActionPerformed
        jTextField1.setText(jTextField1.getText() + "s");
    }//GEN-LAST:event_jButton_sinActionPerformed

    private void jButton_cosh_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_coshActionPerformed
        jTextField1.setText(jTextField1.getText() + "C");
    }//GEN-LAST:event_jButton_coshActionPerformed

    private void jButton_sinh_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_sinhActionPerformed
        jTextField1.setText(jTextField1.getText() + "S");
    }//GEN-LAST:event_jButton_sinhActionPerformed

    private void jButton_cos_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_cosActionPerformed
        jTextField1.setText(jTextField1.getText() + "c");
    }//GEN-LAST:event_jButton_cosActionPerformed

    private void jTextField1_KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        char non =evt.getKeyChar();
        if((Character.isDigit(non)) ){
            evt.consume();
        }
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jLabel1_AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel1AncestorAdded

    }//GEN-LAST:event_jLabel1AncestorAdded

    private void jButton_plus_KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton_plusKeyPressed

    }//GEN-LAST:event_jButton_plusKeyPressed

    private void jButton_plus_KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton_plusKeyReleased

    }//GEN-LAST:event_jButton_plusKeyReleased

    private void jButton_plus_KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton_plusKeyTyped
    }//GEN-LAST:event_jButton_plusKeyTyped

    private void jTextField1_KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed

        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER ){
            Scanner s = new Scanner(jTextField1.getText());
            String b = s.next();
            double x;
            do{
                StringBuilder a = new StringBuilder(b);
                Double mn =eval(a,Double.valueOf(a.indexOf(""+'(')));
                String formattedDouble = String.format("%.1f", mn);
                x = mn;

                if (x % 1 == 0)
                {
                    jTextField1.setText(""+eval(a,Double.valueOf(a.indexOf(""+'('))) ) ;
                    jLabel1.setText("");
                }

                else{
                    jTextField1.setText(formattedDouble);
                    jLabel1.setText("");
                }

            }
            while ( (b = s.next()) != "null");

        }

        else if((evt.getKeyCode()==67 && (evt.isControlDown() ) )){
            jTextField1.setText("");
            jLabel1.setText("");

        }
        else if(evt.getKeyCode()==80 && (evt.isControlDown() ) ){
            jTextField1.setText(jTextField1.getText() + Math.PI);

        }
        else if(evt.getKeyCode()==101 && (evt.isControlDown() ) ){
            jTextField1.setText(jTextField1.getText() + Math.exp(1));

        }
        else if(evt.getKeyCode()==75 && (evt.isControlDown() ) ){
            jTextField1.setText(jTextField1.getText() + "√");
        }

        else  if(c ==KeyEvent.VK_1 ){
            jTextField1.setText(jTextField1.getText() + "1");
            livesum();
        }
        else if(c ==KeyEvent.VK_2 ){
            jTextField1.setText(jTextField1.getText() + "2");
            livesum();
        }
        else if(c ==KeyEvent.VK_3 ){
            jTextField1.setText(jTextField1.getText() + "3");
            livesum();
        }
        else if(c ==KeyEvent.VK_4 ){
            jTextField1.setText(jTextField1.getText() + "4");
            livesum();
        }
        else if(c ==KeyEvent.VK_5 ){
            jTextField1.setText(jTextField1.getText() + "5");
            livesum();
        }
        else if(c ==KeyEvent.VK_6 ){
            jTextField1.setText(jTextField1.getText() + "6");
            livesum();
        }
        else if(c ==KeyEvent.VK_7 ){
            jTextField1.setText(jTextField1.getText() + "7");
            livesum();
        }
        else if(c ==KeyEvent.VK_8 ){
            jTextField1.setText(jTextField1.getText() + "8");
            livesum();
        }
        else if(c ==KeyEvent.VK_9 ){
            jTextField1.setText(jTextField1.getText() + "9");
            livesum();
        }
        else if(c ==KeyEvent.VK_0 ){
            jTextField1.setText(jTextField1.getText() + "0");
            livesum();
        }

        else if(evt.getKeyCode()==49 && (evt.isControlDown() ) ){
            jTextField1.setText(jTextField1.getText());
           // livesum();
        }

    }//GEN-LAST:event_jTextField1KeyPressed
    private void jTextField1_KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jMenuItem_info_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_infoActionPerformed
    }//GEN-LAST:event_jMenuItem_infoActionPerformed

    private void jMenuItem_standart_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_standartActionPerformed
        this.setResizable(true);
        this.setSize(255, 380);
        jTextField1.setSize(220,45);
        jLabel1.setSize(220,22);

    }//GEN-LAST:event_jMenuItem_standartActionPerformed

    private void jMenuItem_sientific_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_sientificActionPerformed
        this.setResizable(true);
        this.setSize(538, 380);
        jTextField1.setSize(503,45);
        jLabel1.setSize(503,22);

    }//GEN-LAST:event_jMenuItem_sientificActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed

    }//GEN-LAST:event_formWindowClosed

    private void jMenuItem_close_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_closeActionPerformed
        setVisible(false);
    }//GEN-LAST:event_jMenuItem_closeActionPerformed

    private void jMenuItem_ON_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_ONActionPerformed
        vkl();
    }//GEN-LAST:event_jMenuItem_ONActionPerformed

    private void jMenuItem_OFF_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_OFFActionPerformed
        vykl();
    }//GEN-LAST:event_jMenuItem_OFFActionPerformed

    private void jButton_E_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_EActionPerformed
        jTextField1.setText(jTextField1.getText() + Math.exp(1));
    }//GEN-LAST:event_jButton_EActionPerformed

    private void jButton_Bracket_front_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_log1ActionPerformed
        jTextField1.setText(jTextField1.getText() + "(");
    }//GEN-LAST:event_jButton_log1ActionPerformed

    private void jButton_Bracket_endActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_log2ActionPerformed
        jTextField1.setText(jTextField1.getText() + ")");
    }//GEN-LAST:event_jButton_log2ActionPerformed

    private void jButton_log3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_log3ActionPerformed
        num = Double.parseDouble(jTextField1.getText());
        calculation = "round";
        mathematic();
    }//GEN-LAST:event_jButton_log3ActionPerformed

    private void jButton_PRC_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_P1ActionPerformed
        Scanner s = new Scanner(jTextField1.getText());
        String b = s.next();

        do{
            StringBuilder a = new StringBuilder(b);
            jLabel1.setText(""+eval(a,Double.valueOf(a.indexOf(""+'('))) ) ;
        }
        while ( (b = s.next()) != "null");
    }//GEN-LAST:event_jButton_P1ActionPerformed

    private void jButton_Log10_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_E1ActionPerformed
        jTextField1.setText(jTextField1.getText() + "L");
    }//GEN-LAST:event_jButton_E1ActionPerformed

    private void jButton_Bin_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_log4ActionPerformed
        num = Double.parseDouble(jTextField1.getText());
        calculation = "bin";
        mathematic();
    }//GEN-LAST:event_jButton_log4ActionPerformed

    private void jButton_Octal_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_P2ActionPerformed
        num = Double.parseDouble(jTextField1.getText());
        calculation = "octal";
        mathematic();
    }//GEN-LAST:event_jButton_P2ActionPerformed

    private void jButton_Hex_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_E2ActionPerformed
        num = Double.parseDouble(jTextField1.getText());
        calculation = "hex";
        mathematic();
    }//GEN-LAST:event_jButton_E2ActionPerformed

    public static void main(String args[]) {


        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html */

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;//
    private javax.swing.JButton jButton_0;
    private javax.swing.JButton jButton_1;
    private javax.swing.JButton jButton_1x;
    private javax.swing.JButton jButton_2;
    private javax.swing.JButton jButton_3;
    private javax.swing.JButton jButton_4;
    private javax.swing.JButton jButton_5;
    private javax.swing.JButton jButton_6;
    private javax.swing.JButton jButton_7;
    private javax.swing.JButton jButton_8;
    private javax.swing.JButton jButton_9;
    private javax.swing.JButton jButton_C;
    private javax.swing.JButton jButton_E;
    private javax.swing.JButton jButton_Log10;
    private javax.swing.JButton jButton_Hex;
    private javax.swing.JButton jButton_P;
    private javax.swing.JButton jButton_PRC;
    private javax.swing.JButton jButton_Octal;
    private javax.swing.JButton jButton_clean;
    private javax.swing.JButton jButton_cos;
    private javax.swing.JButton jButton_cosh;
    private javax.swing.JButton jButton_division;
    private javax.swing.JButton jButton_korin;
    private javax.swing.JButton jButton_log;
    private javax.swing.JButton jButton_Bracket_front;
    private javax.swing.JButton jButton_Bracket_end;
    private javax.swing.JButton jButton_Round;
    private javax.swing.JButton jButton_Bin;
    private javax.swing.JButton JButton_Factorial;
    private javax.swing.JButton jButton_minus;
    private javax.swing.JButton jButton_multiplication;
    private javax.swing.JButton jButton_plus;
    private javax.swing.JButton jButton_point;
    private javax.swing.JButton jButton_power;
    private javax.swing.JButton jButton_reverse;
    private javax.swing.JButton jButton_sin;
    private javax.swing.JButton jButton_sinh;
    private javax.swing.JButton jButton_sum;
    private javax.swing.JButton jButton_tan;
    private javax.swing.JButton jButton_tanh;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem_OFF;
    private javax.swing.JMenuItem jMenuItem_ON;
    private javax.swing.JMenuItem jMenuItem_close;
    private javax.swing.JMenuItem jMenuItem_info;
    private javax.swing.JMenuItem jMenuItem_sientific;
    private javax.swing.JMenuItem jMenuItem_standart;
    private javax.swing.JMenu jMenu_Kalculator;
    private javax.swing.JMenu jMenu_infa;
    private javax.swing.JMenu jMenu_view;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables

    private String eval(StringBuilder a, int indexOf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

