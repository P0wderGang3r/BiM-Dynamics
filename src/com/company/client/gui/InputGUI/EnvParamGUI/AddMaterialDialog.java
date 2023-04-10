package com.company.client.gui.InputGUI.EnvParamGUI;

import com.company.client.gui.Database.DBHandler;
import com.company.client.gui.Database.Material;
import com.company.client.gui.DataHandler;

import javax.swing.*;
import java.awt.event.*;

public class AddMaterialDialog extends JDialog {
    private JPanel contentPane;
    private JPanel param1Panel;
    private JTextField textField1;
    private JButton SubmitButton;

    public AddMaterialDialog(EnvParamForm frame) {
        add(contentPane);
        setVisible(true);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setTitle("Добавление материала");
        setSize(400, 200);

        SubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textField1.getText();
                if (name.length()>0) {
                    if(addMaterial(name))
                        frame.comboBox1.addItem(name);
                    dispose();
                }
            }
        });
    }

    private boolean addMaterial(String name){
        Material material = new Material(
                name,
                DataHandler.lameMu,
                DataHandler.lameLambda,
                DataHandler.materialDensity,
                DataHandler.coefficientNu
        );
        return DBHandler.addMaterial(material);
    };
}
