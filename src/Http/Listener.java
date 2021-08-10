package Http;
public interface Listener{
    void onStart(String url,String path,String file,long size);
    void onRunning(String url,String path,String file,long size,long finishedsize,long speed);
    void onFinished(String url,String path,String file,long size);
}
