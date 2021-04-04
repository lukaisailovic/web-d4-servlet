package com.example.web_d4;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@WebServlet(name = "foodServlet", value = "/order")
public class FoodServlet extends HttpServlet {

    private final Map<String,String> views = new HashMap<>();

    public void init() {
        ServletContext context = getServletContext();
        try {
            String html = StaticFileReader.readFile("order.html",context);
            views.put("order",html);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println(this.views.get("order"));
    }
}
