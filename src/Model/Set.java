package Model;

import java.util.ArrayList;
import java.util.List;

public class Set<T> {
    private final List<T> set;

    public Set() {
        set = new ArrayList<>();
    }

    public List<T> getSet() {
        return set;
    }

    public void add(T object) throws MyException {
        if (!set.contains(object)) { //checks duplication
            set.add(object);
        } else throw new MyException("Set already contains this object: " + object.toString());
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public boolean remove(T object) {
        return set.remove(object);
    }

    public T get(int i) {
        return set.get(i);
    }

    public boolean contains(T object) {
        return set.contains(object);
    }

    public int size() {
        return set.size();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        set.forEach(item -> stringBuilder.append(item).append("\n"));
        return stringBuilder.toString();
    }
}
