package aboutjava.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @program: springBootPractice
 * @description: 关于ZooKeeper 的使用
 * @author: hu_pf
 * @create: 2020-05-28 15:02
 **/
public class AboutZookeeper {
    private static final String connectString = "localhost:2181,localhost:2181,localhost:2181";

    private static ZooKeeper zkClient;

    // 会话超时
    private static final int SESSION_TIMEOUT = 2000;
    // zookeeper集群地址
    private String hosts = "localhost:2181,localhost:2181,localhost:2181";
    private String groupNode = "locks";
    private String subNode = "sub";
    private boolean haveLock = false;

    private ZooKeeper zk;
    // 记录自己创建的子节点路径
    private volatile String thisPath;

    static {
        try {
            zkClient = new ZooKeeper(connectString, 2000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    System.out.println(event.getType() + "----" + event.getPath());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {

//        Thread.sleep(2000);
        zkClient.delete("/eclipse", -1);
        String nodeCreated = zkClient.create("/eclipse2", "hellozk".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

//        Thread.sleep(2000);
//        AboutZookeeper dl = new AboutZookeeper();
//        dl.connectZookeeper();
//        Thread.sleep(Long.MAX_VALUE);
    }

    /**
     * 连接zookeeper
     */
    public void connectZookeeper() throws Exception {
        zk = new ZooKeeper(hosts, SESSION_TIMEOUT, new Watcher() {
            public void process(WatchedEvent event) {
                try {

                    // 判断事件类型，此处只处理子节点变化事件
                    if (event.getType() == Event.EventType.NodeChildrenChanged && event.getPath().equals("/" + groupNode)) {
                        //获取子节点，并对父节点进行监听
                        List<String> childrenNodes = zk.getChildren("/" + groupNode, true);
                        String thisNode = thisPath.substring(("/" + groupNode + "/").length());
                        // 去比较是否自己是最小id
                        Collections.sort(childrenNodes);
                        if (childrenNodes.indexOf(thisNode) == 0) {
                            //访问共享资源处理业务，并且在处理完成之后删除锁
                            doSomething();

                            //重新注册一把新的锁
                            thisPath = zk.create("/" + groupNode + "/" + subNode, null, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                                    CreateMode.EPHEMERAL_SEQUENTIAL);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // 1、程序一进来就先注册一把锁到zk上
        thisPath = zk.create("/" + groupNode + "/" + subNode, null, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);

        // wait一小会，便于观察
        Thread.sleep(new Random().nextInt(1000));

        // 从zk的锁父目录下，获取所有子节点，并且注册对父节点的监听
        List<String> childrenNodes = zk.getChildren("/" + groupNode, true);

        //如果争抢资源的程序就只有自己，则可以直接去访问共享资源
        if (childrenNodes.size() == 1) {
            doSomething();
            thisPath = zk.create("/" + groupNode + "/" + subNode, null, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL);
        }
    }

    /**
     * 处理业务逻辑，并且在最后释放锁
     */
    private void doSomething() throws Exception {
        try {
            System.out.println("gain lock: " + thisPath);
            Thread.sleep(2000);
            // do something
        } finally {
            System.out.println("finished: " + thisPath);
            //释放锁
            zk.delete(this.thisPath, -1);
        }
    }


}
