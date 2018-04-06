import form.Form;

/**
 * 指挥者
 *
 * @author mhh
 * @since 2018/4/2
 */
public class Director {
    private Form form;

    public Director(Form form) {
        this.form = form;
    }

    public void buildForm() {
        form.createMainForm();

        form.createEditForm();

        form.createMessageArea();

        form.setFormVisible();
    }

    public static void main(String[] args) {
        new Director(new Form(3, 3)).buildForm();
    }
}
