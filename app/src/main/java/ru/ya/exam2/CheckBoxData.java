package ru.ya.exam2;

/**
 * Created by vanya on 23.01.15.
 */
public class CheckBoxData {
    private boolean flag;
    private String name;

    public CheckBoxData(boolean flag, String name) {
        this.flag = flag;
        this.name = name;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFlag() {
        return flag;
    }

    public String getName() {
        return name;
    }
}
