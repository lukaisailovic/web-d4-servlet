package com.web_d4;



import com.web_d4.app.DaysOfWeek;
import com.web_d4.app.MealsLoader;
import com.web_d4.app.OrderList;
import com.web_d4.app.view.CurrentOrdersHtml;
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

import java.util.List;
import java.util.Map;

@WebServlet(name = "dashboardServlet", value = "/odabrana-jela")
public class DashboardServlet extends HttpServlet {


    private final Map<String,String> views = new HashMap<>();
    private static final String PATH_PREFIX = "/web-d4/";
    private String password;
    private Map<DaysOfWeek, List<String>> mealsPerDay = new HashMap<>();


    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();

        // load views
        try {
            String html = StaticFileReader.readFile("dashboard.html",context);
            views.put("dashboard",html);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // get password
        try {
            this.password = StaticFileReader.readFile("/secret/password.txt",context);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // load meals
        try {
            this.mealsPerDay = MealsLoader.loadAll(context);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (this.password.equals(req.getParameter("password"))){
            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();
            HtmlResponse htmlResponse = new HtmlResponse(this.views.get("dashboard"));
            OrderList orderList = OrderList.get(getServletContext());
            if (orderList == null){
                orderList = new OrderList();
                orderList.save(getServletContext());
            }
            CurrentOrdersHtml currentOrdersHtml = new CurrentOrdersHtml(orderList,this.mealsPerDay);
            htmlResponse.addParameter("currentOrders",currentOrdersHtml.build());
            out.println(htmlResponse.getHtml());
        } else {
            resp.setStatus(403);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderList orderList = new OrderList();
        orderList.save(getServletContext());
        resp.sendRedirect(PATH_PREFIX+"odabrana-jela?password="+this.password);
    }
}
