package com.github.Viduality.EventAids.Flags;

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

import com.github.Viduality.EventAids.EventAids;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Egg;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.ArrayList;
import java.util.List;

public class EggsplosionSpleef implements Listener {

    @EventHandler
    public void projectileHit (ProjectileHitEvent event) {
        if (event.getEntity() instanceof Egg) {
            if (event.getHitBlock() != null) {
                Location loc = event.getHitBlock().getLocation();
                RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                RegionQuery query = container.createQuery();
                ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(loc));
                if (set.queryValue(null, EventAids.EGGSPLOSION_SPLEEF) != null) {
                    event.getHitBlock().getLocation().createExplosion(0, false, false);
                    Integer radius = set.queryValue(null, EventAids.EGGSPLOSION_SPLEEF);
                    for (Block b : getNearbyBlocks(loc, radius)) {
                        if (query.getApplicableRegions(BukkitAdapter.adapt(b.getLocation())).queryValue(null, EventAids.EGGSPLOSION_SPLEEF) != null) {
                            b.setType(Material.AIR);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void chickenSpawn (EntitySpawnEvent event) {
        if (event.getEntity() instanceof Chicken) {
            Location loc = event.getLocation();
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = container.createQuery();
            ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(loc));
            if (set.queryValue(null, EventAids.EGGSPLOSION_SPLEEF) != null) {
                event.setCancelled(true);
            }
        }
    }

    private List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<>();
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    if (location.distanceSquared(new Location(location.getWorld(), x, y, z)) <= radius * radius) {
                        blocks.add(location.getWorld().getBlockAt(x, y, z));
                    }
                }
            }
        }
        return blocks;
    }
}
