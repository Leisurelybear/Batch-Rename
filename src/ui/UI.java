/**
 * FileName: UI
 * Author:   jason
 * Date:     2019/8/25 12:50
 * Description:
 */
package ui;

import utlis.MatchCopyFilename;
import utlis.TextBorderUtlis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UI extends JFrame {
    private static final long serialVersionUID = 1L;

    // 定义组件
    private JButton button1 = new JButton("选择或复制视频所在目录");
    private JButton button2 = new JButton("选择或复制字幕文件目录");
    private JTextArea jtext1 = new JTextArea(1, 2);
    private JTextArea jtext2 = new JTextArea(1, 2);
    private JButton btn_exec = new JButton("执行");


    final private static int HEIGHT = 30;//固定组件长度

    public UI() {
        this.setTitle("批量修改字幕名");//设置主窗口标题

        Toolkit took = Toolkit.getDefaultToolkit();
        //设置窗口图标
        //要用绝对路径
        Image image = took.getImage("src/img/icon.jpg");
        setIconImage(image);



        JOptionPane.showMessageDialog(null, "请保证选择文件夹的正确性！因为此文件名修改不具备回滚功能\n点击\"确定\"继续", "提示", JOptionPane.INFORMATION_MESSAGE);
        setAttrJText1(jtext1);//设置jt1的属性
        clickOpenDir(button1, jtext1);//点击选择按钮，弹出选择目录

        setAttrJText1(jtext2);//设置jt1的属性
        clickOpenDir(button2, jtext2);//点击选择按钮，弹出选择目录
        // 设置布局管理器(Jpanel默认流布局)

        this.setLayout(null);//设置布局为null，即手动设置大小
        this.add(button1);
        button1.setBounds(5, 5, 200, HEIGHT);

        JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setBounds(10 + button1.getWidth(), 5, 600, HEIGHT+10);
        this.add(scrollPane1);
        jtext1.setBounds(10 + button1.getWidth(), 5, 600, HEIGHT);
        scrollPane1.setViewportView(jtext1);

        this.add(button2);
        button2.setBounds(5, 10 + HEIGHT, 200, HEIGHT);

        JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setBounds(10 + button2.getWidth(), 10 + HEIGHT, 600, HEIGHT+10);
        this.add(scrollPane2);
        jtext2.setBounds(10 + button2.getWidth(), 10 + HEIGHT, 600, HEIGHT);
        scrollPane2.setViewportView(jtext2);


        this.add(btn_exec);//执行按钮
        btn_exec.setBounds(20 + button1.getWidth() + jtext1.getWidth(), 10, 150, HEIGHT * 2);


        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(5, 30 + HEIGHT * 2, 970, HEIGHT * 10);
        this.add(scrollPane);
        JTextArea log = new JTextArea(5, 10);
        setAttrLog(log);
        log.setBounds(5, 30 + HEIGHT * 2, 970, HEIGHT * 10);
        scrollPane.setViewportView(log);


        ///////////////////////////////////////////////////
        clickExec(btn_exec, jtext1, jtext2, log);//执行按钮点击

        // 设置窗体
        this.setSize(1000, 600-HEIGHT * 5);// 窗体大小
        this.setLocationRelativeTo(null);//居中显示窗体
        this.setVisible(true);// 显示
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 退出窗体后将JFrame同时关闭
    }

    //鼠标点击按钮事件
    private static void clickExec(JButton jButton, JTextArea sourceDir, JTextArea targetDir, JTextArea logArea) {//把选中的目录放在框中

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sourceDir.getText().equals("请选择或者输入目录，如：C:/folder") || targetDir.getText().equals("") ||
                        targetDir.getText().equals("请选择或者输入目录，如：C:/folder") || sourceDir.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "请选择目录！", "提示", JOptionPane.INFORMATION_MESSAGE);

                    return;//目录为空
                }
                try {

                    System.err.println(sourceDir.getText() + "---" + targetDir.getText());
                    MatchCopyFilename matchCopyFilename = new MatchCopyFilename(sourceDir.getText(), targetDir.getText());
                    StringBuffer log = matchCopyFilename.renameToTargetNames();
                    logArea.setText(log.toString());
                } catch (Exception exc) {
                    System.out.println("选择目录为空！");
                    exc.printStackTrace();
                }
            }
        });
    }

    private static void clickOpenDir(JButton jButton, JTextArea jtext) {//把选中的目录放在框中
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser ifc = new JFileChooser();
                ifc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                ifc.showOpenDialog(new JLabel());
                try {
                    jtext.setText(ifc.getSelectedFile().toString());//把选择的目录放在其中
                } catch (Exception exc) {
                    System.out.println("选择目录为空！");
                    exc.printStackTrace();
                }
            }
        });
    }

    private static void setAttrLog(JTextArea jtext1) {
        TextBorderUtlis border = new TextBorderUtlis(new Color(0, 100, 100), 5, true);
        jtext1.setBorder(border);
        jtext1.setSelectedTextColor(new Color(654321));
        jtext1.setSelectionColor(new Color(12345678));
        jtext1.setLineWrap(true);        //激活自动换行功能
        jtext1.setWrapStyleWord(true);            // 激活断行不断字功能
        jtext1.setFont(new Font("Serif", 0, 15));
        jtext1.setEditable(false);//是否可以编辑
    }

    private static void setAttrJText1(JTextArea jtext1) {
        jtext1.setSize(50, 60);
        TextBorderUtlis border = new TextBorderUtlis(new Color(255, 100, 100), 2, true);
        jtext1.setBorder(border);
        jtext1.setSelectedTextColor(new Color(654321));
        jtext1.setSelectionColor(new Color(12345678));
        //jtext1.setLineWrap(true);        //激活自动换行功能
        jtext1.setWrapStyleWord(true);            // 激活断行不断字功能
        jtext1.setFont(new Font("Serif", 0, 15));
        jtext1.setText("请选择或者输入目录，如：C:/folder");
//        jtext1.setEditable(false);//是否可以编辑
        jtext1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (jtext1.getText().equals("请选择或者输入目录，如：C:/folder")){
                    jtext1.setText("");
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }
}
