package youyihj.zenutils.impl.network;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import youyihj.zenutils.ZenUtils;
import youyihj.zenutils.api.network.IClientMessageHandler;
import youyihj.zenutils.api.network.IServerMessageHandler;

/**
 * @author youyihj
 */
public enum ZenUtilsNetworkHandler {
    INSTANCE;

    private final SimpleNetworkWrapper channel = NetworkRegistry.INSTANCE.newSimpleChannel(ZenUtils.MODID);
    private final Int2ObjectArrayMap<IClientMessageHandler> clientHandlers = new Int2ObjectArrayMap<>();
    private final Int2ObjectArrayMap<IServerMessageHandler> serverHandlers = new Int2ObjectArrayMap<>();

    ZenUtilsNetworkHandler() {
        channel.registerMessage(ZenUtilsMessage.Server2Client.class, ZenUtilsMessage.Server2Client.class, 0, Side.CLIENT);
        channel.registerMessage(ZenUtilsMessage.Client2Server.class, ZenUtilsMessage.Client2Server.class, 1, Side.SERVER);
    }

    public void registerServer2ClientMessage(String key, IClientMessageHandler clientMessageHandler) {
        clientHandlers.put(key.hashCode(), clientMessageHandler);
    }

    public void registerClient2ServerMessage(String key, IServerMessageHandler serverMessageHandler) {
        serverHandlers.put(key.hashCode(), serverMessageHandler);
    }

    public void sendToDimension(String key, IServerMessageHandler serverMessageHandler, int dimensionID) {
        channel.sendToDimension(getServer2ClientMessage(key, serverMessageHandler), dimensionID);
    }

    public void sendToAll(String key, IServerMessageHandler serverMessageHandler) {
        channel.sendToAll(getServer2ClientMessage(key, serverMessageHandler));
    }

    public void sendToAllAround(String key, IServerMessageHandler serverMessageHandler, double x, double y, double z, double range, int dimensionID) {
        channel.sendToAllAround(getServer2ClientMessage(key, serverMessageHandler), new NetworkRegistry.TargetPoint(dimensionID, x, y, z, range));
    }

    public void sendTo(String key, IServerMessageHandler serverMessageHandler, EntityPlayerMP player) {
        channel.sendTo(getServer2ClientMessage(key, serverMessageHandler), player);
    }

    public void sendToServer(String key, IClientMessageHandler clientMessageHandler) {
        channel.sendToServer(getClient2ServerMessage(key, clientMessageHandler));
    }

    IClientMessageHandler getClientMessageHandler(int key) {
        return clientHandlers.getOrDefault(key, IClientMessageHandler.NONE);
    }

    IServerMessageHandler getServerMessageHandler(int key) {
        return serverHandlers.getOrDefault(key, IServerMessageHandler.NONE);
    }

    private ZenUtilsMessage.Server2Client getServer2ClientMessage(String key, IServerMessageHandler serverMessageHandler) {
        ZenUtilsMessage.Server2Client message = new ZenUtilsMessage.Server2Client();
        message.setKey(key);
        message.setServerMessageHandler(serverMessageHandler);
        return message;
    }

    private ZenUtilsMessage.Client2Server getClient2ServerMessage(String key, IClientMessageHandler clientMessageHandler) {
        ZenUtilsMessage.Client2Server message = new ZenUtilsMessage.Client2Server();
        message.setKey(key);
        message.setClientMessageHandler(clientMessageHandler);
        return message;
    }
}
