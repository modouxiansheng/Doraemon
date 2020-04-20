package aboutjava;

import okhttp3.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @program: Test
 * @description:
 * @author: hu_pf@suixingpay.com
 * @create: 2019-05-05 11:13
 **/
public class ChangePic {

    private static final String FILE_PATH = "/Users/hupengfei/mygit/modouxiansheng/_posts";
    private static final String MD_SUFFIX = "md";
    private static final String FILE_SEG = "/";
    private static final String EMPTY = "";
    private static final String PIC_PRE = "/img/pageImg/";
    private static List<File> FILE_LIST;
    private static Map<String,List<String>> MARKDOWN_MAP_PATH = new HashMap<>();
    private static OkHttpClient okHttpClient;
    static {
        okHttpClient = new OkHttpClient().newBuilder().
                connectTimeout(2, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool())
                .build();
        FILE_LIST = getFilePath(FILE_PATH);
    }
    public static void main(String[] args) throws IOException {
        getPic();
        readTo();
    }

    public static void getPic() throws IOException {
        for (File file : FILE_LIST){
            List<String> realMarkDownPic = new ArrayList<>();
            String fileName = file.getName().replaceAll(".md",EMPTY);
            StringBuffer myPage = new StringBuffer();
            FileChannel in = new FileInputStream(file).getChannel();
            ByteBuffer byteBuffer =ByteBuffer.allocate(1024);
            while (in.read(byteBuffer)!=-1){
                //做好写的准备
                byteBuffer.flip();
                myPage = myPage.append(byteBuffer2String(byteBuffer,StandardCharsets.UTF_8));
                //清除数据
                byteBuffer.clear();
            }
            List<String> markDownPic = getPicPath(myPage.toString());
            for (int i = 0; i < markDownPic.size(); i++) {
                Request request = new Request.Builder().get().url(markDownPic.get(i)).build();
                Call call = okHttpClient.newCall(request);
                onResponse(call.execute(),fileName+i);
                realMarkDownPic.add(fileName+i+".jpg");
            }
            MARKDOWN_MAP_PATH.put(fileName,realMarkDownPic);
        }
    }

    public static void readTo() throws IOException {
        for (File file : FILE_LIST){
            String fileName = file.getName().replaceAll(".md",EMPTY);
            //讀取文件
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream,"utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = "";
            StringBuilder sb = new StringBuilder();
            while((str=bufferedReader.readLine())!=null){
                sb.append(str);
                sb.append(System.getProperty("line.separator"));
            }
            List<String> markDownPic = getPicPath(sb.toString());
            for (int i = 0; i < markDownPic.size(); i++) {
                List<String> list = MARKDOWN_MAP_PATH.get(fileName);
                sb = new StringBuilder(sb.toString().replace(markDownPic.get(i),PIC_PRE+list.get(i).substring(list.get(i).indexOf("——")+2,list.get(i).lastIndexOf("-"))+i+".jpg"));
            }
            if (markDownPic.size()>0){
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                bufferedWriter.write(sb.toString());
                //注意flush
                bufferedWriter.flush();
            }
        }
    }

    public static String byteBuffer2String(ByteBuffer buf, Charset charset) {
        byte[] bytes;
        if (buf.hasArray()) {
            bytes =  Arrays.copyOfRange(buf.array(),0,buf.limit());
        } else {
            buf.rewind();
            bytes = new byte[buf.remaining()];
        }
        return new String(bytes, charset);
    }

    public static ByteBuffer string2ByteBuffer(String s, Charset charset) {
        return ByteBuffer.wrap(s.getBytes(charset));
    }

    public static List<File> getFilePath(String path){
        File file = new File(path);
        File[] files = file.listFiles();
        List<File> fileList = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()){
                fileList.addAll(getFilePath(path+FILE_SEG+files[i].getName()));
            }else {
                if (files[i].getName().contains(MD_SUFFIX)){
                    fileList.add(files[i]);
                }
            }
        }
        return fileList;
    }

    public static List<String> getPicPath(String fileData){
        String reg = "!\\[.*?\\]\\(.*?\\).*";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(fileData);
        List<String> markDownPic = new ArrayList<>();
        while (matcher.find()){
            String picPath = matcher.group(0);
            markDownPic.add(picPath.substring(picPath.indexOf("(")+1).replaceAll("\\)",EMPTY));
        }
        return markDownPic;
    }

    public static void onResponse(Response response,String fileName) throws IOException {
        //将响应数据转化为输入流数据
        InputStream inputStream=response.body().byteStream();
        //将输入流数据转化为Bitmap位图数据
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while ((len = inputStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();

        //new一个文件对象用来保存图片，默认保存当前工程根目录
        File imageFile = new File("/Users/hupengfei/mygit/modouxiansheng/img/pageImg/"+fileName.replaceAll("不学无数——",EMPTY)+".jpg");
        //创建输出流
        FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
        //写入数据
        fileOutputStream.write(data);
        //关闭输出流
        fileOutputStream.close();
        //关闭输入流
        inputStream.close();
    }

}
