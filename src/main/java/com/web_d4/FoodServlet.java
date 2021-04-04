package com.web_d4;

import com.web_d4.app.*;
import com.web_d4.app.view.FoodForm;
import com.web_d4.core.HtmlResponse;
import com.web_d4.core.StaticFileReader;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@WebServlet(name = "foodServlet", value = "/order")
public class FoodServlet extends HttpServlet {

    private static final String PATH_PREFIX = "/web-d4/";
    private final Map<String,String> views = new HashMap<>();
    private final Map<DaysOfWeek, List<String>> mealsPerDay = new HashMap<>();


    public void init() {
        ServletContext context = getServletContext();

        // load views
        try {
            String html = StaticFileReader.readFile("order.html",context);
            views.put("order",html);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // load meals
        try {
            this.loadMeals(context);
        } catch (IOException e) {
            e.printStackTrace();
        }

        OrderList orderList = new OrderList();
        orderList.save(context);


    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getSession().getId();
        User user = new User(id);
        OrderList orderList = OrderList.get(getServletContext());
        if (orderList.hasOrders(user)){
            resp.sendRedirect(PATH_PREFIX+"success");
        } else {
            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();
            HtmlResponse htmlResponse = new HtmlResponse(this.views.get("order"));
            FoodForm foodForm = new FoodForm(this.mealsPerDay);
            htmlResponse.addParameter("form",foodForm.build());

            out.println(htmlResponse.getHtml());
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderList orderList = OrderList.get(getServletContext());
        List<Order> orders = new ArrayList<>();
        User user = new User(req.getSession().getId());
        for (DaysOfWeek day: DaysOfWeek.values()){
            String meal = req.getParameter(day.toString().toLowerCase());
            if (meal.equals("")){
                resp.sendRedirect(PATH_PREFIX+"error");
            }
            orders.add(new Order(user,day,meal));
            System.out.println("USER "+req.getSession().getId()+" FOR DAY " + day.toString() + " CHOSE "+ meal );
        }
        orderList.addOrders(orders);
        orderList.save(getServletContext());
        resp.sendRedirect(PATH_PREFIX+"success");
    }

    private void loadMeals(ServletContext context) throws IOException {
        for (DaysOfWeek day: DaysOfWeek.values()){
            String path = "/MEALS/"+day.toString().toLowerCase()+".txt";
            InputStream is = context.getResourceAsStream(path);
            if (is != null) {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(isr);
                String text;
                // We read the file line by line and later will be displayed on the
                // browser page.
                while ((text = reader.readLine()) != null) {
                    if (!mealsPerDay.containsKey(day)){
                        ArrayList<String> meals = new ArrayList<>();
                        meals.add(text);
                        mealsPerDay.put(day,meals);
                    } else {
                        mealsPerDay.get(day).add(text);
                    }
                    System.out.println("Added "+text+" on "+day.toString());
                }
            }

        }
    }
}
