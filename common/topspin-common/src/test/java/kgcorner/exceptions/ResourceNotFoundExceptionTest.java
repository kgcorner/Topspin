package kgcorner.exceptions;

import com.kgcorner.exceptions.ResourceNotFoundException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 19/11/19
 */

public class ResourceNotFoundExceptionTest {

    @Test
    public void resourceNotFound() {
        ResourceNotFoundException exception = new ResourceNotFoundException("test");
        assertEquals("test", exception.getMessage());
        exception = new ResourceNotFoundException();
        assertEquals("no such resource found", exception.getMessage());
    }

}