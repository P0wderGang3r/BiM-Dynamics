package com.company.Simulation.SimulationFunctions;

import com.company.Simulation.SimulationVariables.SimulationGlobals;

public class WaveFrontComputations {

    /**
     * Вычисление напряжения Эйлера-Коши на стыке волн деформации
     */
    public static double computeTension(double lilDeformations) {
        if (0 > lilDeformations) {
            //return \lambda + 2 * \mu + 2 * \nu
            return SimulationGlobals.getLameLambda() + 2 * SimulationGlobals.getLameMu() + 2 * SimulationGlobals.getCoefficientNu();
        } else if (0 < lilDeformations) {
            //return \lambda + 2 * \mu - 2 * \nu
            return SimulationGlobals.getLameLambda() + 2 * SimulationGlobals.getLameMu() - 2 * SimulationGlobals.getCoefficientNu();
        } else return 0;
    }

    /**
     * Вычисление характеристической скорости
     */
    public static double computeCharSpeed(double lilDeformations) {
        //return \sigma / \rho === (\lambda + 2 * \mu +- 2 * \nu) / \rho
        return computeTension(lilDeformations) / SimulationGlobals.getMaterialDensity();
    }

    /**
     * Формула для вычисления скорости новообразованной ударной волны
     */
    public static double computeNewWaveFrontSpeed(double speedLeft, double speedRight, double deltaMovLeft, double deltaMovRight, double deltaMovCurr) {
        return speedLeft * speedLeft - (speedLeft * speedLeft - speedRight * speedRight) * (deltaMovLeft) / (deltaMovCurr - deltaMovRight);
    }

    public static void computeCollisions() {

    }
}
