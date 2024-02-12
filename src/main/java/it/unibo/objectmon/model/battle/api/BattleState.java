package it.unibo.objectmon.model.battle.api;
import java.util.Optional;

import it.unibo.objectmon.model.data.api.objectmon.Objectmon;
import it.unibo.objectmon.model.entities.api.Player;
import it.unibo.objectmon.model.entities.npc.TrainerImpl;
/**
 * current state of the battle.
 */
public interface BattleState {
    /**
     * @return current stats of the player.
     */
    Player getPlayerStats();
    /**
     * get objectmon actualstats in fight.
     * @return current stats of the objectmon in fight of player
     */
    Objectmon getPlayerObjectmonStats();
    /**
     * 
     * @return current stats of trainer.
     */
    Optional<TrainerImpl> getTrainerStats();
    /**
     * 
     * @return current stats of the objectmon as enemy.
     */
    Objectmon getEnemyObjectmonStats();
}
