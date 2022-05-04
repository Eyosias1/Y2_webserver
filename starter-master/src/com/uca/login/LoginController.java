package com.uca.login;

import com.uca.core.TeacherCore;
import com.uca.dao._Encryptor;
import com.uca.entity.TeacherEntity;
import com.uca.gui.LoginGUI;
import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;

import java.io.IOException;

public class LoginController
{
    public static String pathSaved = null;

    public static boolean authenticate(String username, String password)
    {
        if (username.isEmpty() || password.isEmpty())
        { // tried to log in without filling in the form
            return false;
        }
        TeacherEntity user = TeacherCore.readByUserName(username);
        if (user == null)
        { // means the user doesn't exist
            return false;
        }
        return _Encryptor.verifyUserPassword(password, user.getUserPwd(), user.getUserSalt());
    }

    public static String handleLoginPost(Request req, Response res) throws TemplateException, IOException
    {
        if (!authenticate(req.queryParams("username"), req.queryParams("userpwd")))
        {
            return LoginGUI.display("&eacute;chec d'authentification");
        }
        // else, login success
        req.session().attribute("currentUser", req.queryParams("username"));
        if (pathSaved != null)
        {
            res.redirect(pathSaved);
            // if they requested to go somewhere without being logged in...
            // once logged in they will be redirected there
        }
        res.redirect("/");
        // if logged in without a query we redirect them to the root
        return null;
    }

    public static void ensureUserIsLoggedIn(Request request, Response response)
    {
        if (request.session().attribute("currentUser") == null)
        {
            pathSaved = request.pathInfo(); // saves the path to redirect right away after login
            response.redirect("/login");
        }
    }
}