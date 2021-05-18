package com.kgcorner.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.ParameterMode;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 17/05/21
 */

public class OperationTest {

    @Test
    public void testGetterSettersWithCommonType() {
        String value = "Sample Value";
        String name = "ColumnName";
        Operation operation = new Operation(value, Operation.TYPES.STRING, name,
            Operation.OPERATORS.EQ);
        Assert.assertEquals(value, operation.getValue());
        Assert.assertEquals(name, operation.getName());
        Assert.assertEquals(Operation.TYPES.STRING, operation.getType());
        Assert.assertEquals(Operation.OPERATORS.EQ, operation.getOperator());
        Assert.assertEquals(String.class, operation.getOperandType());
    }

    @Test
    public void testParameterModeConstructor() {
        Operation operation = new Operation("Some Value", Operation.TYPES.STRING, "Some name",
            ParameterMode.IN);
        Assert.assertEquals(ParameterMode.IN, operation.getMode());
    }

    @Test
    public void setValue() {
        String value = "Sample Value";
        String name = "ColumnName";
        Operation operation = new Operation("Some Value", Operation.TYPES.STRING, name,
            Operation.OPERATORS.EQ);
        operation.setValue(value);
        Assert.assertEquals(value, operation.getValue());
    }

    @Test
    public void setType() {
        Operation.TYPES type = Operation.TYPES.BOOLEAN;

        String name = "ColumnName";
        Operation operation = new Operation("Some Value", Operation.TYPES.STRING, name,
            Operation.OPERATORS.EQ);
        operation.setType(type);
        Assert.assertEquals(type, operation.getType());
        Assert.assertEquals(Boolean.class, operation.getOperandType());
    }

    @Test
    public void testOperandType() {
        Operation.TYPES type = Operation.TYPES.BOOLEAN;

        String name = "ColumnName";
        Operation operation = new Operation("Some Value", OperationTest.class, name,
            Operation.OPERATORS.EQ);
        Assert.assertEquals(OperationTest.class, operation.getOperandType());
    }


    @Test
    public void setName() {
        String name = "ColumnName";
        Operation operation = new Operation("Some Value", Operation.TYPES.STRING, "TmpName",
            Operation.OPERATORS.EQ);
        operation.setName(name);
        Assert.assertEquals(name, operation.getName());
    }

    @Test
    public void setMode() {
        ParameterMode mode = ParameterMode.IN;
        Operation operation = new Operation("Some Value", Operation.TYPES.STRING, "TmpName",
            Operation.OPERATORS.EQ);
        operation.setMode(mode);
        Assert.assertEquals(mode, operation.getMode());
    }

    @Test
    public void getOperator() {
    }
}