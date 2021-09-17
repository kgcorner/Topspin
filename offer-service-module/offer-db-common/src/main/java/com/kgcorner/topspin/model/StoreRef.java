package com.kgcorner.topspin.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/09/21
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StoreRef {
    private String id;
    private String name;
    private String description;
}