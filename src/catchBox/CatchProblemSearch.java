package catchBox;

import agentSearch.Action;
import agentSearch.Problem;

import java.util.ArrayList;
import java.util.List;

public class CatchProblemSearch<S extends CatchState> extends Problem<S> {
    private Cell goalPosition;
    protected List<Action> availableAction;


    public CatchProblemSearch(S initialCatchState, Cell goalPosition) {
        super(initialCatchState);

        this.initialState.setGoal(goalPosition.getLine(),goalPosition.getColumn());
        this.goalPosition = goalPosition;
        // posiçao é definida sempre que outra posição é cumprida
        // goalPosition == box a apanhar
        // Sempre que a box é apanhada é redefenida a goalPosition com setGoal() [CatchState]

        //açoes possiveis de executar
        availableAction = new ArrayList<>(4);
        availableAction.add(new ActionUp());
        availableAction.add(new ActionRight());
        availableAction.add(new ActionDown());
        availableAction.add(new ActionLeft());
    }

    @Override
    public List<S> executeActions(S state) {
        List<S> successors = new ArrayList<>();
        for (Action action: availableAction) {
            if(action.isValid(state)){
                S successor = (S) state.clone();
                action.execute(successor);
                successors.add(successor);
            }
        }
        return successors;
    }

    public boolean isGoal(S state) {
        return goalPosition.getLine() == state.getLineCatch() && goalPosition.getColumn() == state.getColumnCatch();
    }

    public Cell getGoalPosition() {
        return goalPosition;
    }
}
