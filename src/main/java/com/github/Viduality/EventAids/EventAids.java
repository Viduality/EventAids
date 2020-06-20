package com.github.Viduality.EventAids;

/*
 * EventAids
 * Copyright (C) 2020, Viduality
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import com.github.Viduality.EventAids.Flags.EggsplosionSpleef;
import com.github.Viduality.EventAids.Flags.SnowballSpleef;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.IntegerFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EventAids extends JavaPlugin {

    public static EventAids instance;
    public static StateFlag SNOWBALL_SPLEEF;
    public static IntegerFlag EGGSPLOSION_SPLEEF;

    public void onEnable() {
        instance = this;

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new SnowballSpleef(), this);
        pm.registerEvents(new EggsplosionSpleef(), this);

    }

    public void onLoad() {
        registerFlags();
    }

    public void onDisable() {

    }

    public static EventAids getInstance() {
        return instance;
    }

    private void registerFlags() {
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
        try {
            StateFlag snowballSpleef = new StateFlag("snowball-spleef", false);
            IntegerFlag eggsplosionSpleef = new IntegerFlag("eggsplosion-spleef-radius");
            registry.register(snowballSpleef);
            registry.register(eggsplosionSpleef);
            SNOWBALL_SPLEEF = snowballSpleef;
            EGGSPLOSION_SPLEEF = eggsplosionSpleef;
        } catch (FlagConflictException e) {
            Flag<?> existingSnowballSpleef = registry.get("snowball-spleef");
            Flag<?> existingEggsplosionSpleef = registry.get("eggsplosion-spleef-radius");
            if (existingSnowballSpleef instanceof StateFlag) {
                SNOWBALL_SPLEEF = (StateFlag) existingSnowballSpleef;
            }
            if (existingEggsplosionSpleef instanceof IntegerFlag) {
                EGGSPLOSION_SPLEEF = (IntegerFlag) existingEggsplosionSpleef;
            }
        }
    }
}
