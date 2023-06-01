package git.tsunami047.tsunamidckit;

import eos.moe.dragoncore.network.PacketSender;
import git.tsunami047.tsunamidckit.async.MyThreadPool;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.CompletableFuture;

/**
 *@Author: natsumi
 *@CreateTime: 2023-06-01  18:09
 *@Description: 对龙核的指令进行封装
 */
public class PacketFactory {

    /**
     * @date 2023/6/1 18:39
     * @param player 玩家名字
     * @param fileName 文件名
     * @param yaml yaml文件

     * @description 令玩家在当前界面上打开一个二级界面
     */
    public void openSubGUI(Player player,String fileName, YamlConfiguration yaml) {
        MyThreadPool.singleExecute(()-> {
            PacketSender.sendYaml(player, fileName, yaml);
            PacketSender.sendRunFunction(player,
                    "",
                    "方法.打开二级界面('"+fileName+"')",
                    false);
        });
    }

    /**
     * @date 2023/6/1 20:33
     * @param player 玩家对象
     * @param fileName 文件名
     * @param yaml yaml文件

     * @description 发送之后然后打开这个界面
     */
    public void openGUI(Player player,String fileName, YamlConfiguration yaml){
        MyThreadPool.singleExecute(()-> {
            PacketSender.sendYaml(player, fileName, yaml);
            PacketSender.sendOpenGui(player, fileName);
        });
    }

    /**
     * @date 2023/6/1 19:28
     * @param player 玩家
     * @param fileName 文件名
     * @param yaml yaml文件
     * @return CompletableFuture<Void>
     * @description 异步执行结束后会返回一个CompletableFuture告知,此时在打开inventory
     */
    public CompletableFuture<Void> open(Player player,String fileName,YamlConfiguration yaml) {
        return CompletableFuture.supplyAsync(() -> {
            PacketSender.sendYaml(player, fileName, yaml);
            return null;
        });
    }
}
