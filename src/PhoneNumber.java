/**
 * 用于展示如何用toString更方便地展示该实例的信息，
 * 重写之后就可以很方便地在命令行中打印该实例的具体信息了。
 *
 * @author LightDance
 */
public class PhoneNumber {

    String number;

    boolean sex;

    int intimacy;

    public PhoneNumber(String number, boolean sex, int intimacy) {
        this.number = number;
        this.sex = sex;
        this.intimacy = intimacy;
    }

    @Override
    public String toString() {
        return getClass().getName() +
                "@ number:" + number +
                "  sex:" + (sex?"male":"female") +
                "  initmacy:" + intimacy;
    }

    public static void main(String[] args) {
        PhoneNumber phoneNumber = new PhoneNumber("4008123123" , true , 29);
        System.out.println(phoneNumber);
    }
}
