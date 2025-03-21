package it.unibo.objectmon.model.entities.npc;

import java.util.List;
import java.util.Optional;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.objectmon.model.battle.api.BattleInitiator;
import it.unibo.objectmon.model.data.api.objectmon.Objectmon;
import it.unibo.objectmon.model.data.api.objectmon.ObjectmonParty;
import it.unibo.objectmon.model.data.objectmon.ObjectmonPartyImpl;
import it.unibo.objectmon.model.entities.api.AbstractNPC;
import it.unibo.objectmon.model.entities.api.Player;
import it.unibo.objectmon.model.entities.api.Trainer;
import it.unibo.objectmon.model.misc.eventlog.api.InteractionLogger;
import it.unibo.objectmon.model.world.api.Coord;

/**
 * Models an NPC capable of battling.
 */
public final class TrainerImpl extends AbstractNPC implements Trainer {

    private final ObjectmonParty objectmonParty;
    private final BattleInitiator battleInitiator;

    /**
     * Creates a new Trainer.
     * 
     * @param name The name of the Trainer.
     * @param coord The starting position of the Trainer.
     * @param team The team of Objectmons of the Trainer.
     * @param battleInitiator the notifier of a battle start.
     */
    public TrainerImpl(final String name,
            final Coord coord,
            final List<Objectmon> team,
            final BattleInitiator battleInitiator) {
        super(name, coord);
        this.objectmonParty = new ObjectmonPartyImpl(team);
        this.battleInitiator = battleInitiator;
    }

    @Override
    public boolean isDefeated() {
        return this.objectmonParty.getParty().size() == 0;
    }

    @SuppressFBWarnings(value = "EI_EXPOSE_REP",
    justification = "By design it was decided to let it circulate freely in the model")
    @Override
    public ObjectmonParty getObjectmonParty() {
        return this.objectmonParty;
    }

    @Override
    public void handleInteraction(final Player player, final InteractionLogger logger) {
        if (!isDefeated()) {
            logger.log(this.getName() + " challenges " + player.getName());
            this.battleInitiator.startBattle(player, Optional.of(this), Optional.empty());
        } else {
            logger.log(this.getName() + " has already been defeated.");
        }
    }
}
