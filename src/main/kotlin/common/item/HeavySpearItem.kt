package io.github.nk00657.takeyari.common.item

import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.Multimap
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.AxeItem
import net.minecraft.world.item.Tier
import net.minecraftforge.common.ForgeMod
import java.util.UUID

class HeavySpearItem(
    tier: Tier,
    attackDamage: Float,
    attackSpeed: Float,
    private val reachBonus: Double,
    private val strength: Int,
    properties: Properties
) : AxeItem(tier, attackDamage, attackSpeed, properties){
    private val REACH_MODIFIER_UUID = UUID.fromString("7f3e82d1-21e4-4c8d-b7f2-69024f0c8685")

    override fun getDefaultAttributeModifiers(slot: EquipmentSlot): Multimap<Attribute, AttributeModifier> {
        val modifiers = ImmutableMultimap.builder<Attribute, AttributeModifier>()

        modifiers.putAll(super.getDefaultAttributeModifiers(slot))

        if (slot == EquipmentSlot.MAINHAND) {
            modifiers.put(
                ForgeMod.ENTITY_REACH.get(),
                AttributeModifier(
                    REACH_MODIFIER_UUID,
                    "Spear entity reach bonus",
                    reachBonus,
                    AttributeModifier.Operation.ADDITION
                )
            )
        }

        return modifiers.build()
    }

    override fun hurtEnemy(stack: ItemStack, target: LivingEntity, attacker: LivingEntity): Boolean {
        val level = attacker.level()


        if(!level.isClientSide){
            attacker.addEffect(MobEffectInstance(MobEffects.DAMAGE_BOOST, 60, 1+strength, false, false, false))
            attacker.addEffect(MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10, 5, false, false, false))

            target.addEffect(MobEffectInstance(MobEffects.GLOWING, 100, 1, false, false, false))
            target.addEffect(MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 5, false, false, false))
            target.addEffect(MobEffectInstance(MobEffects.BLINDNESS, 60, 1, false, false, false))
            target.addEffect(MobEffectInstance(MobEffects.WEAKNESS, 10, 5, false, false, false))
            target.addEffect(MobEffectInstance(MobEffects.CONFUSION, 60, 1, false, false, false))


            target.hurt(level.damageSources().magic(), 2.0f + strength)

            level.playSound(
                null,
                target.x, target.y, target.z,
                SoundEvents.ANVIL_USE,
                SoundSource.PLAYERS,
                1.5f,
                2.0f
                )

        }



        return super.hurtEnemy(stack, target, attacker)
    }


}