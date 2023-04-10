package com.company.client.gui.InputGUI.GraphGUI;

import com.company.client.gui.DataHandler;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SetFormulaDialog extends JDialog {
    private JPanel contentPane;
    private JPanel xPanel;
    private JTextField formulaField;
    private JLabel formulaLabel;
    private JTextField precisionField;
    private JLabel precisionLabel;
    private JButton setButton;
    private JLabel statusLabel;
    private JCheckBox splineCheckBox;
    private JButton buttonOK;

    public SetFormulaDialog(JFreeChart chart, XYSeries series) {
        add(contentPane);
        setTitle("Задание формулы");
        setSize(400, 200);

        setVisible(true);
        this.setAlwaysOnTop(true);

        statusLabel.setText("");
        precisionField.setText("1");

        setButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double[][] array = DataHandler.getFormulaArray(
                        formulaField.getText(),
                        precisionField.getText());
                if(array!=null) {
                    series.clear();
                    for(int i=0; i< array[0].length;i++)
                        series.add(array[0][i], array[1][i]);
                }
            }
        });

        splineCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                XYPlot plot = chart.getXYPlot();
                XYSplineRenderer r = (XYSplineRenderer) plot.getRenderer(0);
                // точность сплайна
                if(e.getStateChange() == ItemEvent.DESELECTED)
                    r.setPrecision(1);
                else
                    r.setPrecision(DataHandler.spline_precision);
            }
        });
    }
}
