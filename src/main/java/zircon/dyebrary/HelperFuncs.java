package zircon.dyebrary;

public class HelperFuncs {
    public static int convertToHex(float[] colComps){
        return ((int)(colComps[0]*255) << 16) + ((int)(colComps[1]*255) << 8) + (int)(colComps[2]*255);
    }
}
