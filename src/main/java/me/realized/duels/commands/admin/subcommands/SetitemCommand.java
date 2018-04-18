package me.realized.duels.commands.admin.subcommands;

import me.realized.duels.commands.SubCommand;
import me.realized.duels.event.KitItemChangeEvent;
import me.realized.duels.kits.Kit;
import me.realized.duels.utilities.Helper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SetitemCommand extends SubCommand {

    public SetitemCommand() {
        super("setitem", "setitem [name]", "duels.admin", "Replaces the displayed item to held item for selected kit.", 2);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void execute(Player sender, String[] args) {
        ItemStack held;

        held = sender.getInventory().getItemInHand();

        if (held == null || held.getType() == Material.AIR) {
            Helper.pm(sender, "Errors.empty-hand", true);
            return;
        }

        String name = Helper.join(args, 1, args.length, " ");

        if (kitManager.getKit(name) == null) {
            Helper.pm(sender, "Errors.kit-not-found", true);
            return;
        }

        Kit kit = kitManager.getKit(name);
        ItemStack old = kit.getDisplayed();
        ItemStack _new = held.clone();
        kit.setDisplayed(_new);
        kitManager.getGUI().update(kitManager.getKits());
        Helper.pm(sender, "Kits.set-item", true, "{NAME}", name);
        KitItemChangeEvent event = new KitItemChangeEvent(name, sender, old, _new);
        Bukkit.getPluginManager().callEvent(event);
    }
}
