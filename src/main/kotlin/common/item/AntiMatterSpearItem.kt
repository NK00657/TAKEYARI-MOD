package io.github.nk00657.takeyari.common.item

import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Tier
import net.minecraft.world.level.Level
import net.minecraft.world.phys.AABB
import net.minecraftforge.common.capabilities.ForgeCapabilities


class AntiMatterSpearItem(
    tier: Tier,
    attackDamage: Int,
    attackSpeed: Float,
    reachBonus: Double,
    capacity: Int,
    maxReceive: Int,
    maxExtract: Int,
    properties: Properties
) : MekaSpear(tier,attackDamage,attackSpeed,reachBonus,capacity,maxReceive,maxExtract,properties){
    override fun hurtEnemy(stack: ItemStack, target: LivingEntity, attacker: LivingEntity): Boolean {
        val level = attacker.level()
        if (!level.isClientSide){
            stack.getCapability(ForgeCapabilities.ENERGY).ifPresent { storage ->
                val energyCost = 100000
                if (storage.energyStored >= energyCost){
                    storage.extractEnergy(energyCost, false)

                    attacker.addEffect(MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,40,5,false,false,false))
                    level.explode(
                        attacker,
                        target.x,target.y,target.z,
                        30.0f,
                        Level.ExplosionInteraction.TNT
                    )
                    target.discard()
                    val aabb = attacker.boundingBox.inflate(30.0)
                    val nearbyEntities = level.getEntitiesOfClass(LivingEntity::class.java, aabb){ entity ->
                        entity != attacker
                    }
                    for (mob in nearbyEntities) {
                        mob.hurt(level.damageSources().magic(), 50.0f)
                    }
                    level.playSound(null, target.x, target.y, target.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 5.0f, 0.2f)
                    val serverLevel = level as? ServerLevel
                    serverLevel?.let {
                        it.sendParticles(ParticleTypes.FLASH, target.x, target.y + 1.0, target.z, 5, 0.0, 0.0, 0.0, 0.0)
                        it.sendParticles(ParticleTypes.SONIC_BOOM, target.x, target.y + 1.0, target.z, 3, 0.5, 0.5, 0.5, 0.0)
                        it.sendParticles(ParticleTypes.REVERSE_PORTAL, target.x, target.y, target.z, 200, 2.0, 2.0, 2.0, 0.5)
                    }

                }
            }

        }
        return super.hurtEnemy(stack, target, attacker)
    }
}