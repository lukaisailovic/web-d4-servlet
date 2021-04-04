package com.web_d4;

import com.web_d4.app.*;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet(name = "successOrderServlet", value = "/success")
public class SuccessOrderServlet extends HttpServlet {

    private final Map<String,String> views = new HashMap<>();
    private static final String PATH_PREFIX = "/web-d4/";

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
        User user = new User(req.getSession().getId());
        OrderList orderList = OrderList.get(getServletContext());
        List<Order> userOrders = new ArrayList<>();
        if (orderList != null){
            userOrders = orderList.getUserOrders(user);
        }
        if (userOrders.size() > 0){
            UserOrderHtml userOrderHtml = new UserOrderHtml(userOrders);
            htmlResponse.addParameter("userOrder",userOrderHtml.build());
            out.println(htmlResponse.getHtml());
        } else {
            resp.sendRedirect(PATH_PREFIX+"order");
        }

    }
}
