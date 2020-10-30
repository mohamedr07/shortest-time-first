package javaapplication1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class JavaApplication1 extends JFrame {
    
    private int i = 0;
    private int numberOfProcesses;
    private int timeNeeded = 0;
    ArrayList<Process> pro = new ArrayList<Process>();
    int[] completedPro = new int[1000];
    JPanel p = new JPanel();
    
    public static void main(String[] args) {
        new JavaApplication1();
    }
    JavaApplication1() {
        design();
    }   
    private void design() {

        
        JLabel l1 = new JLabel("Number of processes:");
        JTextField tf1 = new JTextField();
        JButton b1 = new JButton("Submit");
        this.add(p);
        p.add(l1);
        p.add(tf1);
        p.add(b1);
        p.setLayout(null);
        tf1.setHorizontalAlignment(SwingConstants.CENTER);
        l1.setHorizontalAlignment(SwingConstants.CENTER);
        l1.setBounds(40, 70, 200, 50);
        tf1.setBounds(110,120,60,20);
        b1.setBounds(90,150,100,20);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            for(int i = 0; i < 1000; i++)
                completedPro[i] = -1;
            numberOfProcesses = Integer.parseInt(tf1.getText());
            getProcesses();            }         
        });
        this.setTitle("SRTF");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(280,500);
        this.setVisible(true);    
    }
    private void getProcesses() {
        p.removeAll();
        p.repaint();
        p.revalidate();
        JLabel l01 = new JLabel("Process number :"+ (i+1));
        JLabel l02 = new JLabel("Arrival Time:");
        JLabel l03 = new JLabel("Burst Time:");
        JTextField tf01 = new JTextField();
        JTextField tf02 = new JTextField();
        JButton b01 = new JButton("Submit");
        p.add(l01);
        p.add(l02);
        p.add(l03);
        p.add(tf01);
        p.add(tf02);
        p.add(b01);
        l01.setBounds(40,70,200,50);
        l02.setBounds(20,120,120,20);
        l03.setBounds(20,150,120,20);
        tf01.setBounds(140,120,120,20);
        tf02.setBounds(140,150,120,20);
        b01.setBounds(90,180,100,20);
        b01.setHorizontalAlignment(SwingConstants.CENTER);
        l01.setHorizontalAlignment(SwingConstants.CENTER);
        b01.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Process x = new Process();
                x.arrivalTime = Integer.parseInt(tf01.getText());
                x.burstTime = Integer.parseInt(tf02.getText());
                x.burstTime2 = Integer.parseInt(tf02.getText());
                pro.add(x);
                i++;
                if(i < numberOfProcesses)
                {
                    getProcesses();
                }
                else
                {
                    i = 0;
                    solve();
                }
            }

        });
    }
    private void showResult(){
        p.removeAll();
        p.repaint();
        p.revalidate();
        
        
        double averageTurnaroundTime = 0;
        double averageWaitingTime = 0;
        
        JPanel p1 = new JPanel(new FlowLayout());
        JPanel p2 = new JPanel(new FlowLayout());
        p.add(p1);
        p.add(p2);
 
        String gTableHeader[] = {"From", "To", "Process"};
        String rTableHeader[] = {"Process", "Arrival", "Burst", "Completed", "Waiting", "Turnaround"};
        
        DefaultTableModel model1 = new DefaultTableModel(gTableHeader, 3);
        DefaultTableModel model2 = new DefaultTableModel(rTableHeader, 6);
        model1.setRowCount(1000);
        model2.setRowCount(1000);
        JTable grantTable = new JTable(model1);
        JTable resultTable = new JTable(model2);     
        
        JScrollPane gTableScrollPane = new JScrollPane(grantTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);   
        JScrollPane rTableScrollPane = new JScrollPane(resultTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);   
        
        DefaultTableModel mod1 = (DefaultTableModel) grantTable.getModel();
        DefaultTableModel mod2 = (DefaultTableModel) resultTable.getModel();
        
        gTableScrollPane.setPreferredSize(new Dimension(260,180));
        p1.add(gTableScrollPane);
        rTableScrollPane.setPreferredSize(new Dimension(260,180));
        p2.add(rTableScrollPane);

        p1.setBounds(10,10,260,180);
        p2.setBounds(10,200,260,180);
        grantTable.setBackground(new Color(116, 121, 125));
        resultTable.setBackground(new Color(116, 121, 125));
        grantTable.getTableHeader().setBackground(new Color(55, 61, 66));
        resultTable.getTableHeader().setBackground(new Color(55, 61, 66));
        grantTable.setForeground(Color.WHITE);
        resultTable.setForeground(Color.WHITE);
        grantTable.getTableHeader().setForeground(Color.WHITE);
        resultTable.getTableHeader().setForeground(Color.WHITE);
        grantTable.setGridColor(new Color(66, 71, 76));
        resultTable.setGridColor(new Color(66, 71, 76)); 
        
        
        for(int j = 0; j < timeNeeded; j++) {
            String x = Integer.toString(completedPro[j]);
            if(x.equals("-1"))
                x = "idle";
            String Row[] = {Integer.toString(j),Integer.toString(j+1),x};
            mod1.insertRow(j, Row);
        }
        for(int j = 0; j < numberOfProcesses; j++)
        {
            pro.get(j).calculate();
            String row[] = {Integer.toString(j+1), Integer.toString(pro.get(j).arrivalTime), 
            Integer.toString(pro.get(j).burstTime2), Integer.toString(pro.get(j).completedTime),
            Integer.toString(pro.get(j).waitingTime), Integer.toString(pro.get(j).turnaroundTime)};
            mod2.insertRow(j, row);
            averageWaitingTime += pro.get(j).waitingTime;
            averageTurnaroundTime += pro.get(j).turnaroundTime;
        }
        String x = String.format("%.6f",(averageWaitingTime / numberOfProcesses));
        String y = String.format("%.6f",(averageTurnaroundTime / numberOfProcesses));
        JLabel wtl = new JLabel("Average Waiting Time:");
        JLabel wtl2 = new JLabel(x);
        JLabel ttl = new JLabel("Average Turnaround Time:");
        JLabel ttl2 = new JLabel(y);
        JButton clearb = new JButton("Clear");
        p.add(wtl);
        p.add(wtl2);
        p.add(ttl);
        p.add(ttl2);
        p.add(clearb);
        wtl.setBounds(10,390,130,20);
        wtl2.setBounds(150,390,130,20);
        ttl.setBounds(10,410,130,20);
        ttl2.setBounds(150,410,130,20);
        clearb.setBounds(90,440,100,20);
        clearb.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                p.removeAll();
                p.repaint();
                p.revalidate();
                i = 0;
                timeNeeded = 0;
                pro.clear();
                design();
            }
            
        });
    }
    private void solve() { 
        int timer = 0, completedProcesses = 0;
        
        while(true) {
            int min = 99;
            int x = numberOfProcesses;
            
            if(completedProcesses == numberOfProcesses)
                break;
            for(int j = 0; j < numberOfProcesses; j++) {
                if((pro.get(j).arrivalTime <= timer) && (pro.get(j).completed == 0) && (pro.get(j).burstTime < min)){
                    min = pro.get(j).burstTime;
                    x = j;
                }
            }
            if(x == numberOfProcesses) {
                timer++;
                timeNeeded++;
            }
            else
            {
                pro.get(x).burstTime--;
                completedPro[timer] = x+1;
                timer++;
                timeNeeded++;
                if(pro.get(x).burstTime == 0)
                {
                    pro.get(x).completed = 1;
                    pro.get(x).completedTime = timer;
                    completedProcesses++;
                }
            }
        }
        showResult();
    }
}
class Process {
    public int arrivalTime;
    public int burstTime;
    public int burstTime2;
    public int waitingTime;
    public int turnaroundTime;
    public int completed = 0;
    public int completedTime;
    void calculate() {
        turnaroundTime = completedTime - arrivalTime;
        waitingTime = turnaroundTime - burstTime2;        
    }
}