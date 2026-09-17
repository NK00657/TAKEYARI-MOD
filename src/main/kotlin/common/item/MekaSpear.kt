package io.github.nk00657.takeyari.common.item

import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Tier
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.energy.EnergyStorage
import net.minecraftforge.energy.IEnergyStorage
import kotlin.math.roundToInt

abstract class MekaSpear(
    tier: Tier,
    attackDamage: Int,
    attackSpeed: Float,
    reachBonus: Double,
    protected val capacity: Int,
    protected val maxReceive: Int,
    protected val maxExtract: Int,
    properties: Properties
) : NormalSpearItem(tier, attackDamage, attackSpeed, reachBonus, 0.0, properties) {

    override fun initCapabilities(stack: ItemStack, nbt: CompoundTag?): ICapabilitySerializable<CompoundTag> {
        return SpearCapabilityProvider(stack, nbt)
    }

    protected open inner class SpearCapabilityProvider(
        protected val stack: ItemStack,
        nbt: CompoundTag?
    ) : ICapabilitySerializable<CompoundTag> {

        // 初期化時は単にインスタンスを作るだけにする（apply 内での deserializeNBT 呼び出しを削除）
        protected val energyStorage: EnergyStorage = EnergyStorage(capacity, maxReceive, maxExtract, 0)
        protected val energyOptional: LazyOptional<IEnergyStorage> = LazyOptional.of { energyStorage }

        init {
            // nbt が渡ってきた場合は自前の deserializeNBT を通す
            if (nbt != null) {
                deserializeNBT(nbt)
            }
        }

        override fun <T : Any?> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
            if (cap == ForgeCapabilities.ENERGY) {
                return energyOptional.cast()
            }
            return getCustomCapability(cap, side)
        }

        protected open fun <T : Any?> getCustomCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
            return LazyOptional.empty()
        }

        override fun serializeNBT(): CompoundTag {
            val tag = CompoundTag()
            tag.putInt("Energy", energyStorage.energyStored)
            writeCustomNBT(tag)
            return tag
        }

        override fun deserializeNBT(nbt: CompoundTag) {
            // "Energy" キーが存在し、かつ型が Int の場合のみ安全に復元
            if (nbt.contains("Energy", Tag.TAG_INT.toInt())) {
                energyStorage.deserializeNBT(nbt.get("Energy"))
            }
            readCustomNBT(nbt)
        }

        protected open fun writeCustomNBT(tag: CompoundTag) {}
        protected open fun readCustomNBT(tag: CompoundTag) {}
    }

    override fun isBarVisible(stack: ItemStack): Boolean = true

    override fun getBarWidth(stack: ItemStack): Int {
        val energy = stack.getCapability(ForgeCapabilities.ENERGY)
            .map {
                if (it.maxEnergyStored > 0) it.energyStored.toFloat() / it.maxEnergyStored.toFloat() else 0f
            }
            .orElse(0f)
        return (13f * energy).roundToInt()
    }

    override fun getBarColor(stack: ItemStack): Int = 0x00E5FF
}