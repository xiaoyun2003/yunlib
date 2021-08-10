package Http;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import ExceptionCatch.ExceptionCatch;
public class Download
{
    ExceptionCatch ec;
    Listener lt;
    int max=5;
    boolean bp=true;
    int retry=3;
    int time=1000;
    int tasksNum=0;
    int runningTasksNum;
    public Download(){
        this.ec = new ExceptionCatch(){
            @Override
            public void setExceptionCatch(Exception e)
            {
                System.out.println("DownloadError:" + e.getLocalizedMessage());
            }
        }; 
       
        this.lt = new Listener(){

            @Override
            public void onStart(String url, String path, String file, long size)
            {
                System.out.println("开始下载:"+url+"\n"
                +"文件:"+path+file+"\n"
                +"文件大小:"+String.valueOf(size)
                );
            }

            @Override
            public void onRunning(String url, String path, String file, long size, long finishedsize, long speed)
            {
                System.out.println("正在下载.......:"+url+"\n"
                                   +"文件:"+path+file+"\n"
                                   +"文件大小:"+String.valueOf(size)+"\n"
                                   +"已完成大小:"+String.valueOf(finishedsize)+"\n"
                                   +"下载速度:"+String.valueOf(speed)
                                   );
            }

            @Override
            public void onFinished(String url, String path, String file, long size)
            {
                System.out.println("完成下载:"+url+"\n"
                                   +"文件:"+path+file+"\n"
                                   +"文件大小:"+String.valueOf(size)+"\n"
                                   +"总下载数:"+String.valueOf(getTasksNum())
                                   );
            }

           
        };
        
       
    }
    
 public void setListener(Listener lt,int time){
     this.lt=lt;
     this.time=time;
 }
 
 public String getFileByUrl(String url){
     String file="";
     try
     {
         URL u=new URL(url);
         file=u.getFile().replace("/","");
     }
     catch (MalformedURLException e)
     {
         ec.setExceptionCatch(e);
     }
     return file;
 }
 public int getTasksNum(){
     return this.tasksNum;
 }
 public boolean setSettings(int maxTask,boolean breakpoint,int retry){
   this.max=maxTask;
   this.bp=breakpoint;
   this.retry=retry;
   return true;
 }
 

 //creat a task for download,but you can set statue about,it will creat a threading
 public DownloadHandle startDownload(String url,String path,String file){
     if(path==null || path==""){
         path="./";
     }
     if(file==null || file==""){
         file=getFileByUrl(url);
     }
     if(path.charAt(path.length()-1)!='/'){
         path=path+"/";
     }
     
 DownloadHandle ad=new DownloadHandle(url,path,file);
 if(runningTasksNum<=this.max){
 ad.start();
 Timer timer = new Timer();
    
 timer.schedule(new onStart(ad),0,this.time);
 
 runningTasksNum++;
 tasksNum++;
 }else{
     try
     {
         ad.join();
         tasksNum++;
     }
     catch (InterruptedException e)
     {
         ec.setExceptionCatch(e);
     }
 }
 return ad;
 }

    public class onStart extends TimerTask
    {
        DownloadHandle ad;
        long lastsize;
        long speed;
      public  onStart(DownloadHandle ad){
           this.ad=ad; 
        }
        @Override
        public void run()
        {
            System.out.println(ad.isFinished());
            if(!ad.isFinished()){
                speed=(ad.getFinishedSize()-lastsize)/time;
                lastsize=ad.getFinishedSize();
        lt.onRunning(ad.getUrl(),ad.getPath(),ad.getFile(),ad.getSize(),ad.getFinishedSize(),speed);
        }else{
            super.cancel();
        }
        }
     
 }
 
 
 
    public class DownloadHandle extends Thread{
        String url, path,file;
        long size;
        long finishedsize;
        boolean isFinished=false;
        public DownloadHandle(String url,String path,String file){
            this.url=url;
            this.path=path;
            this.file=file;
        }
        
        public long getSize(){
            return size;
        }
        
        public String getUrl(){
            return this.url;
        }
        public long getFinishedSize(){
            return finishedsize;
        }
        
     
        
        public String getPath(){
            return this.path;
        }
        
        public String getFile(){
            return this.file;
        }
        
        public boolean isFinished(){
            return this.isFinished;
        }
        @Override
        public void run()
        {
            int si=0;
            byte[] buf = new byte[2048];
         //core code
            try
            {
                URL u=new URL(this.url);
                try
                {
                  HttpURLConnection c = (HttpURLConnection) u.openConnection();
                  size=c.getContentLengthLong();
                  lt.onStart(this.url,this.path,this.file,size);
                  BufferedInputStream s= new BufferedInputStream(c.getInputStream());
                  BufferedOutputStream fo= new BufferedOutputStream(new FileOutputStream(this.path+this.file));
                    while ((si = s.read(buf)) != -1){
                        fo.write(buf,0,si);     
                     finishedsize=new File(this.path+this.file).length();
                    }
                    fo.close();
                    s.close();
                    c.disconnect();
                    isFinished=true;
                    lt.onFinished(this.url,this.path,this.file,size);
                }
                catch (IOException e)
                {
                    isFinished=true;
                    ec.setExceptionCatch(e);
                } 
            }
            catch (MalformedURLException e)
            {
                isFinished=true;
                ec.setExceptionCatch(e);
            }
        }
    }
 
}




