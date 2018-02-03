package edu.nju.trainCollege.controller;

import edu.nju.trainCollege.model.College;
import edu.nju.trainCollege.model.Manager;
import edu.nju.trainCollege.model.MyData;
import edu.nju.trainCollege.model.Student;
import edu.nju.trainCollege.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("college_manager/")
@SessionAttributes({"manager"})
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @RequestMapping(value = "all_students", method = RequestMethod.GET)
    public String all_student(){
        return "/manager/all_students";
    }

    @RequestMapping(value = "all_students", method = RequestMethod.POST)
    @ResponseBody
    public MyData getAllStudent(){
        MyData data = new MyData();
        data.setData(managerService.getAllStudents());
        return data;
    }

    @RequestMapping(value = "get_student", method = RequestMethod.POST)
    @ResponseBody
    public Student getStudentById(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        return managerService.getStudent(id);
    }

    @RequestMapping(value = "all_colleges", method = RequestMethod.GET)
    public String all_college(){
        return "/manager/all_colleges";
    }

    @RequestMapping(value = "all_colleges", method = RequestMethod.POST)
    @ResponseBody
    public MyData getAllColleges(){
        MyData data = new MyData();
        data.setData(managerService.getAllColleges());
        return data;
    }

    @RequestMapping(value = "unchecked_colleges", method = RequestMethod.GET)
    public String unchecked_college(){
        return "/manager/unchecked_colleges";
    }

    @RequestMapping(value = "unchecked_colleges", method = RequestMethod.POST)
    @ResponseBody
    public MyData getUncheckedColleges(){
        MyData data = new MyData();
        data.setData(managerService.getUncheckedColleges());
        return data;
    }

    @RequestMapping(value = "get_college", method = RequestMethod.POST)
    @ResponseBody
    public College getCollegeById(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        return managerService.getCollege(id);
    }

    @RequestMapping(value = "check_college", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkCollegeById(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        return managerService.changeCollegeState(id,1);
    }

    @RequestMapping(value = "dismiss_college", method = RequestMethod.POST)
    @ResponseBody
    public boolean dismissCollegeById(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        return managerService.changeCollegeState(id,2);
    }

    @RequestMapping("index")
    public String index(){
        return "/manager/index";
    }

    @RequestMapping("homepage")
    public String homepage(){
        return "/manager/homepage";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, ModelMap model){
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        Manager manager = managerService.login(name,password);

        if(manager!=null){
//             因为注解，该属性直接添加到session中
            model.addAttribute("manager",manager);
            return "redirect:/college_manager/homepage";

        }else{
            model.addAttribute("error"," * 用户名或密码不正确");
            return "/manager/index";
        }
    }
}