package it.unibo.objectmon.model.ai;

import it.unibo.objectmon.model.ai.api.AiTrainer;
import it.unibo.objectmon.model.data.api.objectmon.ObjectmonParty;
/**
 * an implementation of ai trainer, 
 * which change the objectmon only if the current fighting objectmon is dead.
 */
public final class EasyAiTrainer extends EasyAiObjectmon implements AiTrainer {
    private static final int SIZE = 6;
    private int index;
    /**
     * Constructor.
     */
    public EasyAiTrainer() {
        this.index = 1;
    }

    @Override
    public void switchObjectmon(final ObjectmonParty team) {
        if (index < EasyAiTrainer.SIZE) {
            team.switchPosition(team.getParty().get(0), team.getParty().get(this.index++));
        }
    }
}
