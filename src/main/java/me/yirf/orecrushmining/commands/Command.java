package me.yirf.orecrushmining.commands;

import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.Permission;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import me.yirf.orecrushmining.Orecrushmining;
import me.yirf.orecrushmining.configs.Blocks;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandRegistry(value = "ocm", usage = "/ocm", permission = @Permission("orecrushmining.admin"), playersOnly = true)
public class Command implements CustomCommand {

    @Override
    public void dispatchCommand(CommandSender sender, Args args) {
        Player player = (Player) sender;
        Location loc = player.getLocation();

        if (args.match(0, "reload")) {
            long startTime = System.currentTimeMillis();
            Orecrushmining.config.save();
            long endTime = System.currentTimeMillis();
            long reloadTime = endTime - startTime;
            info(sender, "&aReloaded config.json in " + reloadTime + " &ams.");
            error(sender, "&4[!] &cBlocks.yml doesn't need reloading!");
        } else if (args.match(0, "debug")) {
            info(sender, "&e[Debug]:");
            if (args.get(1) != null || !args.match(1, "setspeed")) {
                info(sender, "&6" + args.get(1) + " &f-&a " + Blocks.Config.blockHealth(args.get(1).toString()));
            }
        } else {
            error(sender, "Subcommand not found.");
        }
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b) {
        b.then(b.arg("debug")
                .then(b.arg("<key>")
                        .then(b.arg("<value>"))));
        b.then(b.arg("reload"));
    }
}