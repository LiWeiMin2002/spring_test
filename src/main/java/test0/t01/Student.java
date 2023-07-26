package test0.t01;

/**
 * @program: spring学习
 * @description: 学生实体类
 * @author: lwm
 * @create: 2023-07-26 10:12
 */
public class Student {
    private String id;
    private String name;
    private String sex;
    private Address address;

    public Student() {
        System.out.println("Student类构造成功......");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        System.out.println("setId：" + id);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("setName：" + name);
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        System.out.println("setSex：" + sex);
        this.sex = sex;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        System.out.println("setAddress：" + address);
        this.address = address;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", address=" + address +
                '}';
    }
}
