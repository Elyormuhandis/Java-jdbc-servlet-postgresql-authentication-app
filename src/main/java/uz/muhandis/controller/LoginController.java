package uz.muhandis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
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
// With cookies
//            Cookie cookie = new Cookie("authUser", result.getUser().getUsername());
//            cookie.setMaxAge(60*60);
//            resp.addCookie(cookie);
            // With session
            HttpSession session = req.getSession();
            session.setAttribute("authUser", result.getUser().getUsername());
            session.setMaxInactiveInterval(10); //10 sekunddan keyin sessiya o'chib ketadi va yana qayta login qilish talab etiladi
            resp.sendRedirect("/cabinet");
        } else{
            printWriter.write("<h1 style='color:red'>"+result.getMessage()+"</h1>");
        }

    }
}
