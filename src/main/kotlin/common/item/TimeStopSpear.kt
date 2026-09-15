package io.github.nk00657.takeyari.common.item

import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import io.github.nk00657.takeyari.common.event.TimeStop
import net.minecraft.stats.Stats
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Item
import net.minecraftforge.fml.ModList
import net.minecraft.world.level.Level

class TimeStopSpear(properties: Properties):Item(properties) {

    override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
        val itemStack = player.getItemInHand(usedHand)

        if (!level.isClientSide) {
            if(ModList.get().isLoaded("ending_library")){
                TimeStop.triggerTimestop(player, 200)
            }
            else{
                val range = 50.0
                val area = player.boundingBox.inflate(range)
                val nearbyEntities = level.getEntitiesOfClass(LivingEntity::class.java, area){
                    entity -> entity != player
                }

                for (mob in nearbyEntities) {
                    mob.addEffect(MobEffectInstance(MobEffects.WEAKNESS, 200, 255, false, false, false))
                    mob.addEffect(MobEffectInstance(MobEffects.JUMP, 200, 128, false, false, false))
                    mob.addEffect(MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 255, false, false, false))
                    mob.addEffect(MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 5, false, false, false))
                    mob.addEffect(MobEffectInstance(MobEffects.DIG_SLOWDOWN, 200, 255, false, false, false))
                    mob.addEffect(MobEffectInstance(MobEffects.BLINDNESS, 200, 5, false, false, false))
                }
            }

        }

        player.cooldowns.addCooldown(this, 600)
        player.awardStat(Stats.ITEM_USED.get(this))

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide)
    }


}