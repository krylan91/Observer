@FunctionalInterface
public interface IDelFile extends IFile {
    void deleted(String fileName);
}
