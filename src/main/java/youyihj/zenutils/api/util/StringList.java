package youyihj.zenutils.api.util;

import com.google.common.base.Functions;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

@ZenRegister
@IterableSimple("string")
@ZenClass("mods.zenutils.StringList")
public class StringList implements Iterable<String> {
    private final List<String> inner;

    private StringList(Collection<?> list) {
        this.inner = list.stream().map(Functions.toStringFunction()).collect(Collectors.toList());
    }

    @ZenMethod
    public static StringList create(Collection<?> list) {
        return new StringList(list);
    }

    @ZenMethod
    public static StringList create(Object[] objects) {
        return new StringList(Arrays.asList(objects));
    }

    @ZenMethod
    public static StringList empty() {
        return new StringList(Collections.emptyList());
    }

    @ZenMethod
    public static StringList singletonList(Object object) {
        return new StringList(Collections.singletonList(object));
    }

    @Override
    @Nonnull
    public Iterator<String> iterator() {
        return inner.iterator();
    }

    @ZenMethod
    @ZenOperator(OperatorType.INDEXGET)
    public String get(int index) {
        return inner.get(index);
    }

    @ZenMethod
    @ZenOperator(OperatorType.INDEXSET)
    public String set(int index, Object obj) {
        return inner.set(index, String.valueOf(obj));
    }

    @ZenMethod
    public boolean add(Object obj) {
        return inner.add(String.valueOf(obj));
    }

    @ZenMethod
    public boolean remove(Object obj) {
        return inner.remove(String.valueOf(obj));
    }

    @ZenMethod
    public void clear() {
        inner.clear();
    }

    @ZenMethod
    public boolean removeIf(StringPredicate predicate) {
        return inner.removeIf(predicate);
    }

    @ZenMethod
    @ZenOperator(OperatorType.CONTAINS)
    public boolean contains(Object obj) {
        return inner.contains(String.valueOf(obj));
    }

    @ZenMethod
    public boolean add(String s) {
        return inner.add(s);
    }

    @ZenMethod
    public void insert(int index, String s) {
        inner.add(index, s);
    }

    @ZenMethod
    public boolean remove(String s) {
        return inner.remove(s);
    }

    @ZenMethod
    @ZenOperator(OperatorType.CONTAINS)
    public boolean contains(String s) {
        return inner.contains(s);
    }

    @ZenGetter
    @ZenMethod
    public int size() {
        return inner.size();
    }

    @ZenMethod
    public int indexOf(String s) {
        return inner.indexOf(s);
    }

    @ZenMethod
    public int lastIndexOf(String s) {
        return inner.lastIndexOf(s);
    }

    @ZenMethod
    public StringList subList(int fromIndex, int toIndex) {
        return StringList.create(inner.subList(fromIndex, toIndex));
    }

    @ZenMethod
    public void shuffle() {
        Collections.shuffle(inner);
    }

    @ZenMethod
    public String[] toArray() {
        return inner.toArray(new String[0]);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Iterator<String> iterator = this.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext()) sb.append(", ");
        }
        return sb.append("]").toString();
    }

    @ZenMethod
    public List<String> getInner() {
        return inner;
    }
}
