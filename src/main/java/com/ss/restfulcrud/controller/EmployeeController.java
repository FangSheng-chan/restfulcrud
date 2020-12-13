package com.ss.restfulcrud.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ss.restfulcrud.entities.Employee;
import com.ss.restfulcrud.entities.Msg;
import com.ss.restfulcrud.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping("/emps")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    @RequestMapping("/emp")
    public String emps() {
        return "emp/list";
    }

    @ResponseBody
    @RequestMapping("/showEmps")
    public Msg getEmpsWithJson(Model model, @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum) {
        String orderBy = "id";
        PageHelper.startPage(pageNum, 5, orderBy);
        List<Employee> emps = employeeService.findAllEmp();
        PageInfo pageInfo = new PageInfo(emps, 5);
        return Msg.success().add("pageInfo", pageInfo);
    }


    //@RequestMapping("/showEmps")
    public String list(Model model,
                       @RequestParam(required = false, defaultValue = "1", value = "pageNum") Integer pageNum,
                       @RequestParam(defaultValue = "8", value = "pageSize") Integer pageSize) {
//        Collection<User> users = userService.getAll();
//        //放在请求域中
//        model.addAttribute("users",users);
        //为了程序的严谨性，判断非空：
        //设置默认当前页
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        //设置默认每页显示的数据数
        if (pageSize == null) {
            pageSize = 1;
        }
        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNum, pageSize);
        //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
        try {
            List<Employee> employeeList = employeeService.findAllEmp();
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<Employee> pageInfo = new PageInfo<Employee>(employeeList, pageSize);
            //4.使用model传参数回前端
            model.addAttribute("pageInfo", pageInfo);
            model.addAttribute("employeeList", employeeList);
        } finally {
            //清理 ThreadLocal 存储的分页参数,保证线程安全
            PageHelper.clearPage();
        }
        return "/emp/list";
    }

//    @PostMapping("/addEmp")
//    @ResponseBody
//    public ModelAndView addEmp(Employee employee){
//        ModelAndView mav = new ModelAndView();
////        Map<String, Object> map = new HashMap<String, Object>();
////        String name = request.getParameter("name");
////        String password = request.getParameter("password");
////        String phone = request.getParameter("phone");
////        String email = request.getParameter("email");
////        String gender = request.getParameter("gender");
////        String birth = request.getParameter("birth");
////        Date date = new Date();
////        try {
////            if (birth != null) {
////                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//yyyy-mm-dd, 会出现时间不对, 因为小写的mm是代表: 秒
////                date = sdf.parse(birth);
////                employee.setBirth(date);
////            }
////            employee.setName(name);
////            employee.setPassword(password);
////            employee.setPhone(phone);
////            employee.setEmail(email);
////            employee.setGender(gender);
////            employeeService.addEmp(employee);
////        } catch (ParseException e) {
////            date = null;
////            e.printStackTrace();
////        }
//        employeeService.addEmp(employee);
//        return mav;
//    }

    @PostMapping("/addEmp")
    @ResponseBody
    public Msg addEmp(Employee employee) {
        employeeService.addEmp(employee);
//        System.err.println(employee.getDId());
        return Msg.success();
    }

    @GetMapping("/emp/{id}")
    @ResponseBody
    public Msg getEmp(@PathVariable("id") Integer id, Model model) {
        Employee employee = employeeService.getEmpId(id);
//        employee.setDId(employee.getDepartment().getDepartmentId());
        return Msg.success().add("emp", employee);
    }

    /***
     * 更新
     * @param employee
     * @param
     * @return
     */
    @PutMapping("/emp/{id}")
    @ResponseBody
    public Msg editEmp(@PathVariable("id") Integer id,Employee employee) {
        System.err.println(employee);
        employeeService.update(employee);
        return Msg.success();
    }

    //用户删除
    @DeleteMapping("/emp/{id}")
    @ResponseBody
    public Msg deleteEmpById(@PathVariable("id") Integer id) {
        employeeService.delEmp(id);
        return Msg.success();
    }

    @RequestMapping("/performance")
    public String performance() {
        return "emp/performance";
    }

    @RequestMapping("/EPerformance")
    @ResponseBody
    public List<Employee> EPerformance() {
        List<Employee> list = employeeService.findPerformance();
        return list;
    }

    /***
     * 检查用户名是否可用
     * @param name
     * @return
     */
    @ResponseBody
    @PostMapping("/checkEmployee")
    public Msg checkEmployee(@RequestParam("name") String name) {
        //判断用户名是否合法
        String regName = "(^[A-Za-z0-9]{6,16}$)|(^[\u4e00-\u9fa5]{2,5}$)";
        if (!name.matches(regName)) {
            return Msg.fail().add("va_msg", "用户名可以是2-5位中文或者6-16位英文和数字的组合");
        }
        //数据库用户名校验
        boolean flag = employeeService.checkEmp(name);
        if (!flag) {
            return Msg.success();
        } else {
            return Msg.fail().add("va_msg", "用户名已经存在");
        }
    }
}
