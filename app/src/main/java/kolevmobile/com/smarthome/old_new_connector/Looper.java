//package kolevmobile.com.smarthome.old_new_connector;
//
//import java.util.concurrent.PriorityBlockingQueue;
//
///**
// * Created by me on 29/10/2017.
// */
//
//public class Looper extends Thread {
//
//    private PriorityBlockingQueue<Task> queue;
//
//    public Looper() {
//        super();
//
//    }
//
//    public void run() {
//        while (true) {
//            try {
//                if (queue.size() > 0) {
//                    Task task = queue.take();
//                    task.execute();
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void addTask(Task task){
//        queue.add(task);
//    }
//}
