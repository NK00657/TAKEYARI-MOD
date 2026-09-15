package io.github.nk00657.takeyari.core.init

import io.github.nk00657.takeyari.TakeYari
import io.github.nk00657.takeyari.common.item.NormalSpearItem
import io.github.nk00657.takeyari.common.item.ThrowSpearItem
import io.github.nk00657.takeyari.common.item.HeavySpearItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.Tiers
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.fml.ModList
import thedarkcolour.kotlinforforge.forge.registerObject

object ItemInit {
    // アイテム専用の遅延レジストリを作成
    val ITEMS: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, TakeYari.MOD_ID)


    val bamboo_spear by ITEMS.registerObject<Item,Item>("bamboo_spear") {
        NormalSpearItem(
            tier = Tiers.WOOD,
            attackDamage = 3,
            attackSpeed = -2.4f,
            reachBonus = 10.0,
            properties = Item.Properties().rarity(Rarity.COMMON).defaultDurability(10),
            range = 0.0
        )
    }
    val rainbow_bamboo_spear by ITEMS.registerObject("rainbow_bamboo_spear") {
        NormalSpearItem(
            tier = Tiers.NETHERITE,
            attackDamage = 7,
            attackSpeed = -0.0f,
            reachBonus = 1000.0,
            properties = Item.Properties().rarity(Rarity.EPIC).defaultDurability(777),
            range = 0.0
        )
    }
    val tactical_bamboo_spear by ITEMS.registerObject("tactical_bamboo_spear") {
        NormalSpearItem(
            tier = Tiers.IRON,
            attackDamage = 10,
            attackSpeed = -0.0f,
            reachBonus = 20.0,
            properties = Item.Properties().rarity(Rarity.RARE).defaultDurability(10000),
            range = 0.0
        )
    }
    val clothesline by ITEMS.registerObject("clothesline") {
        NormalSpearItem(
            tier = Tiers.IRON,
            attackDamage = 5,
            attackSpeed = 99.6f,
            reachBonus = 1.8,
            properties = Item.Properties().rarity(Rarity.UNCOMMON).defaultDurability(1024),
            range = 10.0
        )
    }
    val a_bamboo_spear_that_can_shoot_down_a_b29 by ITEMS.registerObject("a_bamboo_spear_that_can_shoot_down_a_b29") {
        NormalSpearItem(
            tier = Tiers.NETHERITE,
            attackDamage = 100,
            attackSpeed = -0.0f,
            reachBonus = 10000.0,
            properties = Item.Properties().rarity(Rarity.EPIC).defaultDurability(29),
            range = -5.0
        )
    }
    val half_hearted by ITEMS.registerObject("half_hearted") {
        ThrowSpearItem(
            tier = Tiers.WOOD,
            attackDamage = 500.0,
            attackSpeed = -2.4,
            reachBonus = -5.0,
            velocity = 2.0f,
            inaccuracy = 180.0f,
            properties = Item.Properties().rarity(Rarity.UNCOMMON).defaultDurability(1)
        )
    }
    val shimoheihes_thrown_bamboo_spear by ITEMS.registerObject("shimoheihes_thrown_bamboo_spear") {
        ThrowSpearItem(
            tier = Tiers.IRON,
            attackDamage = 10.0,
            attackSpeed = -2.4,
            reachBonus = 5.0,
            velocity = 8.0f,
            inaccuracy = 0.0f,
            properties = Item.Properties().rarity(Rarity.RARE).defaultDurability(500)
        )
    }
    val rake by ITEMS.registerObject("rake") {
        HeavySpearItem(
            tier = Tiers.WOOD,
            attackDamage = 5.0f,
            attackSpeed = 0.0f,
            reachBonus = 1.0,
            strength = 0,
            properties = Item.Properties().rarity(Rarity.UNCOMMON).defaultDurability(1024)
        )
    }
}