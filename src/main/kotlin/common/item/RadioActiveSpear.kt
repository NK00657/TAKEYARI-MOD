package io.github.nk00657.takeyari.common.item


import mekanism.api.radiation.IRadiationManager
import mekanism.api.tier.BaseTier
import net.minecraft.world.entity.LivingEntity

import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Tier
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraft.network.chat.Component

class RadioActiveSpear(
    tier: Tier,
    attackDamage: Int,
    attackSpeed: Float,
    reachBonus: Double,
    capacity: Int,
    maxReceive: Int,
    maxExtract: Int,
    properties: Properties
) : io.github.nk00657.takeyari.common.item.MekaSpear(tier,attackDamage,attackSpeed,reachBonus,capacity,maxReceive,maxExtract,properties){
    override fun getName(stack: ItemStack): Component{
        return super.getName(stack).copy().withStyle{ style ->
            style.withColor(BaseTier.ULTIMATE.color)
        }
    }






    override fun hurtEnemy(stack: ItemStack, target: LivingEntity, attacker: LivingEntity): Boolean {
        stack.getCapability(ForgeCapabilities.ENERGY).ifPresent { storage ->
            val energyCost = 10000
            if (storage.energyStored >= energyCost){
                storage.extractEnergy(energyCost, false)

                IRadiationManager.INSTANCE.radiate(target, 10000000.0)

            }
        }
        return super.hurtEnemy(stack, target, attacker)
    }
}