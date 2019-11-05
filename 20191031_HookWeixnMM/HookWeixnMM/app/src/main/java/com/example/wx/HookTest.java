package com.example.wx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;

import de.robv.android.xposed.XC_MethodHook;

import de.robv.android.xposed.XposedBridge;

import de.robv.android.xposed.XposedHelpers;

import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookTest implements IXposedHookLoadPackage {
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        if (loadPackageParam.packageName.equals("com.tencent.mm")) {

            XposedBridge.log("微信Hook:找到微信的包");

            Class clazz = loadPackageParam.classLoader.loadClass(

                    "com.tencent.mm.sdk.platformtools.az");
            XposedBridge.log("微信Hook:找到微信的类!");

//            XposedHelpers.findAndHookMethod(clazz, "au", new XC_MethodHook() {
            XposedHelpers.findAndHookMethod("com.tencent.mm.sdk.platformtools.az", loadPackageParam.classLoader,"Yd",String.class,new XC_MethodHook() {
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                }

                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    XposedBridge.log("微信Hook:Hook开始");
                    HashMap<String, String> map1 = new HashMap<String, String>();
                    HashMap<String, String> map2 = new HashMap<String, String>();
                    HashMap<String, String> map3 = new HashMap<String, String>();
                    HashMap<String, String> map4 = new HashMap<String, String>();
                    HashMap<String, String> map5 = new HashMap<String, String>();
                    List<HashMap<String, String>> article_sets =new ArrayList<HashMap<String, String>>();
                    article_sets.add(map1);
                    article_sets.add(map2);
                    article_sets.add(map3);
                    article_sets.add(map4);
                    article_sets.add(map5);
                    Map<String,String> cond = (Map<String, String>) param.getResult();
                    for (String key : cond.keySet()) {
                        int article_idx = 0;
                        try{
                            String key_find = key.split("\\.")[5];
                            String article_idx_str = key_find.substring(key_find.length()-1,key_find.length());
                            article_idx = Integer.parseInt(article_idx_str);
                        }
                        catch (Exception e){
                        }
                        String article_type = "";
                        String article_value = "";
                        if (key.contains("item") && key.contains("title")){
                            article_type = "title";
                            article_value = cond.get(key);
                            XposedBridge.log(String.format("微信Hook:获得第%s文章标题%s",article_idx,cond.get(key)));
                        }
                        else if (key.contains("item") && key.contains("url")){
                            article_type = "url";
                            article_value = cond.get(key);
                            XposedBridge.log(String.format("微信Hook:获得第%s文章链接%s",article_idx,cond.get(key)));
                        } else if (key.contains("item") && key.contains("cover")){
                            article_type = "cover";
                            article_value = cond.get(key);
                            XposedBridge.log(String.format("微信Hook:获得第%s文章封面图%s",article_idx,cond.get(key)));
                        }
                        article_sets.get(article_idx).put(article_type, article_value);
                    }
                    XposedBridge.log("微信Hook:Hook结束");

                }

            });

        }

    }

}