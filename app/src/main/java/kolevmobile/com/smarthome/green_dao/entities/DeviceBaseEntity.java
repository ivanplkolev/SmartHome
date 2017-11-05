//package kolevmobile.com.smarthome.green_dao.entities;
//
//import org.greenrobot.greendao.annotation.Entity;
//import org.greenrobot.greendao.annotation.Id;
//import org.greenrobot.greendao.annotation.Transient;
//
//@Entity(nameInDb = "sensor_data")
//public class DeviceBaseEntity {
//
//    @Transient
//    public static final int DHT_DEVICE = 1;
//    @Transient
//    public static final int CUSTOM_SENSOR_DEVICE = 2;
//    @Transient
//    public static final int RELAY_DEVICE = 3;
//
//
//    @Id(autoincrement = true)
//    private long id;
//
//    private String name;
//
//    private String description;
//
//    private String urlAdress;
//
//    private int port;
//
//    private int deviceType;
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getUrlAdress() {
//        return urlAdress;
//    }
//
//    public void setUrlAdress(String urlAdress) {
//        this.urlAdress = urlAdress;
//    }
//
//    public int getPort() {
//        return port;
//    }
//
//    public void setPort(int port) {
//        this.port = port;
//    }
//
//    public int getDeviceType() {
//        return deviceType;
//    }
//
//    public void setDeviceType(int deviceType) {
//        this.deviceType = deviceType;
//    }
//}
