package com.example.wx;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;

import de.robv.android.xposed.XC_MethodHook;

import de.robv.android.xposed.XposedBridge;

import de.robv.android.xposed.XposedHelpers;

import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Hookinsert implements IXposedHookLoadPackage {
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        if (loadPackageParam.packageName.equals("com.tencent.mm")) {

            XposedBridge.log("微信插入Hook：:找到微信的包");

            Class clazz = loadPackageParam.classLoader.loadClass(

                    "com.tencent.wcdb.database.SQLiteDatabase");
            XposedBridge.log("微信插入Hook：:找到微信的类!");

//            XposedHelpers.findAndHookMethod(clazz, "au", new XC_MethodHook() {
            XposedHelpers.findAndHookMethod("com.tencent.wcdb.database.SQLiteDatabase", loadPackageParam.classLoader,"insertWithOnConflict",String.class,String.class, ContentValues.class,int.class,new XC_MethodHook() {
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                }

                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    XposedBridge.log("微信插入Hook：:Hook开始");
                    Object tableName = param.args[0];
                    Object obj1 = param.args[1];
                    ContentValues cv = (ContentValues)param.args[2];
                    XposedBridge.log(String.format("微信插入Hook： tablename:%s,%s",tableName,obj1));
                    for(String key:cv.keySet()){
                        XposedBridge.log(String.format("微信插入Hook： key=:%s,value=%s",key,cv.get(key)));
                    }
                    XposedBridge.log("微信插入Hook：:Hook结束");

                }

            });

        }

    }

}