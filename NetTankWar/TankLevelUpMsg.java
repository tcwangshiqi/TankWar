package NetTankWar;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * Tank升级的消息
 * @author wangshiqihaha
 *
 */
public class TankLevelUpMsg implements Msg {
	
	int msgType = Msg.TANK_LEVEL_UP_MSG;

	TankClient tc;
	
	int id;
	
	/**
	 * 根据打死该tank（发出TankDeadMsg的Tank)的子弹所属的tank的id号构建构造函数
	 * @param id 打死该tank（发出TankDeadMsg的Tank)的子弹所属的tank的id号
	 */
	public TankLevelUpMsg(int id) {
		this.id = id;
		
	}
	
	/**
	 * 根据消息产生的场所构建新的消息
	 * @param tc
	 */
	public TankLevelUpMsg(TankClient tc) {
		this.tc=tc;
	}
	
	@Override
	/**
	 * 发送相关的消息
	 * @param ds 通过该socket发送数据
	 * @param IP 数据的目标IP
	 * @param udpPort 数据的目标端口
	 */
	public void send(DatagramSocket ds, String IP, int udpPort) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(msgType);
			dos.writeInt(id);

		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] buf = baos.toByteArray();
		try {
			DatagramPacket dp = new DatagramPacket(buf, buf.length,
					new InetSocketAddress(IP, udpPort));
			ds.send(dp);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}

	@Override
	/**
	 * 分析接收到的消息数据
	 * @param dis 接收到的消息数据的输入流
	 */
	public void parse(DataInputStream dis) {
		try {
			int id = dis.readInt();
			
			if (tc.myTank.id == id) {
				tc.myTank.level++;
				return;
			}
			
			for (int i = 0; i < tc.tanks.size(); i++) {
				Tank t = tc.tanks.get(i);
				if (t.id == id) {
					t.level++;
					break;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
