package game.experimental.app;

import game.experimental.app.input.InputSystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsWindow {
    JFrame f;
    JTextField tf;
    InputSystem inputSystemInstance;
    public SettingsWindow(){
        inputSystemInstance = InputSystem.getInstance();
        f=new JFrame();//creating instance of JFrame

        JButton b=new JButton("apply");//creating instance of JButton
        b.setBounds(130,100,100, 40);
        b.addActionListener(new SettingsWindowListener());

        tf = new JTextField();
        tf.setBounds(50,50, 150,20);
        f.add(b);//adding button in JFrame
        f.add(tf);

        f.setSize(400,500);//400 width and 500 height
        f.setLayout(null);//using no layout managers
    }

    public void open() {
        f.setVisible(true);//making the frame visible
    }

    public class SettingsWindowListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            inputSystemInstance.setName(tf.getText());
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