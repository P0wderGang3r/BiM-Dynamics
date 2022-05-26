package com.company.GUI.InputGUI.GraphGUI;

import com.company.GUI.Database.DBHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class LoadGraphDialog extends JDialog {
    private JPanel contentPane;
    private JPanel xPanel;
    private JLabel formulaLabel;
    private JComboBox comboBox1;
    private JButton setButton;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton buttonOK;
    private JButton buttonCancel;

    public LoadGraphDialog() {
        setContentPane(contentPane);
        setTitle("Загрузка графика");
        setSize(400, 220);

        try {
            BufferedImage deleteIcon = ImageIO.read(new File("resources/images/trash-10-16.png"));
            deleteButton.setIcon(new ImageIcon(deleteIcon));
        }
        catch(IOException ex){}

        loadComboBox(comboBox1);

        setVisible(true);
        this.setAlwaysOnTop(true);
    }

    public void loadComboBox(JComboBox cb){
        cb.removeAllItems();

        List<String> names = DBHandler.getGraphNames();
        for (String name : names) {
            cb.addItem(name);
        }
    }
}