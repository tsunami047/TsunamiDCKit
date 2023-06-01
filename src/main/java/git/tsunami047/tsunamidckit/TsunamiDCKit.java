package git.tsunami047.tsunamidckit;

import org.bukkit.plugin.java.JavaPlugin;

public final class TsunamiDCKit extends JavaPlugin {

    public volatile static TsunamiDCKit instance;
    private volatile static TsunamiDCKitAPI tsunamiDCKitAPI;


    /**
     * @date 2023/6/1 19:59


     * @description 实例化对象
     */
    public static void init(){
        tsunamiDCKitAPI = new FunctionImpl();
    }


    @Override
    public void onEnable() {
        instance = this;
        init();
    }

    @Override
    public void onDisable() {

    }

    public static TsunamiDCKitAPI getTsunamiDCKitAPI(){
        return tsunamiDCKitAPI;
    }

}
