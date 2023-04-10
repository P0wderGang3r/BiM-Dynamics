package com.company.client.gui.InputGUI;

import com.company.client.gui.DataHandler;
import com.company.client.gui.GUIGlobals;
import com.company.client.gui.InputGUI.EnvParamGUI.EnvParamForm;
import com.company.client.gui.InputGUI.GraphGUI.GraphForm;
import com.company.client.gui.SimulationGUI.SimulationForm;
import com.company.server.simulation.inter_process_functions.border_handlers.Border;
import com.company.server.simulation.simulation_variables.SimulationGlobals;
import com.company.server.simulation.simulation_types.enums.SimulationTimePow;
import com.company.server.simulation.simulation_types.enums.DenoteFactor;
import com.company.server.SimServer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm extends JFrame {
    private JPanel mainPanel;
    private JButton envParamButton;
    private JButton graphButton;
    private JButton simulationButton;
    private JLabel projectNameLabel;
    private JLabel envParamLabel;
    private JLabel graphLabel;
    private JPanel inputPanel;
    private JPanel simulationPanel;
    private JTextArea projectDescriptionArea;

    public MainForm(SimServer ServerThread) {
        this.setTitle(GUIGlobals.program_title);
        this.setSize(GUIGlobals.main_frame_width, GUIGlobals.main_frame_height);
        this.setVisible(true);

        // позволяет появляться фрейму в центре экрана. (по дефолту - в верхнем левом углу)
        this.setLocationRelativeTo(null);

        // при закрытия данного фрейма закроются все остальные окна, фреймы
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Главная панель со всеми кнопками
        this.add(mainPanel);

        projectNameLabel.setText(GUIGlobals.program_title);

        envParamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EnvParamForm envParamForm = new EnvParamForm(envParamLabel, ServerThread);
            }
        });


        graphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GraphForm graphForm = new GraphForm(graphLabel, ServerThread);
            }
        });

        simulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(DataHandler.graph_input_status && DataHandler.env_param_input_status) {
                    SimulationGlobals.setSimulationGlobals(
                            DataHandler.lameMu,
                            DataHandler.lameLambda,
                            DataHandler.materialDensity,
                            DataHandler.coefficientNu
                    );

                    SimulationTimePow simulationTimePow;

                    switch (DataHandler.unitOfTime){
                        case "мкс" -> simulationTimePow = SimulationTimePow.MICROSECONDS;
                        case "нс" -> simulationTimePow = SimulationTimePow.NANOSECONDS;
                        default -> simulationTimePow = SimulationTimePow.MILLISECONDS;
                    }

                    Border.initBorderDisplacementFunctions(DataHandler.lin_appr_array, DenoteFactor.MILLI, simulationTimePow);
                    SimulationForm simulationForm = new SimulationForm(ServerThread);
                }
                else{
                    // Вывод сообщения об ошибке
                    String error_message = "Введите:\n";

                    if (!DataHandler.env_param_input_status)
                        error_message += "\tПараметры среды\n";

                    if (!DataHandler.graph_input_status)
                        error_message += "\tГрафик линейной аппроксимации";

                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, error_message, "Ошибка", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }
}
