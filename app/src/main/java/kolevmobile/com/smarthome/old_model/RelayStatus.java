//package kolevmobile.com.smarthome.old_model;
//
///**
// * Created by me on 31/10/2017.
// */
//
//public class RelayStatus extends SensorData {
//
//    private static final int RELAY_1 = 0b00000001;
//    private static final int RELAY_2 = 0b00000010;
//    private static final int RELAY_3 = 0b00000100;
//    private static final int RELAY_4 = 0b00001000;
//    private static final int RELAY_5 = 0b00010000;
//    private static final int RELAY_6 = 0b00100000;
//    private static final int RELAY_7 = 0b01000000;
//    private static final int RELAY_8 = 0b10000000;
//
//    private int relayStatus;
//
//    public int getRelayStatus() {
//        return relayStatus;
//    }
//
//    public void setRelayStatus(int relayStatus) {
//        this.relayStatus = relayStatus;
//    }
//
//    private boolean getRelayStatus(int i) {
//        if (i < 1 || i > 8) throw new IllegalArgumentException();
//        int byte_mask = 1 << (i - 1);
//        return (relayStatus & byte_mask) == byte_mask;
//    }
//
//    private boolean getStatusRelay1() {
//        return (relayStatus & RELAY_1) == RELAY_1;
//    }
//
//}
