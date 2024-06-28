package controller;

import java.util.HashMap;
import java.util.Map;
import model.Player;
import model.MultiSession;

public class RoomController {
    private Map<String, MultiSession> sessions;

    public RoomController() {
        this.sessions = new HashMap<>();
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
