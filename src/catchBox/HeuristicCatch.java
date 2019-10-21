package catchBox;

import agentSearch.Heuristic;

public class HeuristicCatch extends Heuristic<CatchProblemSearch, CatchState> {

    @Override
    public double compute(CatchState state) {
        return state.computeHeuristic(problem.getGoalPosition());
    }

    @Override
    public String toString() {
        return "Tile distance to final position (ignores walls)";
    }
}