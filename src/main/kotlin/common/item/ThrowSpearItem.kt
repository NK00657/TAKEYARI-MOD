package io.github.nk00657.takeyari.common.item

import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.Multimap
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.projectile.AbstractArrow
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.ThrownTrident
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Tier
import net.minecraft.world.item.TridentItem
import net.minecraft.world.item.UseAnim
import net.minecraft.world.level.Level
import net.minecraftforge.common.ForgeMod
import java.util.UUID

class ThrowSpearItem(
    private val tier: Tier,
    private val attackDamage: Double,
    private val attackSpeed: Double,
    private val reachBonus: Double,
    private val velocity: Float,
    private val inaccuracy: Float,
    properties: Properties
) : TridentItem(properties.defaultDurability(tier.uses)) {

    private val REACH_UUID = UUID.fromString("4e5f6a7b-8c9d-0e1f-2a3b-4c5d6e7f8a9b")

    override fun getUseAnimation(stack: ItemStack): UseAnim = UseAnim.SPEAR

    override fun releaseUsing(stack: ItemStack, level: Level, entityLiving: LivingEntity, timeLeft: Int) {
        if (entityLiving !is Player) return

        val duration = this.getUseDuration(stack) - timeLeft
        if (duration >= 1) {
            if (!level.isClientSide) {

                stack.hurtAndBreak(1, entityLiving) { p -> p.broadcastBreakEvent(entityLiving.usedItemHand) }

                val trident = ThrownTrident(level, entityLiving, stack)


                trident.shootFromRotation(entityLiving, entityLiving.xRot, entityLiving.yRot, 0.0f, this.velocity, this.inaccuracy)

                if (entityLiving.abilities.instabuild) {
                    trident.pickup = AbstractArrow.Pickup.CREATIVE_ONLY
                }

                level.addFreshEntity(trident)

                level.playSound(
                    null,
                    trident.x, trident.y, trident.z,
                    SoundEvents.TRIDENT_THROW,
                    SoundSource.PLAYERS,
                    1.0f,
                    1.0f
                )

                if (!entityLiving.abilities.instabuild) {
                    entityLiving.inventory.removeItem(stack)
                }
            }

            entityLiving.awardStat(Stats.ITEM_USED.get(this))
        }
    }

    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val stack = player.getItemInHand(hand)
        if (stack.damageValue >= stack.maxDamage - 1) {
            return InteractionResultHolder.fail(stack)
        }
        player.startUsingItem(hand)
        return InteractionResultHolder.consume(stack)
    }

    override fun getDefaultAttributeModifiers(slot: EquipmentSlot): Multimap<Attribute, AttributeModifier> {
        val modifiers = ImmutableMultimap.builder<Attribute, AttributeModifier>()

        if (slot == EquipmentSlot.MAINHAND) {
            // 攻撃力
            modifiers.put(
                Attributes.ATTACK_DAMAGE,
                AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Spear damage", attackDamage + tier.attackDamageBonus, AttributeModifier.Operation.ADDITION)
            )
            // 攻撃速度
            modifiers.put(
                Attributes.ATTACK_SPEED,
                AttributeModifier(BASE_ATTACK_SPEED_UUID, "Spear attack speed", attackSpeed, AttributeModifier.Operation.ADDITION)
            )
            // リーチ
            modifiers.put(
                ForgeMod.ENTITY_REACH.get(),
                AttributeModifier(REACH_UUID, "Spear reach", reachBonus, AttributeModifier.Operation.ADDITION)
            )
        }



        return modifiers.build()
    }

}