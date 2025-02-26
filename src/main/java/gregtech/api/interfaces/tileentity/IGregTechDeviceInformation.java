package gregtech.api.interfaces.tileentity;

import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

/**
 * You are allowed to include this File in your Download, as i will not change it.
 */
public interface IGregTechDeviceInformation {

    /**
     * Is this even a TileEntity which allows GregTech Sensor Kits? I need things like this Function for
     * MetaTileEntities, you MUST check this!!! Do not assume that it's a Information returning Device, when it just
     * implements this Interface.
     */
    default boolean isGivingInformation() {
        return false;
    }

    /**
     * Up to 8 Strings can be returned. Note: If you insert "\\\\" in the String it tries to translate separate Parts of
     * the String instead of the String as a whole.
     *
     * @return an Array of Information Strings.
     */
    default String[] getInfoData() {
        return new String[] {};
    }

    /**
     * Returns a map of key-value pairs containing callbacks to device information.
     *
     * @return a Map where keys are information categories and values are suppliers of corresponding details.
     */
    default Map<String, Supplier<String>> getInfoCallbacks() {
        return Collections.emptyMap();
    }
}
