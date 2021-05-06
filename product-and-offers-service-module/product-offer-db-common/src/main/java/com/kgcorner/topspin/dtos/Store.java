package com.kgcorner.topspin.dtos;

import java.io.Serializable;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 29/04/21
 */

public interface Store extends Serializable {
    String getId();
    String getName();
    String getDescription();
}