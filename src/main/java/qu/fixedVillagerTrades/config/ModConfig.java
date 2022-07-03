package qu.fixedVillagerTrades.config;

import com.electronwill.nightconfig.core.ConfigSpec;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import net.fabricmc.loader.api.FabricLoader;
import qu.fixedVillagerTrades.FixedVillagerTrades;

import java.util.Arrays;

public class ModConfig {

    public final boolean FIX_TRADES;
    public final float MAX_DISCOUNT;

    public ModConfig() {
        FixedVillagerTrades.LOGGER.info("Reading config file for " + FixedVillagerTrades.MOD_ID);
        CommentedFileConfig config = CommentedFileConfig.of(FabricLoader.getInstance().getConfigDir().resolve(FixedVillagerTrades.MOD_ID + ".toml"));
        config.load();
        checkConfig(config);
        FIX_TRADES = config.get("FixedTrades");
        MAX_DISCOUNT = ((Double)config.get("MaxDiscount")).floatValue();
        config.close();
        FixedVillagerTrades.LOGGER.info("Fixed Trades: " + FIX_TRADES + ", Max Discount: " + MAX_DISCOUNT);
    }

    private static void checkConfig(CommentedFileConfig config) {
        ConfigSpec spec = new ConfigSpec();
        spec.defineInList("FixedTrades", true, Arrays.asList(true, false));
        spec.defineInRange("MaxDiscount", 1.0, 0.0, 1.0);
        if (!spec.isCorrect(config)) {
            FixedVillagerTrades.LOGGER.error("One or more config settings were incorrect, setting to default value(s)");
            config.setComment("FixedTrades", "Whether villager trades are fixed to their profession upon generation. Must be either true or false.");
            config.setComment("MaxDiscount", "The maximum discount a trade can have. Vanilla Minecraft is equivalent to 1.0. Must be between 0.0 and 1.0.");
            ConfigSpec.CorrectionListener listener = (action, path, incorrectValue, correctedValue) -> {
                String pathString = String.join(",", path);
                FixedVillagerTrades.LOGGER.error("Corrected " + pathString + ": was " + incorrectValue + ", is now " + correctedValue);
            };
            spec.correct(config, listener);
            config.save();
        }
    }
}
