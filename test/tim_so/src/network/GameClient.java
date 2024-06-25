package network;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class GameClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8080));

        // Gửi dữ liệu đến server
        ByteBuffer buffer = ByteBuffer.wrap("Hello Server!".getBytes());
        socketChannel.write(buffer);

        // Đọc dữ liệu từ server
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        int bytesRead = socketChannel.read(readBuffer);
        if (bytesRead > 0) {
            // Xử lý dữ liệu nhận được
        }
    }
}

