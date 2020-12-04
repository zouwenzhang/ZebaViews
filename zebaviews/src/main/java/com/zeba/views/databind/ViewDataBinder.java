package com.zeba.views.databind;

import android.content.Context;
import android.widget.TextView;

import com.zeba.views.utils.LogViewUtil;

import org.zeba.obj.proxy.MethodInvokeListener;
import org.zeba.obj.proxy.MethodProxy;
import org.zeba.obj.proxy.ObjProxy;
import org.zeba.obj.proxy.ProxyFunc;

import java.lang.reflect.Field;

public class ViewDataBinder {
    private Field dataField;
    private Object dataObj;
    private Object proxyObj;
    private Field proxyField;
    private TextView textView;
    private Object oldValue;

    public ViewDataBinder(TextView view){
        textView=view;
    }

    public void refreshView(Object newValue){
        try{
            if(oldValue==null){
                oldValue="";
            }
            if(newValue==null){
                newValue="";
            }
            if(!oldValue.equals(newValue)){
                oldValue=newValue;
                if(oldValue==null){
                    textView.setText("");
                }else{
                    textView.setText(oldValue.toString());
                }
            }
        }catch (Exception e){
           e.printStackTrace();
        }
    }

    public void setValueToObj(String value){
        setValueToObj(dataField,dataObj,value);
        setValueToObj(proxyField,proxyObj,value);
        oldValue=value;
    }

    private void setValueToObj(Field field,Object obj,String value){
        if(field!=null&&obj!=null){
            try{
                String v=value;
                if(field.getType()==String.class){
                    field.set(obj,v);
                }else{
                    if(v.length()==0){
                        v="0";
                    }
                    if(field.getType()==Integer.class){
                        field.set(obj,Integer.parseInt(v));
                    }else if(field.getType()==Long.class){
                        field.set(obj,Long.parseLong(v));
                    }else if(field.getType()==Float.class){
                        field.set(obj,Float.parseFloat(v));
                    }else if(field.getType()==Double.class){
                        field.set(obj,Double.parseDouble(v));
                    }
                }
                oldValue=field.get(obj);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public boolean bind(Object obj,String name){
        try {
            dataField=obj.getClass().getDeclaredField(name);
            dataField.setAccessible(true);
            oldValue=dataField.get(obj);
            if(oldValue==null){
                oldValue="";
                textView.setText("");
            }else{
                textView.setText(oldValue.toString());
            }
            dataObj=obj;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void bindData(Object obj,final String name,final ProxyFunc func){
        invokeListener=new MethodInvokeListener() {
            @Override
            public void onInvoke(Object o, Object[] objects, MethodProxy methodProxy) {
                String mn=methodProxy.getMethodName().toLowerCase();
                String n=name.toLowerCase();
                if(DataBinder.isDebug){
                    System.out.println("value change "+name);
                }
                if(mn.equals(n)||mn.equals("set"+n)){
                    if(objects!=null&&objects.length==1){
                        if(DataBinder.isDebug){
                            System.out.println("value refresh "+name+objects[0]);
                        }
                        refreshView(objects[0]);
                    }
                }
            }
        };
        ObjProxy.addListener(obj, invokeListener, new ProxyFunc<Object[]>() {
            @Override
            public void onResult(Object[] o) {
                dataObj=o[0];
                proxyObj=o[1];
                bind(dataObj,name);
                try{
                    proxyField=proxyObj.getClass().getSuperclass().getDeclaredField(name);
                    proxyField.setAccessible(true);
                    if(DataBinder.isDebug){
                        System.out.println("bind "+name+" success");
                    }
                }catch (Exception e2){
                    e2.printStackTrace();
                }
                if(func!=null){
                    func.onResult(o[1]);
                }
            }
        });
    }

    public void onDestroy(){
        if(invokeListener!=null){
            ObjProxy.removeListener(dataObj,invokeListener);
            invokeListener=null;
        }
    }

    private MethodInvokeListener invokeListener=null;

    public static void printlnObj(Context context,Object obj){
        Field[] fs= obj.getClass().getDeclaredFields();
        try{
            System.out.println("classname="+obj.getClass().getName());
            LogViewUtil.logToFile(context,"classname="+obj.getClass().getName());
            for(Field f:fs){
                f.setAccessible(true);
                Object v=f.get(obj);
                System.out.println("name="+f.getName()+",value="+v);
                LogViewUtil.logToFile(context,"name="+f.getName()+",value="+v);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
