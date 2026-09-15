package io.github.nk00657.takeyari.common.item
import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.Multimap
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.SwordItem
import net.minecraft.world.item.Tier
import net.minecraft.world.phys.AABB
import net.minecraftforge.common.ForgeMod
import java.util.UUID

class NormalSpearItem (
    tier: Tier,
    attackDamage: Int,
    attackSpeed: Float,
    private val reachBonus: Double,
    private val range: Double,
    properties: Properties
) : SwordItem(tier,attackDamage,attackSpeed,properties) {

    private val REACH_MODIFIER_UUID = UUID.fromString("7f3e82d1-21e4-4c8d-b7f2-69024f0c8684")

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

        if (!level.isClientSide) {
            val range = 5.0 + range

            val area = AABB(
                attacker.x - range, attacker.y - range, attacker.z - range,
                attacker.x + range, attacker.y + range, attacker.z + range
            )

            val nearbyEntities = level.getEntitiesOfClass(LivingEntity::class.java, area){ entity ->
                entity != attacker
            }

            for (mob in nearbyEntities) {
                mob.hurt(level.damageSources().playerAttack(attacker as? net.minecraft.world.entity.player.Player ?: continue), 10.0f)
            }
        }

        return super.hurtEnemy(stack, target, attacker)
    }
}