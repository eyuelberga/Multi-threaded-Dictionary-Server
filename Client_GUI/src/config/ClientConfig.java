package config;

public class ClientConfig {
    private ClientConfig(){
        hostName = "localhost";
        port = 8080;
    }

    public String getHostName() {
        return hostName;
    }

    public synchronized void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Integer getPort() {
        return port;
    }

    public synchronized void setPort(Integer port) {
        this.port = port;
    }

    private static final ClientConfig INSTANCE = new ClientConfig();
    public static ClientConfig getInstance(){
        return INSTANCE;
    }
    private String hostName;
    private Integer port;
}
