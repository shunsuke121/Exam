package tool;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
// アノテーション(URLを指定)のAPI
//import javax.servlet.annotation.WebServlet;
// サーブレット(サーバーで実行した結果を返す)を定義したクラス
//import javax.servlet.http.HttpServlet;
// ユーザ(クライアント)からサーバへのリクエスト
//import javax.servlet.http.HttpServletRequest;
// サーバからユーザ(クライアント)へのレスポンス
//import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(urlPatterns={"/*"})
public class EncodingFilter implements Filter{
	public void doFilter(
			ServletRequest request,ServletResponse response,FilterChain chain
	)throws IOException,ServletException{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		System.out.println("フィルタの前処理");
		chain.doFilter(request, response);
		System.out.println("フィルタの後処理");
	}
	public void init(FilterConfig filterConfig){}
	public void destroy(){}
	
}
