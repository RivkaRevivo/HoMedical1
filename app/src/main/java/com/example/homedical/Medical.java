package com.example.homedical;

public class Medical {

    private String name;
    private String desc;
    private String problem;
    private int category;
    private String key;
    public Medical(String name, String desc, String problem, int category , String key) {
        this.name = name;
        this.desc = desc;
        this.problem = problem;
        this.key = key;
        this.category = category;
    }

    public String getKey() {
        return key;
    }
    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Medical (){}

}
