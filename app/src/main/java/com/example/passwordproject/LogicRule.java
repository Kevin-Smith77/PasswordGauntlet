package com.example.passwordproject;

import java.util.Arrays;
import java.util.List;

// Rule requiring a specific name
public class LogicRule implements MainActivity.PasswordRule {



    @Override
    public String getHint() {
        return "The password must include the name of" +
            " a Wheaton College computer science professor.";
    }

    @Override
    public boolean validate(String text) {
        String[] professors = {"Gagne","Gousie","Tong",
            "Leblanc","Almeder"};
        for(String oneProfessor : professors){
            if(text.toLowerCase().contains(oneProfessor.toLowerCase())){
                return true;
            }

    }
        return false;
    }
}
