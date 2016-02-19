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
 * Tank��������Ϣ
 * @author wangshiqihaha
 *
 */
public class TankLevelUpMsg implements Msg {
	
	int msgType = Msg.TANK_LEVEL_UP_MSG;

	TankClient tc;
	
	int id;
	
	/**
	 * ���ݴ�����tank������TankDeadMsg��Tank)���ӵ�������tank��id�Ź������캯��
	 * @param id ������tank������TankDeadMsg��Tank)���ӵ�������tank��id��
	 */
	public TankLevelUpMsg(int id) {
		this.id = id;
		
	}
	
	/**
	 * ������Ϣ�����ĳ��������µ���Ϣ
	 * @param tc
	 */
	public TankLevelUpMsg(TankClient tc) {
		this.tc=tc;
	}
	
	@Override
	/**
	 * ������ص���Ϣ
	 * @param ds ͨ����socket��������
	 * @param IP ���ݵ�Ŀ��IP
	 * @param udpPort ���ݵ�Ŀ��˿�
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
	 * �������յ�����Ϣ����
	 * @param dis ���յ�����Ϣ���ݵ�������
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
