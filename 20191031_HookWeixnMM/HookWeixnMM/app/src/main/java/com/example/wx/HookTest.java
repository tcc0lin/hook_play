package com.example.wx;

import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;

import de.robv.android.xposed.XC_MethodHook;

import de.robv.android.xposed.XposedBridge;

import de.robv.android.xposed.XposedHelpers;

import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookTest implements IXposedHookLoadPackage {

    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        if (loadPackageParam.packageName.equals("com.tencent.mm")) {

            XposedBridge.log("微信Hook:找到微信的包!");

            Class clazz = loadPackageParam.classLoader.loadClass(

                    "com.tencent.mm.sdk.platformtools.ay");
            XposedBridge.log("微信Hook:找到微信的类!");

//            XposedHelpers.findAndHookMethod(clazz, "au", new XC_MethodHook() {
            XposedHelpers.findAndHookMethod("com.tencent.mm.sdk.platformtools.ay", loadPackageParam.classLoader,"WA",String.class,new XC_MethodHook() {
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                }

                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    XposedBridge.log("微信Hook:Hook开始");
                    Map<String,String> cond = (Map<String, String>) param.getResult();
                    for (String key : cond.keySet()) {
                        if (key.endsWith("cover")){
                            XposedBridge.log(String.format("微信Hook：获取文章链接：%s",cond.get(key)));
                        }
                    }
                    XposedBridge.log("微信Hook:Hook结束");

                }

            });

        }

    }

}