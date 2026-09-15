package io.github.nk00657.takeyari.common.item

import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.Multimap
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.Arrow
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.BowItem
import net.minecraft.world.level.Level
import net.minecraft.world.phys.EntityHitResult
import java.util.UUID


class SupportSpearItem(
    properties: Properties,
    val get: Any

) : BowItem(properties) {
    private val SPEED_MODIFIER_UUID = UUID.fromString("e2a3b4c5-d6e7-4890-a1b2-c3d4e5f60718")

    override fun getDefaultAttributeModifiers(slot: EquipmentSlot): Multimap<Attribute, AttributeModifier> {
        val modifiers = ImmutableMultimap.builder<Attribute, AttributeModifier>()

        modifiers.putAll(super.getDefaultAttributeModifiers(slot))

        if (slot == EquipmentSlot.OFFHAND) {
            modifiers.put(
                Attributes.MOVEMENT_SPEED,
                AttributeModifier(
                    SPEED_MODIFIER_UUID,
                    "Support Spear movement speed bonus",
                    0.2,
                    AttributeModifier.Operation.MULTIPLY_TOTAL
                )
            )
        }


        return modifiers.build()
    }

    fun getCustomPowerForTime(charge: Int): Float {
        var power = charge.toFloat() / 10.0f
        power = (power * power + power * 2.0f) / 3.0f
        if (power > 1.0f) power = 1.0f
        return power
    }

    override fun releaseUsing(stack: ItemStack, level: Level, entity: LivingEntity, timeLeft: Int){
        if(entity !is Player) return

        val charge = this.getUseDuration(stack) - timeLeft
        val power = getCustomPowerForTime(charge)

        if(power < 0.1f) return

        if(!level.isClientSide){
            val arrow = object : Arrow(level, entity) {
                override fun onHitEntity(result: EntityHitResult){
                    super.onHitEntity(result)
                    val target = result.entity
                    if(target is Player){
                        target.addEffect(MobEffectInstance(MobEffects.HEALTH_BOOST, 200, 1, true, true))
                    }
                    else if (target is LivingEntity){
                        target.hurt(level.damageSources().magic(), 4.0f)
                        target.addEffect(MobEffectInstance(MobEffects.POISON, 200, 2, false, false, false))
                    }
                }
            }


        }

    }





}