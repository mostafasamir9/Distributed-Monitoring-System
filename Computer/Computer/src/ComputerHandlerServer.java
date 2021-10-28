import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ComputerHandlerServer extends Thread {

	private Socket server;

	public ComputerHandlerServer(Socket server) {
		this.server = server;

	}

	@Override
	public void run() {

		/* send data */
		DataOutputStream dos = null;

		try {
			dos = new DataOutputStream(server.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			dos.writeUTF(ComputerHandlerNode.data + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/* recieve data */

		DataInputStream dis = null;
		try {
			dis = new DataInputStream(server.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String st = null;
		try {
			st = new String(dis.readUTF());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(st);

		if (st.contains("server recieved data successfully")) {
			try {
				receiveFile("MA.exe");
				System.out.println("Recieved Mobile Agent from server");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ComputerHandlerNode.data = " ";
		} else if (st.contains("server recieved unrecognized data")) {
			try {
				try {
					receiveFile("MA.exe");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Recieved default Mobile Agent from server");
		}

	}

	// function to recieve file

	public void receiveFile(String fileName) throws Exception {
		int bytes = 0;
		FileOutputStream fileOutputStream = new FileOutputStream(fileName);

		DataInputStream dis = null;
		try {
			dis = new DataInputStream(server.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long size = dis.readLong(); // read file size
		byte[] buffer = new byte[4 * 1024];
		while (size > 0 && (bytes = dis.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
			fileOutputStream.write(buffer, 0, bytes);
			size -= bytes; // read upto file size
		}
		fileOutputStream.close();
	}

}
