package io.github.nk00657.takeyari.common.item

import mekanism.api.MekanismAPI
import net.minecraft.world.item.Tier

class MekaSpearModule(
    tier: Tier,
    attackDamage: Int,
    attackSpeed: Float,
    reachBonus: Double,
    capacity: Int,
    maxReceive: Int,
    maxExtract: Int,
    properties: Properties
): MekaSpear(tier,attackDamage,attackSpeed,reachBonus,capacity,maxReceive,maxExtract,properties) {
    
}