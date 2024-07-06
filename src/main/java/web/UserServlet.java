package web;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/")
public class UserServlet extends HttpServlet {
    private UserDao userDao;

    public void init() {
        userDao = new UserDao();
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getServletPath();

        switch (action){
            case "/new":
                showNewForm(request, response);
                break;
            case "/insert":
                insertUser(request, response);
                break;

            case "/delete":
                deleteUser(request, response);
                break;

            case "/edit":
                showEditForm(request, response);
                break;

            case "/update":
                updateUser(request, response);
                break;

            default:
                listUser(request, response);
                break;
        }
    }


    private void listUser(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
            List<User> listUser = userDao.selectAllUsers();

            request.setAttribute("listUser", listUser);
            request.getRequestDispatcher("user-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
            request.getRequestDispatcher("user-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
            int id = Integer.parseInt(request.getParameter("id"));
            User existingUser = userDao.selectUserById(id);
            request.setAttribute("user", existingUser);
            RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
            dispatcher.forward(request, response);
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
        throws IOException{
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String country = request.getParameter("country");

            User user = new User(name, email, country);
            userDao.saveUser(user);
            response.sendRedirect("list");
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
        throws IOException{
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String country = request.getParameter("country");
            User user = new User(id, name, email, country);
            userDao.updateUserById(user);
            response.sendRedirect("list");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
        throws IOException{
            int id = Integer.parseInt(request.getParameter("id"));
            userDao.deleteUserById(id);
            response.sendRedirect("list");
    }
}