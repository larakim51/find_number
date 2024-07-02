package controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import model.MultiSession;
import network.GameClient;

public class RoomController {
    private Map<String, MultiSession> sessions;
    private Socket socket;
    private ObjectOutputStream out;

    public RoomController() {
        this.sessions = new HashMap<>();
        try {
            socket = new Socket("localhost", 12345);
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void createRoom(String roomName) {
        // Logic tạo phòng
        boolean success = createRoomLogic(roomName);
        if (success) {
            try {
                GameClient client = GameClient.getInstance();
                client.sendMessage("CREATE_ROOM:" + roomName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Xử lý tạo phòng thất bại
        }
    }
    private boolean createRoomLogic(String roomName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createRoomLogic'");
    }
    public MultiSession createSession() {
        MultiSession session = new MultiSession();
        sessions.put(session.getSessionId(), session);
        return session;
    }

    public MultiSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }
}
