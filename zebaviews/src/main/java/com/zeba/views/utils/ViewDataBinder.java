package com.zeba.views.utils;

import android.widget.TextView;

import org.zeba.obj.proxy.MethodInvokeListener;
import org.zeba.obj.proxy.MethodProxy;
import org.zeba.obj.proxy.ObjProxy;
import org.zeba.obj.proxy.ProxyFunc;

import java.lang.reflect.Field;

public class ViewDataBinder {
    private Field dataField;
    private Object dataObj;
    private TextView textView;
    private Object oldValue;

    public ViewDataBinder(TextView view){
        textView=view;
    }

    public void refreshView(Object newValue){
        try{
            if(oldValue==null&&newValue==null){
                return;
            }
            if(!oldValue.equals(newValue)){
                oldValue=newValue;
                if(oldValue==null){
                    textView.setText("");
                }else{
                    textView.setText(oldValue.toString());
                }
            }else{
                System.out.println("oldValue.equals(v)");
                System.out.println("oldValue="+String.valueOf(oldValue)+",newValue="+String.valueOf(newValue));
            }
        }catch (Exception e){
           e.printStackTrace();
        }
    }

    public void setValueToObj(String value){
        if(dataField!=null&&dataObj!=null){
            try{
                String v=value;
                if(dataField.getType()==String.class){
                    dataField.set(dataObj,v);
                }else{
                    if(v.length()==0){
                        v="0";
                    }
                    if(dataField.getType()==Integer.class){
                        dataField.set(dataObj,Integer.parseInt(v));
                    }else if(dataField.getType()==Long.class){
                        dataField.set(dataObj,Long.parseLong(v));
                    }else if(dataField.getType()==Float.class){
                        dataField.set(dataObj,Float.parseFloat(v));
                    }else if(dataField.getType()==Double.class){
                        dataField.set(dataObj,Double.parseDouble(v));
                    }
                }
                oldValue=dataField.get(dataObj);
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
                if(mn.equals(n)||mn.equals("set"+n)){
                    if(objects!=null&&objects.length==1){
                        refreshView(objects[0]);
                    }
                }
            }
        };
        ObjProxy.addListener(textView, obj, invokeListener, new ProxyFunc<Object[]>() {
            @Override
            public void onResult(Object[] o) {
                bind(o[0],name);
                if(func!=null){
                    func.onResult(o[1]);
                }
                try{
                    refreshView(dataField.get(dataObj));
                }catch (Exception e){
                    e.printStackTrace();
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
}
