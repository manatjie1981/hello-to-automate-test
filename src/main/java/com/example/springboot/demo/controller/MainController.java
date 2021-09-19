package com.example.springboot.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;

@Controller
public class MainController {

    @GetMapping("/from")
    public String from(){
        return "main_from";
    }

    @GetMapping("/runtestcase")
    public String runtestcase(){
        run();
        return "main_from";
    }

    private void run(){
        try
        {

            File dir = new File("D:/robot");
            //String cmd = "robot -v policy:"+policyNo+" -v date:"+admitDate+" -v doc:"+doctorNo+" ecs_create_claim_robot_framework.robot";
            String cmd = "robot test_script01.txt";
            System.out.println("cmd : "+cmd);
            Process process = Runtime.getRuntime().exec(cmd, null, dir);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //return null;
    }
}
