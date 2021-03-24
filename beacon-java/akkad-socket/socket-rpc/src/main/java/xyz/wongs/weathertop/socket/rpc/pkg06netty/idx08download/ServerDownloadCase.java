package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx08download;

import xyz.wongs.weathertop.socket.rpc.pkg06netty.util.HttpCallerUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WCNGS@QQ.COM
 * @ClassName NettyClient$
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/9$ 11:12$
 * @Version 1.0.0
 */
public class ServerDownloadCase {

    public static void main(String[] args) throws Exception{
        Map<String, String> params = new HashMap<String, String>();
        byte[] ret = HttpCallerUtils.getStream("http://192.168.60.82:9099/", params);

        //byte[] ret = HttpProxy.get("http://192.168.1.111:8765/images/006.jpg");
        //写出文件
        String writePath = System.getProperty("user.dir")+ File.separatorChar + "Doc"  + File.separatorChar + "download" +  File.separatorChar + "a.doc";
        FileOutputStream fos = new FileOutputStream(writePath);
        fos.write(ret);
        fos.close();
    }
}
