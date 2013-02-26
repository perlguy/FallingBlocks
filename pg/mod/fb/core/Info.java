package pg.mod.fb.core;

public class Info {
	/**
	 * Reference
	 * 
	 * General purpose library to contain mod related constants
	 * 
	 * @author perlguy
	 * 
	 */

    /* Debug Mode On-Off */
    public static final boolean DEBUG_MODE = false;

    /* General Mod related constants */
    public static final String MOD_ID = "FB1";
    public static final String MOD_NAME = "FallingBlock";
    public static final String VERSION = "@VERSION@";
    public static final String CHANNEL_NAME = MOD_ID;
    public static final int TPS = 20;
    // public static final int SHIFTED_ID_RANGE_CORRECTION = 256;
    public static final String SERVER_PROXY_CLASS = "pg.mod.fb.core.proxy.CommonProxy";
    public static final String CLIENT_PROXY_CLASS = "pg.mod.fb.core.proxy.ClientProxy";
    // public static final int VERSION_CHECK_ATTEMPTS = 3;

}
