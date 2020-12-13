package com.ss.restfulcrud.entities;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.stream.Stream;

public class MavReturnTemp {
    private final static String SUCCESS = "success";
    private final static String DATA = "data";
    private final static String MESSAGE = "message";

    public static ModelAndView success(Object data) {
        ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
        mav.addObject(SUCCESS, true);
        mav.addObject(DATA, true);
        return mav;
    }

    public static ModelAndView success(String... message) {
        ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
        StringBuffer sb = new StringBuffer();
        Stream.of(message).forEach(sb::append);
        mav.addObject(SUCCESS, true);
        mav.addObject(MESSAGE, sb);
        return mav;
    }
}
