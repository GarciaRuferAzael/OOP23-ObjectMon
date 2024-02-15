package it.unibo.objectmon.model.encounters.impl;

import java.util.Random;
import java.util.Optional;
import it.unibo.objectmon.model.battle.api.BattleInitiator;
import it.unibo.objectmon.model.core.GameContext;
import it.unibo.objectmon.model.data.api.objectmon.Objectmon;
import it.unibo.objectmon.model.data.objectmon.ObjectmonEnum;
import it.unibo.objectmon.model.data.objectmon.ObjectmonFactory;
import it.unibo.objectmon.model.encounters.api.RandomEncounterManager;
import it.unibo.objectmon.model.entities.api.Player;
import it.unibo.objectmon.model.entities.player.PlayerImpl;
import it.unibo.objectmon.model.gamestate.Observer;
import it.unibo.objectmon.model.world.api.Coord;

/**
 * The RandomEncounterManagerImpl class manages random encounters within the game world.
 * It observes changes in the game context and triggers encounters based on certain conditions.
 * This class implements the Observer interface to receive updates when the {@link Player} moves.
 */
public final class RandomEncounterManagerImpl implements Observer, RandomEncounterManager {

    private final GameContext gameContext;
    private final BattleInitiator battleInitiator;
    private static final double ENCOUNTER_RATE = 0.2; //the chance of triggering a random encounter on a suitable tile.
    private final Random random = new Random();
    private final int graceDuration;
    private int gracePeriodCounter;
    private boolean gracePeriodActive;

    /**
     * Constructs a RandomEncounterManager.
     * 
     * @param gameContext the game context
     * @param battleStartListener the manager used to start the battle
     */
    public RandomEncounterManagerImpl(final GameContext gameContext, final BattleInitiator battleInitiator, final int graceDuration) {
        this.gameContext = gameContext;
        this.battleInitiator = battleInitiator;
        this.graceDuration = graceDuration;
        if (gameContext.getPlayer() instanceof PlayerImpl) {
            final PlayerImpl playerImpl = (PlayerImpl) gameContext.getPlayer();
            playerImpl.addObserver(this);
        } else {
            throw new IllegalStateException("Could not attach RandomEncounterManager as observer to the Player");
        }
    }

    @Override
    public void performCheck() {
        if (!gracePeriodActive) {
            final Coord position = gameContext.getPlayer().getPosition();
            if (gameContext.getWorld().getMap().get(position).isTriggersEncounters() && encountersOccurs()) {
               startRandomEncounter();
            }
        } else {
            gracePeriodCounter++;
            if (gracePeriodCounter >= graceDuration) {
                gracePeriodActive = false;
                gracePeriodCounter = 0;
            }
        }
    }

    private boolean encountersOccurs() {
        return random.nextDouble() <= ENCOUNTER_RATE;
    }

    // Stars a random encounter by selecting a wild Objectmon with an average level based on the player's party and initiates a battle.
    private void startRandomEncounter() {
        final int wildLevel = (int) gameContext.getPlayer().getObjectmonParty().getParty().stream()
            .mapToInt(Objectmon::getLevel)
            .average()
            .getAsDouble();
        final Objectmon wildObjectmon = ObjectmonFactory.createObjectmon(selectRandomObjectmon(), wildLevel);
        battleInitiator.startBattle(gameContext.getPlayer(), Optional.empty(), Optional.of(wildObjectmon));
        // Activate grace period after encountering.
        activateGracePeriod();
    }

    private ObjectmonEnum selectRandomObjectmon() {
        final ObjectmonEnum[] avaiableObjectmon = ObjectmonEnum.values();
        final int randomIndex = random.nextInt(avaiableObjectmon.length - 1);
        return avaiableObjectmon[randomIndex];
    }

    @Override
    public void update() {
        performCheck();
    }

    @Override
    public void activateGracePeriod() {
        gracePeriodActive = true;
    }

    @Override
    public boolean isGracePeriodActive() {
        return this.gracePeriodActive;
    }
}
