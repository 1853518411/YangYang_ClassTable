package 控制类;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import 控制类.Login;
import 课程表.Course_Table;
public class Download_table {
	private String url;//保存路径
	private CookieStore cookieStore;//登录cookie
	private String semester;//学期
	public Download_table(String id,String semester,CookieStore cookieStore) {
		String s[]=semester.split("_");
		this.semester=s[0]+"-"+s[1]+"-"+s[2];
		this.url="./"+id+"-"+this.semester+".xls";		
		this.cookieStore=cookieStore;
	}
	public String download() {
		
		String tablelink="http://100-fosu-edu-cn.webvpn.fosu.edu.cn:8118/xskb/xskb_list.do";
		String downlink="http://100-fosu-edu-cn.webvpn.fosu.edu.cn:8118/xskb/xskb_print.do";
		String html=null;
		//请求设置
		RequestConfig requestConfig = RequestConfig.custom().setCircularRedirectsAllowed(true).build();
		//创建一个HttpContext对象，用来保存Cookie
	    HttpClientContext httpClientContext = HttpClientContext.create();
	    
		//构造一个带这个Cookie的HttpClient(设置重定向，cookie和允许环形重定向)
	    CloseableHttpClient newHttpClient = HttpClientBuilder.create().setDefaultCookieStore(this.cookieStore).setRedirectStrategy(new LaxRedirectStrategy()).setDefaultRequestConfig(requestConfig).build();
	    CloseableHttpResponse response = null;
	    
	    HttpGet httpGet = new HttpGet(tablelink);
	    httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded");
	    httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
	    httpGet.setHeader("Connection", "keep-alive");
	    httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
	    httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36");
	    
	    try {
	    	response=newHttpClient.execute(httpGet);
	    	
	    	
	    	HttpPost httpPost = new HttpPost(downlink);	
	    	List<NameValuePair> formparams = new ArrayList<NameValuePair>();
	        formparams.add(new BasicNameValuePair("xnxq01id",this.semester));
	        formparams.add(new BasicNameValuePair("zc",""));
	        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
		    httpPost.setEntity(entity);
		    		    
		    httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		    httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		    httpPost.setHeader("Connection", "keep-alive");
		    httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
		    httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36");
		    response=newHttpClient.execute(httpPost);
		    
	    	File file = new File(this.url);
	    	//这里直接获取输入流,不要先获取实体,不然输入流会断开
	    	InputStream is = response.getEntity().getContent();
	    	FileOutputStream fileout = new FileOutputStream(file);
	    	byte[] buffer = new byte[512];
            int ch = 0;
            while ((ch = is.read(buffer)) != -1) {
                fileout.write(buffer, 0, ch);
            }
            is.close();
            fileout.close();
	    } catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    
	    return this.url;
	}
		
	
}
