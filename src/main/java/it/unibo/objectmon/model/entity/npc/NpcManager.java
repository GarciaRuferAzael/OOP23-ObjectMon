package it.unibo.objectmon.model.entity.npc;

import java.util.Collections;
import java.util.Set;
import it.unibo.objectmon.model.entity.api.npc.AbstractNPC;

/**
 * A manager that handles all the NPCs in game.
 */
public final class NpcManager {
    private final Set<AbstractNPC> npcs;

    /**
     * Initializes the manager and creates the in game NPCs.
     */
    public NpcManager() {
        this.npcs = NPCFactory.createDemoNPCs();
    }

    /**
     * Retrieves an immutable set of current NPCs.
     * @return An immutable set of current NPCs.
     */
    public Set<AbstractNPC> getNpcs() {
        return Collections.unmodifiableSet(npcs);
    }
}
