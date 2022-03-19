package xyz.titnoas.velocity.PacketListener;

import com.velocitypowered.proxy.protocol.packet.TabCompleteRequest;
import com.velocitypowered.proxy.protocol.packet.TabCompleteResponse;
import dev.simplix.protocolize.api.Direction;
import dev.simplix.protocolize.api.listener.AbstractPacketListener;
import dev.simplix.protocolize.api.listener.PacketReceiveEvent;
import dev.simplix.protocolize.api.listener.PacketSendEvent;

public class TabCompleteListener extends AbstractPacketListener<com.velocitypowered.proxy.protocol.packet.TabCompleteRequest> {


	public TabCompleteListener() {
		super(TabCompleteRequest.class, Direction.UPSTREAM, 0);
	}

	@Override
	public void packetReceive(PacketReceiveEvent<TabCompleteRequest> packetReceiveEvent) {
		var tabCompleteReq = packetReceiveEvent.packet();

		if(!tabCompleteReq.getCommand().equalsIgnoreCase("/"))
			return;

		TabCompleteResponse.Offer offer = new TabCompleteResponse.Offer("/Nice try ;):lol");


		TabCompleteResponse nop = new TabCompleteResponse();
		nop.getOffers().add(offer);
		nop.setTransactionId(tabCompleteReq.getTransactionId());

		packetReceiveEvent.player().sendPacket(nop);
		packetReceiveEvent.cancelled(true);
	}

	@Override
	public void packetSend(PacketSendEvent<TabCompleteRequest> packetSendEvent) {

	}
}
