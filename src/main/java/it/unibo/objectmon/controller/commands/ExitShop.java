package it.unibo.objectmon.controller.commands;

import it.unibo.objectmon.controller.commands.api.Command;
import it.unibo.objectmon.model.Model;

public final class ExitShop implements Command {

    @Override
    public void execute(final Model model) {
        model.getTradeManager().stopTrade();
    }
    
}
