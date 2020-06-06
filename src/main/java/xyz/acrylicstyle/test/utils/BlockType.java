package xyz.acrylicstyle.test.utils;

import org.bukkit.Material;
import util.CollectionList;
import util.ICollectionList;

public enum BlockType {
    DIAMOND_ORE("ダイヤモンド鉱石", Material.DIAMOND_ORE),
    IRON_ORE("鉄鉱石", Material.IRON_ORE),
    GOLD_ORE("金鉱石", Material.GOLD_ORE),
    CHEST("チェスト", Material.CHEST), // you cant break trapped chest so it can be used to troll
    REDSTONE_ORE("レッドストーン鉱石", Material.REDSTONE_ORE),
    LAPIS_ORE("ラピスラズリ鉱石", Material.LAPIS_ORE),
    COAL_ORE("石炭鉱石", Material.COAL_ORE),
    EMERALD_ORE("エメラルド鉱石", Material.EMERALD_ORE),
    STONE("石",
            /* solid blocks */
            Material.STONE,
            Material.ANDESITE,
            Material.COBBLESTONE,
            Material.DIORITE,
            Material.GRANITE
    ),
    DIRT("土",
            Material.DIRT,
            Material.COARSE_DIRT,
            Material.PODZOL,
            Material.GRASS_BLOCK,
            Material.GRASS,
            Material.TALL_GRASS,
            Material.GRASS_PATH
    ),
    WOOD("原木",
            Material.ACACIA_LOG,
            Material.BIRCH_LOG,
            Material.DARK_OAK_LOG,
            Material.OAK_LOG,
            Material.JUNGLE_LOG,
            Material.SPRUCE_LOG
    ),
    ATTACK("攻撃", (Material) null);

    private final String name;
    private final CollectionList<Material> types;

    BlockType(String name, Material... types) {
        this.name = name;
        this.types = ICollectionList.asList(types);
    }

    public String getName() {
        return name;
    }

    public CollectionList<Material> getTypes() {
        return types;
    }
}
