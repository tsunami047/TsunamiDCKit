package git.tsunami047.tsunamidckit;
/**
 *@Author: natsumi
 *@CreateTime: 2023-06-01  18:47
 *@Description: ?
 */
public class FunctionImpl implements TsunamiDCKitAPI{

    private static final DynamicUtility dynamicUtility = new DynamicUtility();

    public DynamicUtility getDynamicUtility(){
        return dynamicUtility;
    }

    private static final PacketFactory packetFactory = new PacketFactory();

    public PacketFactory getPacketFactory(){
        return packetFactory;
    }



}
