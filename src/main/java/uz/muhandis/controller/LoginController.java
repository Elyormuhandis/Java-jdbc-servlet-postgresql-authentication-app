package uz.muhandis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.muhandis.model.Result;
import uz.muhandis.model.User;
import uz.muhandis.service.UserService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/login") //xml configuratsiyani annotatsiya bilan yozish mumkin
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("login.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserService();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Result result = null;
        try {
            result = userService.login(new User(username, password));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        PrintWriter printWriter = resp.getWriter();
        if (result.isSuccess()){
            printWriter.write("<h1 style='color:green'>"+result.getMessage()+"</h1>");
            printWriter.write("<h1 style='color:green'>"+"Your id is "+result.getUser().getId()+"</h1>");
            printWriter.write("<h1 style='color:green'>"+"Your first name is "+result.getUser().getFirstName()+"</h1>");
            printWriter.write("<h1 style='color:green'>"+"Your last name is "+result.getUser().getLastName()+"</h1>");
            printWriter.write("<h1 style='color:green'>"+"Your login is "+result.getUser().getUsername()+"</h1>");
            printWriter.write("<h1 style='color:green'>"+"Your phone is "+result.getUser().getPhoneNumber()+"</h1>");
        } else{
            printWriter.write("<h1 style='color:red'>"+result.getMessage()+"</h1>");
        }

    }
}
