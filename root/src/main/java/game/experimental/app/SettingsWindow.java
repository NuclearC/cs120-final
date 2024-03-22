package game.experimental.app;

import game.experimental.app.input.InputSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsWindow {
    JFrame frame;
    JTextField playerNameField;
    InputSystem inputSystemInstance;
    private JButton createButton(String name, int x, int y, SettingsWindowListener listener){
        JButton button=new JButton(name);//creating instance of JButton
        button.setBounds(x,y,80,20);
        button.addActionListener(listener);
        button.setBackground(new Color(79,152,44));
        button.setBorderPainted(true);
        frame.add(button);
        return button;
    }

    public SettingsWindow(){
        inputSystemInstance = InputSystem.getInstance();
        frame =new JFrame();//creating instance of JFrame
        frame.setLayout(null);
        JTabbedPane tabbedPane = new JTabbedPane();
        String[] tabName = {"General","Mouse","Keyboard","Video"};

        playerNameField = new JTextField();
        playerNameField.setBounds(20,50, 100,20);
        playerNameField.setText("player's name");//we should get the players name
        JLabel playerNameLable = new JLabel("Player Name");
        playerNameLable.setBounds(20,20, 100,30);

        JPanel[] panel = new JPanel[4];
        for(int i = 0; i < panel.length; i++){
            panel[i] = new JPanel();
            panel[i].setBounds(10,10,700,400);
            panel[i].setBackground(new Color(215,255,140));
            panel[i].setLayout(null);
        }
        panel[0].add(playerNameField);
        panel[0].add(playerNameLable);
        for(int i = 0; i < panel.length; i++){
            tabbedPane.addTab(tabName[i],panel[i]);
        }
        tabbedPane.setBounds(0,0,700,400);
        frame.add(tabbedPane);

        JButton apply= createButton("apply",400,420,new SettingsWindowListener());
        JButton save= createButton("save",500,420,new SettingsWindowListener());
        JButton cancel=createButton("cancel",600,420,new SettingsWindowListener());


        frame.setSize(700,500);//400 width and 500 height
        frame.setLayout(null);//using no layout managers
    }

    public void open() {
        frame.setVisible(true);//making the frame visible
    }

    public class SettingsWindowListener implements ActionListener {
        private void setAllData(){
            inputSystemInstance.setName(playerNameField.getText());
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            String commend = e.getActionCommand();
            if(commend.equals("cancel")){
                frame.setVisible(false);
                frame.dispose();
            }
            else if(commend.equals("save")){
                setAllData();
                frame.setVisible(false);
                frame.dispose();
            }
            else if(commend.equals("apply")){
               setAllData();
            }

        }

    }
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        String commend = e.getActionCommand();
////        if(commend.equals("save")){
////            try{
////                saveAction(e);
////            }catch (Exception exception){
////
////            }
//        try {
//        }
//        catch (Exception exception){
//
//        }
//
//    }

}  