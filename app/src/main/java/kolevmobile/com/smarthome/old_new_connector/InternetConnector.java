//package kolevmobile.com.smarthome.old_new_connector;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.URL;
//import java.net.URLConnection;
//
///**
// * Created by me on 29/10/2017.
// */
//
//public class InternetConnector {
//
//    public static StringBuilder readUrl(String urlAdress) throws NoDeviceException {
//        StringBuilder sb = new StringBuilder();
//        try {
//            URL url = new URL(urlAdress);
//            URLConnection connection = url.openConnection();
//            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String returnString = "";
//            while ((returnString = in.readLine()) != null) {
//                sb.append(returnString);
//            }
//            in.close();
//        } catch (Exception e) {
//            throw new NoDeviceException();
//        }
//        return sb;
//    }
//
//}
