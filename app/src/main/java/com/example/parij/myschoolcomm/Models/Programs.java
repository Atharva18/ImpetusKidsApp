package com.example.parij.myschoolcomm.Models;

import java.util.ArrayList;

/**
 * Created by admin on 20/03/2018.
 */

public class Programs {
    String name;
    private ArrayList<Memory> memoryImageLinks = new ArrayList<>();

    public Programs() {
        memoryImageLinks = new ArrayList<>();
    }

    public Programs(String name, ArrayList<Memory> memoryImageLinks) {
        this.name = name;
        this.memoryImageLinks = memoryImageLinks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Memory> getMemoryImageLinks() {
        return memoryImageLinks;
    }

    public void setMemoryImageLinks(ArrayList<Memory> memoryImageLinks) {
        this.memoryImageLinks = memoryImageLinks;
    }
}
