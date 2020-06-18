package 课程表;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.http.client.CookieStore;
import org.junit.jupiter.api.Test;

class Login_systemTest {

	@Test
	void testLogin() {		
		
		//System.out.println(Login_system.login());
	}

	@Test
	void testDownload() {
		String username="";//学号
		String password="";//密码
		CookieStore cookieStore=Login_system.login(username,password);
		System.out.println(cookieStore);
		System.out.println(Login_system.download(cookieStore));
	}

}
