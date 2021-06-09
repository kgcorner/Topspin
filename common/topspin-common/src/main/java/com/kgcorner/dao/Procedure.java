package com.kgcorner.dao;


import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 09/05/21
 */

public class Procedure {
    private final String name;
    private final List<Operation> arguments;

    public Procedure(String name, List<Operation> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public List<Operation> getArguments() {
        return arguments;
    }
}