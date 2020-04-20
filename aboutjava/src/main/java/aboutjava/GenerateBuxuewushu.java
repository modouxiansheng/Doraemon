package aboutjava;

import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @program: Test
 * @description:
 * @author: hu_pf@suixingpay.com
 * @create: 2018-09-20 14:46
 **/
public class GenerateBuxuewushu {

    private static OkHttpClient okHttpClient;
    static {
        okHttpClient = new OkHttpClient().newBuilder().
                connectTimeout(2, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool())
                .build();
    }
    public static void main(String[] args) throws IOException {
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dataFormat.format(new Date());
        String source = "史上最便捷搭建RocketMQ服务器的方法.md";
        String title = source.substring(0,source.indexOf(".md"));
        String target = date+"-不学无数——"+title+"-2020.md";
        String bgPath = getBgPath();
        String add = "---\n" + "layout:     post                    # 使用的布局（不需要改）\n"
                + "title:      "+title+"        # 标题\n" + "subtitle:   "+title+"        #副标题\n"
                + "date:       "+date+"          # 时间\n" + "author:     不学无数                      # 作者\n"
                + "header-img: img/"+bgPath+"    #这篇文章标题背景图片\n" + "catalog: true                       # 是否归档\n"
                + "tags:                               #标签\n" + "    - JVM\n" +"    - Java\n"+ "---\n\n";

        readTo(source,target,add);
    }

    public static void readTo(String source,String target,String add) throws IOException {
        File file = new File("/Users/hupengfei/Documents/"+source);
        FileChannel in = new FileInputStream(file).getChannel();
        FileChannel out = new FileOutputStream("/Users/hupengfei/mygit/modouxiansheng/_posts/"+target).getChannel();
//        FileChannel out = new FileOutputStream("/Users/hupengfei/Downloads/"+target).getChannel();
        ByteBuffer wrap = ByteBuffer.wrap(add.getBytes());
        out.write(wrap);
//        ByteBuffer byteBuffer =ByteBuffer.allocate((int) file.length());
        ByteBuffer byteBuffer =ByteBuffer.allocate((int) file.length());
        StringBuffer stringBuffer = new StringBuffer();
        List<String> realMarkDownPic = new ArrayList<>();
        String fileName = source.replaceAll(".md","");
        while (in.read(byteBuffer)!=-1){
            //做好写的准备
            byteBuffer.flip();
            stringBuffer.append(ChangePic.byteBuffer2String(byteBuffer,StandardCharsets.UTF_8));
            //清除数据
            byteBuffer.clear();
        }
        List<String> picPathList = ChangePic.getPicPath(stringBuffer.toString());
        for (int i = 0; i < picPathList.size(); i++) {
            Request request = new Request.Builder().get().url(picPathList.get(i)).build();
            Call call = okHttpClient.newCall(request);
            ChangePic.onResponse(call.execute(),fileName+i);
            realMarkDownPic.add(fileName+i+".jpg");
            stringBuffer = new StringBuffer(stringBuffer.toString().replace(picPathList.get(i),"/img/pageImg/"+fileName+i+".jpg"));
        }
        out.write(ChangePic.string2ByteBuffer(stringBuffer.toString(),StandardCharsets.UTF_8));
    }

    public static String getBgPath(){
        String path = "/Users/hupengfei/mygit/modouxiansheng/img";
        File file = new File(path);
        File[] files = file.listFiles();
        List<File> fileList = Arrays.stream(files).filter(file1 -> file1.getName().contains("bg")).collect(Collectors.toList());
        Random random = new Random();
        int i = random.nextInt(fileList.size());
        return fileList.get(i).getName();
    }
}
