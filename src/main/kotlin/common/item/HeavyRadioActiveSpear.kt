package io.github.nk00657.takeyari.common.item

import mekanism.api.Coord4D
import mekanism.api.radiation.IRadiationManager
import mekanism.api.tier.BaseTier
import net.minecraft.network.chat.Component
import net.minecraft.stats.Stats
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Tier
import net.minecraft.world.level.Level
import net.minecraftforge.common.capabilities.ForgeCapabilities

class HeavyRadioActiveSpear(
    tier: Tier,
    attackDamage: Int,
    attackSpeed: Float,
    reachBonus: Double,
    private val range: Double,
    capacity: Int,
    maxReceive: Int,
    maxExtract: Int,
    properties: Properties
): RadioActiveSpear(tier,attackDamage,attackSpeed,reachBonus,capacity,maxReceive,maxExtract,properties){

    override fun getName(stack: ItemStack): Component {
        return super.getName(stack).copy().withStyle { style ->
            style.withColor(BaseTier.ULTIMATE.color)
        }
    }

    override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
        val itemStack = player.getItemInHand(usedHand)

        if (!level.isClientSide) {
            var success = false

            itemStack.getCapability(ForgeCapabilities.ENERGY).ifPresent { storage ->
                val energyCost = 10000
                if (storage.energyStored >= energyCost) {
                    storage.extractEnergy(energyCost, false)

                    val coord = Coord4D(player.blockPosition(), level)
                    IRadiationManager.INSTANCE.radiate(coord, range)


                    player.cooldowns.addCooldown(this, 300) // 15秒（300 tick）
                    player.awardStat(Stats.ITEM_USED.get(this))
                    success = true
                }
            }

            if (success) {
                return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide)
            }
        } else {

            val hasEnergy = itemStack.getCapability(ForgeCapabilities.ENERGY)
                .map { it.energyStored >= 10000 }
                .orElse(false)

            if (hasEnergy) {
                return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide)
            }
        }

        return InteractionResultHolder.pass(itemStack)
    }
}