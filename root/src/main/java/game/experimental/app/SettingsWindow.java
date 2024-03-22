package game.experimental.app;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsWindow implements ActionListener {
    JFrame f;
    SettingsWindow(){
        f=new JFrame();//creating instance of JFrame

        JButton b=new JButton("click");//creating instance of JButton
        b.setBounds(130,100,100, 40);

        f.add(b);//adding button in JFrame

        f.setSize(400,500);//400 width and 500 height
        f.setLayout(null);//using no layout managers
    }

    public void open() {
        f.setVisible(true);//making the frame visible
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String commend = e.getActionCommand();
//        if(commend.equals("save")){
//            try{
//                saveAction(e);
//            }catch (Exception exception){
//
//            }
        try {
            System.out.println("Saved from Settings page");
        }
        catch (Exception exception){

        }

    }

}  