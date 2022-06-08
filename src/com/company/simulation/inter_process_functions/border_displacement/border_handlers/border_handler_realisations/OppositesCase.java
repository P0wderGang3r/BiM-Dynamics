package com.company.simulation.inter_process_functions.border_displacement.border_handlers.border_handler_realisations;

import com.company.simulation.inter_process_functions.border_displacement.border_handlers.IBorderHandler;
import com.company.simulation.simulation_variables.SimulationGlobals;
import com.company.simulation.simulation_variables.wave_front.DenoteFactor;
import com.company.simulation.simulation_variables.wave_front.WaveFront;

import java.util.ArrayList;

public class OppositesCase implements IBorderHandler {
    /**
     * Вычисление напряжения Эйлера-Коши на стыке волн деформации
     */
    public double computeTension() {
        //return \lambda + 2 * \mu + 2 * \nu
        return SimulationGlobals.getLameLambda() + 2 * SimulationGlobals.getLameMu() + 2 * SimulationGlobals.getCoefficientNu();
    }

    /**
     * Вычисление характеристической скорости
     */
    public double computeSpeed()
    {
        //return \sigma / \rho === (\lambda + 2 * \mu +- 2 * \nu) / \rho
        return Math.sqrt(computeTension() / SimulationGlobals.getMaterialDensity());
    }

    @Override
    public WaveFront generateNewWaveFront(ArrayList<WaveFront> prevWaveFronts, double speed) {
        //Игнорируем факт поступления скорости
        assert (speed == 0.0);

        double localSpeed = DenoteFactor.METERS.toMillimeters(computeSpeed());

        double A0L = prevWaveFronts.get(0).getA0();
        double A1L = prevWaveFronts.get(0).getA1();
        double A2L = 0.0 - prevWaveFronts.get(0).getA1() / localSpeed;
        double startTL = prevWaveFronts.get(0).getStartTime();

        double A0R = prevWaveFronts.get(1).getA0();
        double A1R = prevWaveFronts.get(1).getA1();
        double A2R = prevWaveFronts.get(1).getA2();
        double startTR = prevWaveFronts.get(0).getStartTime();

        //Частный случай при CL = 0, xL = 0, Xi = 0
        double A2i = (A1R + A2R * localSpeed - A1L) / (localSpeed);
        double A1i = A1L;
        double A0i = A0R + A1R * (startTL - startTR);

        WaveFront newWaveFront = new WaveFront(A0i, A1i, A2i, startTL);

        newWaveFront.setSpeed(localSpeed);

        return newWaveFront;
    }
}