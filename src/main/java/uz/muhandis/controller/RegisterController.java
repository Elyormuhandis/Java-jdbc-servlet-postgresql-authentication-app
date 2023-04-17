package uz.muhandis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.muhandis.model.Result;
import uz.muhandis.model.User;
import uz.muhandis.service.UserService;

import java.io.IOException;
import java.io.PrintWriter;

public class RegisterController extends HttpServlet {
    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("register.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String p1 = req.getParameter("password");
        String p2 = req.getParameter("prePassword");
        PrintWriter printWriter = resp.getWriter();
        if (!p1.equals(p2)) {
            printWriter.write("<h1>Parollar mos emas</h1>");
            return;
        }
        User user = new User(req.getParameter("firstName"), req.getParameter("lastName"),
                req.getParameter("username"), req.getParameter("phoneNumber"), req.getParameter("password"));
        Result result = null;
        try {
            result = userService.registerUser(user);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        printWriter.write("<h1>"+result.getMessage()+"</h1>");
        return;

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
