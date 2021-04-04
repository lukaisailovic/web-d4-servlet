package com.web_d4;

import java.io.*;
import javax.servlet.ServletContext;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");


        String filename = "/MEALS/ponedeljak.txt";
        ServletContext context = getServletContext();

        // First get the file InputStream using ServletContext.getResourceAsStream()
        // method.
        InputStream is = context.getResourceAsStream(filename);
        if (is != null) {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            PrintWriter writer = response.getWriter();
            String text;
            // We read the file line by line and later will be displayed on the
            // browser page.
            while ((text = reader.readLine()) != null) {
                out.println("<h1>" + text + "</h1>");
            }
        }


        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}