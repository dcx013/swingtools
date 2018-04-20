import form.Form;

/**
 * @author mhh
 * @since 2018/4/20
 */
public class Main {
    public static void main(String[] args) {
        new Director(new Form(2, 2)).buildForm("1");
    }
}
