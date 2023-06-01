package git.tsunami047.tsunamidckit;

import eos.moe.dragoncore.network.PacketSender;
import git.tsunami047.tsunamidckit.async.MyThreadPool;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Set;

/**
 *@Author: natsumi
 *@CreateTime: 2023-06-01  18:42
 *@Description: ?
 */
public class DynamicUtility {

    /**
     * @date 2023/6/1 18:52
     * @param player 玩家
     * @param configurationSections 要给玩家添加的ConfigurationSection

     * @description 给玩家打开的页面添加一个configurationSection
     */
    public void addComponentsInOpen(Player player, ConfigurationSection... configurationSections){
        MyThreadPool.singleExecute(()-> {
            StringBuilder sb = new StringBuilder();
            for (ConfigurationSection section : configurationSections) {
                String name = section.getName();
                Set<String> keys = section.getKeys(false);
                sb.append("方法.添加组件(方法.新建组件('").append(name).append("'));");
                for (String key : keys) {
                    sb.append("方法.设置组件值('").append(name).append("','").append(key).append("','").append(section.getString(key)).append("');");
                }
            }
            PacketSender.sendRunFunction(player, "", sb.toString(), false);
        });
    }



}
