package test0.t01;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-07-26 11:22
 */
public class Address {
    private String province;//省
    private String city;//市

    public Address() {
        System.out.println("Address构造成功......");
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        System.out.println("setProvince：" + province);
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        System.out.println("setCity：" + city);
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
