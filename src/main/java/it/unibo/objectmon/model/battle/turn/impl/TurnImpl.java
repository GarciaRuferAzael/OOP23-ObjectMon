package it.unibo.objectmon.model.battle.turn.impl;


import it.unibo.objectmon.model.battle.moves.type.Move;
import it.unibo.objectmon.model.battle.turn.StatTurn;
import it.unibo.objectmon.model.battle.turn.api.Turn;
import it.unibo.objectmon.model.data.api.objectmon.Objectmon;
import it.unibo.objectmon.model.data.api.statistics.StatEnum;

/**
 * implementation of turn.
 */
public final class TurnImpl implements Turn {
    private StatTurn stat;

    /**
     * constructor.
     */
    public TurnImpl() {
        this.stat = StatTurn.IS_WAITING_MOVE;
    }

    @Override
    public StatTurn whichFirst(
            final Move moveAI, 
            final Move movePLayer,
            final Objectmon playerObjectmon,
            final Objectmon aiObjectmon
        ) {
            return moveAI.equals(Move.ATTACK)
            ? movePLayer.equals(Move.ATTACK)
                ? whichUseSkillFirst(playerObjectmon, aiObjectmon)
                : StatTurn.PLAYER_TURN
            : movePLayer.equals(Move.ATTACK)
                ? StatTurn.AI_TURN
                : StatTurn.PLAYER_TURN;
    }

    private StatTurn whichUseSkillFirst(final Objectmon playerObjectmon, final Objectmon aiObjectmon) {
        return playerObjectmon.getStats().getStat(StatEnum.SPD) 
            >= aiObjectmon.getStats().getStat(StatEnum.SPD)
            ? StatTurn.PLAYER_TURN
            : StatTurn.AI_TURN;
    }
    @Override
    public StatTurn getStat() {
        return this.stat;
    }
    @Override
    public void setTurn(final StatTurn statTurn) {
        this.stat = statTurn;
    }
}
