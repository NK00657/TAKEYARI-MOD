package io.github.nk00657.takeyari.core.init

import io.github.nk00657.takeyari.TakeYari
import io.github.nk00657.takeyari.common.item.AntiMatterSpearItem
import io.github.nk00657.takeyari.common.item.HeavySpearItem
import io.github.nk00657.takeyari.common.item.NormalSpearItem
import io.github.nk00657.takeyari.common.item.RadioActiveSpear
import io.github.nk00657.takeyari.common.item.SupportSpearItem
import io.github.nk00657.takeyari.common.item.ThrowSpearItem
import io.github.nk00657.takeyari.common.item.TimeStopSpear
import net.minecraft.world.item.Item
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.Tiers
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.ModList
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

object ItemInit {
    // アイテム専用の遅延レジストリを作成
    val ITEMS: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, TakeYari.MOD_ID)

    val bamboo_spear: RegistryObject<Item> = ITEMS.register("bamboo_spear") {
        NormalSpearItem(
            tier = Tiers.WOOD,
            attackDamage = 3,
            attackSpeed = -2.4f,
            reachBonus = 10.0,
            properties = Item.Properties().rarity(Rarity.COMMON).defaultDurability(10),
            range = 0.0
        )
    }

    val rainbow_bamboo_spear: RegistryObject<Item> = ITEMS.register("rainbow_bamboo_spear") {
        NormalSpearItem(
            tier = Tiers.NETHERITE,
            attackDamage = 7,
            attackSpeed = -0.0f,
            reachBonus = 1000.0,
            properties = Item.Properties().rarity(Rarity.EPIC).defaultDurability(777),
            range = 0.0
        )
    }

    val tactical_bamboo_spear: RegistryObject<Item> = ITEMS.register("tactical_bamboo_spear") {
        NormalSpearItem(
            tier = Tiers.IRON,
            attackDamage = 10,
            attackSpeed = -0.0f,
            reachBonus = 20.0,
            properties = Item.Properties().rarity(Rarity.RARE).defaultDurability(10000),
            range = 0.0
        )
    }

    val clothesline: RegistryObject<Item> = ITEMS.register("clothesline") {
        NormalSpearItem(
            tier = Tiers.IRON,
            attackDamage = 5,
            attackSpeed = 99.6f,
            reachBonus = 1.8,
            properties = Item.Properties().rarity(Rarity.UNCOMMON).defaultDurability(1024),
            range = 10.0
        )
    }

    val a_bamboo_spear_that_can_shoot_down_a_b29: RegistryObject<Item> = ITEMS.register("a_bamboo_spear_that_can_shoot_down_a_b29") {
        NormalSpearItem(
            tier = Tiers.NETHERITE,
            attackDamage = 100,
            attackSpeed = -0.0f,
            reachBonus = 10000.0,
            properties = Item.Properties().rarity(Rarity.EPIC).defaultDurability(29),
            range = -5.0
        )
    }

    val half_hearted: RegistryObject<Item> = ITEMS.register("half_hearted") {
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

    val shimoheihes_thrown_bamboo_spear: RegistryObject<Item> = ITEMS.register("shimoheihes_thrown_bamboo_spear") {
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

    val rake: RegistryObject<Item> = ITEMS.register("rake") {
        HeavySpearItem(
            tier = Tiers.WOOD,
            attackDamage = 5.0f,
            attackSpeed = 0.0f,
            reachBonus = 1.0,
            strength = 0,
            properties = Item.Properties().rarity(Rarity.UNCOMMON).defaultDurability(1024)
        )
    }

    val a_very_strong_bamboo_spear: RegistryObject<Item> = ITEMS.register("a_very_strong_bamboo_spear") {
        TimeStopSpear(
            properties = Item.Properties().rarity(Rarity.EPIC).defaultDurability(1024)
        )
    }

    val bamboo_tube: RegistryObject<Item> = ITEMS.register("bamboo_tube") {
        SupportSpearItem(
            Item.Properties().rarity(Rarity.UNCOMMON).defaultDurability(32)
        )
    }

    val anti_matter_bamboo_spear: RegistryObject<Item> = ITEMS.register("anti_matter_bamboo_spear"){
        AntiMatterSpearItem(
            tier = Tiers.NETHERITE,
            10,
            -3.2F,
            10.0,
            10000000,
            10000,
            10000,
            properties = Item.Properties().rarity(Rarity.EPIC)
        )
    }

    var URANIUM_BAMBOO_SPEAR: RegistryObject<RadioActiveSpear>? = null

    fun register(bus: IEventBus){
        if (ModList.get().isLoaded("mekanism")){
            println(">>> Mekanism loaded check: true")

            URANIUM_BAMBOO_SPEAR = ITEMS.register(
                "uranium_bamboo_spear"
            ){
                RadioActiveSpear(
                    tier = Tiers.NETHERITE,
                    attackDamage = 10,
                    attackSpeed = -3.2f,
                    capacity = 1000000,
                    maxExtract = 1000,
                    maxReceive = 1000,
                    reachBonus = 10.0,
                    properties = Item.Properties()

                )
            }
        }
        ITEMS.register(bus)
    }


}