package com.example.testrabbitmq.ztest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.AppUtils;
import com.example.testrabbitmq.ztest.utils.VersionUpdateClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        com.xunlei.downloadprovider/.launch.LaunchActivity
        new Thread(new Runnable() {
            @Override
            public void run() {
                VersionUpdateClient.startVersionUpdateMonitor();
            }
        }).start();

        TextView textView=(TextView)findViewById(R.id.start);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断包名是否存在
                if(!AppUtils.isInstallApp("cn.xt800.support")){
                    copyFilesFromAssets(MainActivity.this, Environment.getExternalStorageDirectory().getAbsolutePath()+"/xt800/xt.apk");
                }
//                if(isAvilible("cn.xt800.support")){
////                  下面代码是调起迅雷.
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_LAUNCHER);
//                ComponentName cn = new ComponentName("com.xunlei.downloadprovider", "com.xunlei.downloadprovider.launch.LaunchActivity");
//                intent.setComponent(cn);
//                startActivity(intent);
//                }
            }
        });

    }
    public boolean isAvilible(String packageName) {
        PackageManager packageManager = getPackageManager();
        //获取手机系统的所有APP包名，然后进行一一比较
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (pinfo.get(i).packageName
                    .equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }
    public  void copyFilesFromAssets(Context context, String savePath){
        try {
                InputStream is = context.getAssets().open("xt.apk");
                FileOutputStream fos = new FileOutputStream(new File(savePath));
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
                    // buffer字节
                    fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
                }
                fos.flush();// 刷新缓冲区
                is.close();
                fos.close();
                Toast.makeText(this, "复制完成!!", Toast.LENGTH_SHORT).show();
                File file=new File(savePath);
                if(file.exists()){
                    AppUtils.installApp(file,"");//静默安装
                }
        } catch (Exception e) {
            Toast.makeText(this, "复制异常!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
