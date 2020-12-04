package com.zeba.views.databind;

import android.app.Activity;

import com.zeba.views.SEditText;
import com.zeba.views.STextView;
import com.zeba.views.utils.LogViewUtil;

import org.zeba.obj.proxy.ProxyFunc;

import java.io.File;
import java.lang.reflect.Field;

public class DataBinder {
    private Object hostObj;
    private Object dataObj;
    private Object proxyObj;
    public static boolean isDebug=false;

    public DataBinder(final Object host, Object obj){
        this.dataObj =obj;
        this.hostObj =host;
        bind(new ProxyFunc<DataBinder>() {
            @Override
            public void onResult(DataBinder dataBinder) {
                try{
                    Field[] fields= hostObj.getClass().getDeclaredFields();
                    for(Field f:fields){
                        f.setAccessible(true);
                        if(f.getType()==dataObj.getClass()){
                            Object mo=f.get(hostObj);
                            if(mo==null){
                                continue;
                            }
                            if(f.get(hostObj)==dataObj){
                                f.set(hostObj,proxyObj);
                                break;
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if(hostObj instanceof Activity){
                        Activity activity=(Activity) hostObj;
                        LogViewUtil.logToFile(activity,LogViewUtil.getCrashInfo(e));
                    }
                }
            }
        });
    }

    public DataBinder(Object obj){
        this.dataObj =obj;
    }

    public DataBinder bind(SEditText eText,String name){
        eText.bindData(dataObj,name);
        return this;
    }

    public DataBinder bind(STextView eText, String name){
        eText.bindData(dataObj,name);
        return this;
    }

    public DataBinder bindViews(Object obj){
        Field[] fields= obj.getClass().getDeclaredFields();
        Field[] fields1=dataObj.getClass().getDeclaredFields();
        for(Field f:fields){
            try{
                if(f.getType()==STextView.class){
                    STextView stv=(STextView)f.get(obj);
                    String fn=stv.getFieldName();
                    if(fn!=null&&!"".equals(fn)){
                        bindData(stv,fn);
                    }else{
                        Field field=findField(fields1,f.getName());
                        if(field!=null){
                            bindData(stv,field.getName());
                        }
                    }
                }else if(f.getType()==SEditText.class){
                    SEditText stv=(SEditText)f.get(obj);
                    String fn=stv.getFieldName();
                    if(fn!=null&&!"".equals(fn)){
                        bindData(stv,fn);
                    }else{
                        Field field=findField(fields1,f.getName());
                        if(field!=null){
                            bindData(stv,field.getName());
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return this;
    }

    private Field findField(Field[] fields,String name){
        name=name.toLowerCase();
        for(Field field:fields){
            if(("tv"+field.getName()).toLowerCase().equals(name)){
                return field;
            }
        }
        return null;
    }

    public DataBinder bindData(SEditText et, String name){
        et.bindData(dataObj,name,proxyFunc);
        return this;
    }

    public DataBinder binds(SEditText et, String name){
        et.bindData(dataObj,name,proxyFunc);
        return this;
    }

    public DataBinder bindData(STextView eText, String name){
        eText.bindData(dataObj,name,proxyFunc);
        return this;
    }

    public DataBinder binds(STextView eText, String name){
        eText.bindData(dataObj,name,proxyFunc);
        return this;
    }

    public void bind(ProxyFunc<DataBinder> proxyFunc){
        proxyFuncResult=proxyFunc;
        onProxyDataResult();
    }

    private void onProxyDataResult(){
        if(proxyFuncResult!=null&&proxyObj!=null){
            proxyFuncResult.onResult(this);
            proxyFuncResult=null;
        }
    }

    public<T> T data(){
        return (T) dataObj;
    }

    public <T> T proxy(){
        return (T)proxyObj;
    }

    private ProxyFunc<DataBinder> proxyFuncResult;

    private ProxyFunc<Object> proxyFunc=new ProxyFunc<Object>() {
        @Override
        public void onResult(Object o) {
            proxyObj=o;
            onProxyDataResult();
        }
    };
}
