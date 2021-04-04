package com.web_d4;

import com.web_d4.app.FoodForm;
import com.web_d4.core.HtmlResponse;
import com.web_d4.core.StaticFileReader;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name = "successOrderServlet", value = "/success")
public class SuccessOrderServlet extends HttpServlet {

    private final Map<String,String> views = new HashMap<>();


    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();

        // load views
        try {
            String html = StaticFileReader.readFile("success.html",context);
            views.put("success",html);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        HtmlResponse htmlResponse = new HtmlResponse(this.views.get("success"));
        out.println(htmlResponse.getHtml());
    }
}
