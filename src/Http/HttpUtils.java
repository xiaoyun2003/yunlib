package Http;
public class HttpUtils
{
    public static String getUA(String type)
    {
        String ua;
        switch (type)
        {
            case "windows":
                ua = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36";
                break;
            case "mac":
                ua = "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.133 Safari/534.16";
                break;
            case "android":
                ua = "Mozilla/5.0 (Linux; U; Android 7.1.2; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
                break;
            case "iphone":
                ua = "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5";
                break;
            case "ipad":
                ua = "Mozilla/5.0 (iPad; U; CPU OS 4_2_1 like Mac OS X; zh-cn) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8C148 Safari/6533.18.5";
                break;
            case "qq":
                ua = "Mozilla/5.0 (Linux; Android 7.1.2; vivo X9Plus L Build/N2G47H; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/77.0.3865.120 MQQBrowser/6.2 TBS/045710 Mobile Safari/537.36 V1_AND_SQ_8.5.5_1630_YYB_D N_8050531 QQ/8.5.5.5105 NetType/WIFI WebP/0.3.0 Pixel/1080 StatusBarHeight/73 SimpleUISwitch/0 QQTheme/1000 InMagicWin/0";
                break;
            case "weixin":
                ua = "Mozilla/5.0 (Linux; Android 7.1.2; en-us; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/77.0.3865.120 MQQBrowser/6.2 TBS/045617 Mobile Safari/537.36 MMWEBID/9768 MicroMessenger/8.0.6.1900(0x28000653) Process/tools WeChat/arm64 Weixin NetType/WIFI Language/zh_CN ABI/arm64";
                break;
            default:
                ua = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E)"; 
        }
        return ua;
    }
    
    public static String getHeadersByUrl(String url)
    {
        String  Host=new Http().getHostNameByUrl(url);   
        String headers="X-Requested-With:XMLHttpRequest\nAccept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\nAccept-Language:zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7\nConnection:keep-alive\n" +
            "Host:" + Host + "\n" +
            "User-Agent:" + getUA("android") +
            "Referer:" + url;
        return headers;
    }
}
