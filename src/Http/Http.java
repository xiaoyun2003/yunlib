package Http;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import ExceptionCatch.ExceptionCatch;
public class Http {
    ExceptionCatch ec;
    HttpURLConnection c;
    public Http()
    {
           ec = new ExceptionCatch(){
            @Override
            public void setExceptionCatch(Exception e)
            {
                System.out.println("HttpError:" + e.getLocalizedMessage());
            }
        }; 
    }
    
	public void setExceptionCatch(ExceptionCatch ec) 
    {
        this.ec = ec;
    }
    
    public String getHostNameByUrl(String url)
    {
        try
        {
            return new URL(url).getHost();
        }
        catch (MalformedURLException e)
        {
            ec.setExceptionCatch(e);
            return null;
        } 
    }
    
    public String get(String url, String headers, String cookies,String encode)
    {
        //create a URL
        try
        {
            URL u=new URL(url);
            try
            {
                BufferedReader bin;
                StringBuilder res = new StringBuilder();
                c = (HttpURLConnection)u.openConnection();
                c.setRequestMethod("GET");
                if (headers == null || headers=="")
                {
                    headers = HttpUtils.getHeadersByUrl(url);
                }
                String dcookies="";
                if (cookies != null)
                {
                 dcookies = "\nCookie:" + cookies;
                }
                if(encode==null || encode==""){
                    encode="utf-8";
                }

                try
                {
                    for (String header:(headers + dcookies).split("\\r?\\n"))
                    {
                        String[] r=header.split(":", 2);
                        c.setRequestProperty(r[0], r[1]);
                    }
                }
                catch (ArrayIndexOutOfBoundsException e)
                {
                    ec.setExceptionCatch(e);
                }
                c.connect();
                bin = new BufferedReader(new InputStreamReader(c.getInputStream(),encode));
       
                String line;
            
                while ((line = bin.readLine()) != null)
                {  
                    res.append(line);
                }  
           
                bin.close();
                c.disconnect();
                return res.toString();
            }
            catch (IOException e)
            {
                ec.setExceptionCatch(e);
                return null;
            }
        }
        catch (MalformedURLException e)
        {
            ec.setExceptionCatch(e);
            return null;
        }
    }
    
    public String post(String url, String data, String headers, String cookies,String encode)
    {
        //create a URL
        try
        {
            URL u=new URL(url);
            try
            {
                BufferedReader bin;
                StringBuilder res = new StringBuilder();
                c = (HttpURLConnection)u.openConnection();
                c.setRequestMethod("POST");
                if (headers == null || headers=="")
                {
                    headers = HttpUtils.getHeadersByUrl(url);
                }

                String dcookies="";
                if (cookies != null)
                {
                    dcookies = "\nCookie:" + cookies;
                }
                if(encode==null || encode==""){
                    encode="utf-8";
                }

                try
                {
                    for (String header:(headers + dcookies).split("\\r?\\n"))
                    {
                        String[] r=header.split(":", 2);
                        c.setRequestProperty(r[0], r[1]);
                    }
                }
                catch (ArrayIndexOutOfBoundsException e)
                {
                    ec.setExceptionCatch(e);
                }
                c.setDoOutput(true);
                c.setDoInput(true);
                if (data != null)
                {
                    BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(c.getOutputStream(),encode));
                    bw.write(data);
                    bw.close();
                }
                c.connect();
                bin = new BufferedReader(new InputStreamReader(c.getInputStream(),encode));
       
                String line;
           
                while ((line = bin.readLine()) != null)
                {  
                    res.append(line);
                }  
           
                bin.close();
                c.disconnect();
                return res.toString();
            }
            catch (IOException e)
            {
                ec.setExceptionCatch(e);
                return null;
            }
        }
        catch (MalformedURLException e)
        {
            ec.setExceptionCatch(e);
            return null;
        }
    }
    
    public String getResHeader(String key)
    {
        return c.getHeaderField(key);
    }

}
