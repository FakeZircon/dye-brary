package zircon.dyebrary;

import net.minecraft.block.MapColor;
import net.minecraft.util.DyeColor;
import net.minecraft.util.StringIdentifiable;

import java.util.ArrayList;

public class ModDyeColour implements StringIdentifiable {
    //todo expose constructor and global list of dyes, these values shouldn't even be here but I don't know where else to keep them in the meantime :)
//    CANDY_APPLE("candy_apple", 0xf73838,MapColor.BRIGHT_RED, 0xf73838, 0xf73838),
//    CLEMENTINE("clementine", 0xf79738, MapColor.ORANGE, 0xf79738, 0xf79738),
//    LEMON("lemon", 0xf7f738, MapColor.YELLOW, 0xf7f738, 0xf7f738),
//    CHARTREUSE("chartreuse", 0x97f738, MapColor.EMERALD_GREEN, 0x97f738, 0x97f738),
//    HARLEQUIN("harlequin", 0x34e834, MapColor.PALE_GREEN, 0x34e834, 0x34e834),
//    SEAFOAM("seafoam", 0x38f797, MapColor.BRIGHT_TEAL, 0x38f797, 0x38f797),
//    ROBINS_EGG("robins_egg", 0x38f7f7, MapColor.DIAMOND_BLUE, 0x38f7f7, 0x38f7f7),
//    DENIM("denim", 0x74b6f7, MapColor.DARK_AQUA, 0x74b6f7, 0x74b6f7),
//    PIGEON_BLUE("pigeon_blue", 0x8686f7, MapColor.TERRACOTTA_BLUE, 0x8686f7, 0x8686f7),
//    SEANCE("seance", 0x9738f7, MapColor.PURPLE, 0x9738f7, 0x9738f7),
//    BYZANTIUM("byzantium", 0xc252f7, MapColor.MAGENTA, 0xc252f7, 0xc252f7),
//    NULL_PINK("null_pink", 0xf738f7, MapColor.MAGENTA, 0xf738f7, 0xf738f7),
//    CERISE("cerise", 0xf73897, MapColor.TERRACOTTA_PINK, 0xf73897, 0xf73897),
//    GUNMETAL("gunmetal", 0x5a5a5a, MapColor.TERRACOTTA_GRAY, 0x5a5a5a, 0x5a5a5a),
//    APPARITION("apparition", 0xa2a2a2, MapColor.TERRACOTTA_LIGHT_GRAY, 0xa2a2a2, 0xa2a2a2),
//    NACRE("nacre", 0xf7f7f7, MapColor.OFF_WHITE, 0xf7f7f7, 0xf7f7f7);

    private final String name;
    private final MapColor mapColor;
    private final float[] colorComponents;
    private final int fireworkColor;
    private final int signColor;
    private final int color;

    public ModDyeColour(String name, int color, MapColor mapColor, int fireworkColor, int signColor) {
        this.name = name;
        this.mapColor = mapColor;
        this.signColor = signColor;
        this.color = color;
        int j = (color & 0xFF0000) >> 16;
        int k = (color & 0xFF00) >> 8;
        int l = (color & 0xFF);
        this.colorComponents = new float[]{j / 255.0F, k / 255.0F, l / 255.0F};
        this.fireworkColor = fireworkColor;
    }

    public String getName() {
        return this.name;
    }

    public int getColor() { return this.color; }

    public float[] getColorComponents() {
        return this.colorComponents;
    }

    public MapColor getMapColor() {
        return this.mapColor;
    }

    public int getFireworkColor() {
        return this.fireworkColor;
    }

    public int getSignColor() {
        return this.signColor;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public static ArrayList<ModDyeColour> DyeList = new ArrayList<ModDyeColour>();
}
