public class Overseer implements IAddFile, IDelFile {
    @Override
    public void added(String fileName) {
        System.out.println(this.getClass().getSimpleName() + ": Файл \"" + fileName + "\" добавлен.");
    }

    @Override
    public void deleted(String fileName) {
        System.out.println(this.getClass().getSimpleName() + ": Файл \"" + fileName + "\" удалён.");
    }

    public static void main(String[] args) {
        Monitor m = new Monitor();
        m.subscribe(new Overseer());
        m.subscribe((IDelFile) c -> System.out.println(c.getClass().getSimpleName() + ": File \"" + c + "\" deleted."));
        m.start();
    }
}
