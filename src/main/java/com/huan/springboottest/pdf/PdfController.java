package com.huan.springboottest.pdf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

/**
 * @Author: Huan
 * @CreateTime: 2020-05-10 14:46
 */
@Controller
@RequestMapping("pdf")
public class PdfController {

    @GetMapping("get")
    public ModelAndView get(){
        View view = new PdfView();
        ModelAndView mv = new ModelAndView();
        mv.setView(view);
        return mv;
    }
}
