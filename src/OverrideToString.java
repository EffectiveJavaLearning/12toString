/**
 * 建议在所有类中都重写toString方法。
 *
 * 虽然在{@link Object#toString()}有其默认的实现方法，会返回"类名" + "@" + "16进制无符号hashCode"，
 * 但其提供信息并不直观、丰富，16进制hashCode一般不如直接展示数据，因此建议所有类中一律重写这个方法。
 *
 * 这一条并不像重写equals或者hashCode时那样严格，各种要求，只要整一个便于debug的toString就可以了。
 * 最好当这个实例被作为println, printf, 字符串连接操作符(+号), assert等的参数时，也会自动被调用，
 * 将重要信息展示出来。并且当这个实例被其他对象使用，但运行时出现异常时，
 * 也有可能自动调用这个实例的toString()方法。如果不重写toString，那么就有可能打印出又多又没用的信息，
 * 比如还是这个{@link PhoneNumber},重写之后就可以很方便地在命令行中打印该实例的具体信息了。
 *
 * 虽然不这么做也没什么大问题，但当我们debug时，显然更愿意看见这种信息:
 * “PhoneNumber@ number:4008123123  sex:male  initmacy:29”，而不是一串儿16进制数字。
 *
 * 虽然对于toString来说，应该尽量全面地展示一切有用数据，但如果对象的有用数据特别多，
 * 或者不是很容易以字符串形式展示出来的话，就应该展示一些总述与摘要信息，比如
 * "Manhattan residential phone directory (1487536 listings)"或者"Thread[main,5,main]"这样。
 * 但是，这样做的前提是要保证别写出来之后让自己都看不懂，比如这种：
 * “Assertion failure: expected {abc, 123}, but was {abc, 123}”
 *
 * 实现toString方法时，必须决定是否在文档中指定返回值格式。对于一些例如PhoneNumber或者矩阵的信息而言，
 * 指定格式的优点是提供了标准、明确、可读的表示形式，这可用于输入输出，也可用于持久化易读性高的对象信息，
 * 例如csv文件。最好顺便提供一个配套的静态工厂或构造函数，这样就可以轻松地在字符串表示形式，
 * 与对应实例之间切换。这种方法广泛用于Java平台类库，包括{@link java.math.BigInteger},
 * {@link java.math.BigDecimal}和大多数基本类型的装箱类
 *
 * 指定返回值格式的缺陷也比较明显，一旦格式被定，而该类又被广泛运用，那么如果万一需要修改它将会十分麻烦，
 * 不指定格式将会提供更好的灵活性
 *
 * 不过，无论是否指定格式，都应该在文档里面讲清楚这个toString方法都展示了什么信息，你想用它来做什么等等。
 * 如果指定了格式，那么就要严格地遵守，比如下面这个带格式的toString方法以及其下面另外一段注释的例子：
 * {@link #toString()}
 *
 * 另外，应该对toString中包含的信息提供编程访问(programmatic access)，比如应将下面的areaCode,
 * prefix,lineNum解析好，否则需要这些信息的程序员就需要手动解析它们，而这个过程还挺容易出错，
 * 导致系统不稳定，尤其是当格式发生变化时。如果不提供访问这些字段的方法，那这些字段实际上就起到了API作用，
 * 即使已经声明过它们可能会出现变化。(?)
 *
 * 没有必要在静态类写toString(),因为静态工具类不需要实例化，因而无需打印出有关实例的信息;
 * 也没必要在枚举类中写toString(),因为Java已经为它们提供了近乎完美的toString方法了(打印出枚举字段名)，
 * 比如{@link ToStringEnum}。
 *
 * 建议为子类需要相同形式toString方法的抽象父类(abstract class) 写一个toString方法，
 * 比如Java中{@link java.util.AbstractCollection}等集合的抽象类就是这么干的。
 *
 * Google的开源工具Auto Value和大多数IDE也可以自动生成toString方法，虽然没办法自动确认正确的格式
 * (比如电话号码的XXX-YYY-ZZZZ),但至少对于没有格式要求的toString方法来说，
 * 这要比从Object那里继承过来的toString好得没倍。
 *
 * 总之，toString为调试工作带来了极大便利，应该用美观的形式提供一个易读易用的toString方法。
 *
 *
 * @author LightDance
 */
public class OverrideToString {

    String number;

    public OverrideToString(String number) {
        this.number = number;
    }

    /**
     * 返回符合日常所见的电话号码形式的数据
     * 字符串包含12个字符："XXX-YYY-ZZZZ",
     * XXX -- 区号, YYY --前缀, ZZZZ 行号
     * 每个字母代表一个数字。
     *
     * 如果某一部分数字太小，将会导致少一位(或者两位、三位)，
     * 因此需要用下面的String.format补零。
     * 比如当ZZZZ = 123时，实际显示的行号为 0123
     */
    @Override public String toString() {

        int areaCode = Integer.parseInt(number.substring(0 , 3)),
                prefix = Integer.parseInt(number.substring(3 , 6)),
                lineNum = Integer.parseInt(number.substring(6 , 10));
        return String.format("%03d-%03d-%04d",
                areaCode, prefix, lineNum);
    }

    /**
     * ****如果决定不要规定格式，那么文档可以这样写：****
     *
     * 关于本Potion类的toString方法的简单说明。
     * 对于该方法的具体表现形式并未加以约束，但可以参考以下形式：
     *
     * "[Potion #9: type=love, smell=turpentine, look=india ink]"
     */

    public static void main(String[] args) {
        System.out.println(new OverrideToString("4008123123"));
        System.out.println(ToStringEnum.str1dfsfa.toString());
    }
}
