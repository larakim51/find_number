package network;

public interface ClientHandlerDelegate {
    void loginSuccess(String username);
    void gameFinished();
    void clientDisconnected();
}
