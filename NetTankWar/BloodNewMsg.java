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
 * Ѫ��Ĳ�����Ϣ
 * @author wangshiqihaha
 *
 */
public class BloodNewMsg implements Msg{
	
	int msgType = Msg.BLOOD_NEW_MSG;
	
	TankClient tc;
	
	Blood b;
	
	public BloodNewMsg(Blood b) {
		this.b=b;
	}
	
	public BloodNewMsg(TankClient tc) {
		this.tc=tc;
	}
	
	@Override
	/**
	 * ������Ϣ
	 * @param ds ͨ����socket��������
	 * @param IP ���ݵ�Ŀ��IP
	 * @param udpPort ���ݵ�Ŀ��˿�
	 */
	public void send(DatagramSocket ds, String IP, int udpPort) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(msgType);
			dos.writeInt(b.x);
			dos.writeInt(b.y);
			dos.writeBoolean(b.isLive());
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
	 * ������Ϣ
	 * @param dis ���յ�����Ϣ���ݵ�������
	 */
	public void parse(DataInputStream dis) {
		try {
			
			
			int x = dis.readInt();
			int y = dis.readInt();
			
			boolean live = dis.readBoolean();

			// System.out.println("id:" + id + "-x:" + x + "-y:" + y + "-dir:" +
			// dir + "-good:" + good);
			
			
			Blood b=new Blood();
			b.x=x;
			b.y=y;
			b.setLive(live);
			tc.bloods.add(b);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
