package 控制类;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONObject;

public class Login {
	private String username;//学号
	private String password;//密码
	public Login(String username,String password) {
		this.username=username;
		this.password=password;
	}
	public CookieStore login() {
		
		String login_url="http://authserver-fosu-edu-cn-s.webvpn.fosu.edu.cn:8118/authserver/login?service=https%3A%2F%2Fwebvpn.fosu.edu.cn%2Fauth%2Fcas_validate%3Fentry_id%3D1%26redirect_uri%3Dhttp%253A%252F%252Fweb-fosu-edu-cn-s.webvpn.fosu.edu.cn%253A8118%252F";
		String html=null;
		JSONObject jsonObject = new JSONObject();
		//创建一个HttpContext对象，用来保存Cookie
	    HttpClientContext httpClientContext = HttpClientContext.create();
	    
	    //HttpClient访问方法
		CloseableHttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
		HttpGet httpGet = new HttpGet(login_url);
		CloseableHttpResponse response = null;
		try {
		    response = httpClient.execute(httpGet);
		    //获取返回结果中的实体
	    	HttpEntity entity = response.getEntity();
	    	html=EntityUtils.toString(entity);	    	
	    	EntityUtils.consume(entity);
		} catch (IOException e) {
		     e.printStackTrace();
		}
		//解析html，提取lt这个动态元素
		Document doc = Jsoup.parse(html);
		Elements lt_node = doc.select("[name*=lt]");
		String lt=(String)lt_node.attr("value");
				
		//模拟登录
	    HttpPost httpPost = new HttpPost(login_url);
	    httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
	    httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
	    httpPost.setHeader("Connection", "keep-alive");
	    httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
	    httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36");
	    
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("username",this.username));
        formparams.add(new BasicNameValuePair("password",this.password));
        formparams.add(new BasicNameValuePair("lt",lt));
        formparams.add(new BasicNameValuePair("dllt","userNamePasswordLogin"));
        formparams.add(new BasicNameValuePair("execution","e1s1"));
        formparams.add(new BasicNameValuePair("_eventId","submit"));
        formparams.add(new BasicNameValuePair("rmShown","1"));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
	    httpPost.setEntity(entity);
	    		
	    //执行请求，传入HttpContext，将会得到请求结果的信息
	    try {
	    	response=httpClient.execute(httpPost, httpClientContext);
	    	/*
	    	html=EntityUtils.toString(response.getEntity());
	    	System.out.println(html);*/
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    //从请求结果中获取Cookie，此时的Cookie已经带有登录信息了
	    CookieStore cookieStore = httpClientContext.getCookieStore();	    
	    return cookieStore;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
