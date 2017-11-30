
package dev.uublabs.makingrestcalls.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyResponse {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("cars")
    @Expose
    private List<String> cars = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<String> getCars() {
        return cars;
    }

    public void setCars(List<String> cars) {
        this.cars = cars;
    }

}
