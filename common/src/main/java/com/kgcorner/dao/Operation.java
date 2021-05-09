package com.kgcorner.dao;


import javax.persistence.ParameterMode;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 09/05/21
 */

public class Operation {
    public enum TYPES {
        BOOLEAN,
        STRING,
        NUMBER
    }

    public Class classType;

    public ParameterMode mode;

    public enum OPERATORS {
        GE,
        EQ,
        LE,
        GT,
        LT,
        TRUTH,
        FAKE,
        LIKE,
        IS_NOT_NULL,
        IS_NULL;
    }

    private Object value;
    private TYPES type;
    private String name;
    private OPERATORS operator;


    /**
     * Creates {@link Operation} object using given parameters. Use this if you expect to use callable
     * @param value Value of the operand
     * @param type {@link TYPES} of the operand or field in object
     * @param name name of the field in this object which will be passed to procedure
     * @param mode {@link ParameterMode} mode of the parameter
     */
    public Operation(Object value, TYPES type, String name, ParameterMode mode) {
        super();
        this.mode = mode;
        this.value = value;
        this.type = type;
        this.name = name;
    }

    /**
     * Use this operand if you want to use criteria api
     * @param value Value of the operand
     * @param type {@link TYPES} of the object ot be populated
     * @param name name of the field in this object which will be used for comparision
     * @param operator {@link Operation} which will be applied on this operand
     */
    public Operation(Object value, TYPES type, String name, OPERATORS operator) {
        super();
        this.value = value;
        this.type = type;
        this.name = name;
        this.operator = operator;
    }

    /**
     * Use this operand if you expect to use criteria api
     * @param value Value of the operand
     * @param type {@link Class} of the object ot be populated
     * @param name name of the field in this object which will be used for comparision
     * @param operator {@link Operation} which will be applied on this operand
     */
    public Operation(Object value, Class type, String name, OPERATORS operator) {
        super();
        this.value = value;
        this.classType = type;
        this.name = name;
        this.operator = operator;
    }



    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public TYPES getType() {
        return type;
    }

    public void setType(TYPES type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getOperandType() {
        Class type = null;
        if(this.type != null) {
            switch (this.type) {
                case BOOLEAN:
                    type = Boolean.class;
                    break;
                case STRING:
                    type = String.class;
                    break;
                case NUMBER:
                    type = Integer.class;
                    break;
            }
        }
        else {
            type = classType;
        }
        return type;
    }

    public ParameterMode getMode() {
        return mode;
    }

    public void setMode(ParameterMode mode) {
        this.mode = mode;
    }

    public OPERATORS getOperator() {
        return operator;
    }




}