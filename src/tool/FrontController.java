package tool;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "*.action" })
public class FrontController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            // リクエストからクラス名へ変換
            String path = req.getServletPath().substring(1);  // 例: "scoremanager/Login.action"
            String name = path.replace(".action", "Action").replace("/", ".");

            System.out.println("★ servlet path → " + req.getServletPath());
            System.out.println("★ class name   → " + name);

            // Actionクラスの生成と実行
            Action action = (Action) Class.forName(name).getDeclaredConstructor().newInstance();
            String view = action.execute(req, res);  // 画面遷移先のJSP名 or redirect命令

            if (view != null) {
                if (view.startsWith("redirect:")) {
                    // リダイレクト処理
                    String location = view.substring("redirect:".length());
                    res.sendRedirect(req.getContextPath() + "/" + location);
                } else {
                    // フォワード処理
                    RequestDispatcher dispatcher = req.getRequestDispatcher("/" + view);
                    dispatcher.forward(req, res);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res); // POSTも同様に処理
    }
}