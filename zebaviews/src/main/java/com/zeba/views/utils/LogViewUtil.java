package com.zeba.views.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

public class LogViewUtil {

    public  static final boolean IsLogger=true;
    //个位为0表示关闭，非0表示开启
    public static final int Type_1=10;
    public static void e(String e){
        if(!IsLogger){
            return;
        }
        ep(e);
    }
    public static void e(int tag,String e){
        if(tag%10==0){
            return;
        }
        ep(e);
    }
    public static void ep(String e){
        e = e.trim();
        int index = 0;
        int maxLength = 3800;
        if(e.length()<=maxLength){
            Log.e("mylog ", e);
            return;
        }
        String sub;
        while(index<e.length()){
            if(index+maxLength>e.length()){
                sub=e.substring(index);
                Log.e("mylog ", sub);
                break;
            }else{
                sub=e.substring(index, index+maxLength);
                index+=maxLength;
                Log.e("mylog ", sub);
            }
        }
    }
    public static void logToFile(Context context,String content){
        File file=context.getExternalFilesDir("views_log");
        file=new File(file,"log.txt");
        LogViewUtil.LogToText(content,file,false);
    }
    public static void LogToText(String content, File file, boolean isDel) {
        FileAppendFileOutputStream(file.getAbsolutePath(), content + "\n");
    }
    /**
     * ׷���ļ���ʹ��FileOutputStream���ڹ���FileOutputStreamʱ���ѵڶ���������Ϊtrue
     *
     */
    public static void FileAppendFileOutputStream(String file, String conent) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write(conent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static String getCrashInfo(Throwable ex) {
        String info = "";
        ByteArrayOutputStream baos = null;
        PrintStream printStream = null;
        try {
            baos = new ByteArrayOutputStream();
            printStream = new PrintStream(baos);
            ex.printStackTrace(printStream);
            byte[] data = baos.toByteArray();
            info = new String(data);
            data = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (printStream != null) {
                    printStream.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return info;
    }
}
