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
 * ����̹�˱����е���Ϣ��
 * @author mashibing
 *
 */
public class TankDeadMsg implements Msg {
	int msgType = Msg.TANK_DEAD_MSG;

	TankClient tc;
	
	int life;
	
	int id;
	/**
	 * ����̹�˵�id������Ϣ
	 * @param id ̹��id
	 */

	int TankId;
	/**
	 * ɱ����tank��tankId
	 */
	
	public TankDeadMsg(int id,int life,int TankId) {
		this.id = id;
		this.life = life;
		this.TankId=TankId;
	}
	
	/**
	 * ������Ϣ�����ĳ��������µ���Ϣ
	 * @param tc
	 */
	public TankDeadMsg(TankClient tc) {
		this.tc = tc;
	}
	
	/**
	 * �������յ�����Ϣ����
	 * @param dis ���յ�����Ϣ���ݵ�������
	 */
	public void parse(DataInputStream dis) {
		try {
			int id = dis.readInt();
			int life = dis.readInt();
			
			if (tc.myTank.id == id) {
				return;
			}

			// System.out.println("id:" + id + "-x:" + x + "-y:" + y + "-dir:" +
			// dir + "-good:" + good);
			for (int i = 0; i < tc.tanks.size(); i++) {
				Tank t = tc.tanks.get(i);
				if (t.id == id) {
					t.setLife(life);
					if(life<=0) {
						t.setLive(false);
						tc.tanks.remove(t);
					}
					break;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
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
			dos.writeInt(life);
			
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

}
