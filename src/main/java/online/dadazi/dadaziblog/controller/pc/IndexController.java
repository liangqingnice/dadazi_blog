package online.dadazi.dadaziblog.controller.pc;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * pc控制层
 * @author lqk
 */
@Controller("IndexController")
public class IndexController {
    @RequestMapping("/index")
    public String toIndexPage(){
        return "index";
    }
}
