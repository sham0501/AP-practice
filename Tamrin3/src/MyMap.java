import java.util.Comparator;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class MyMap<K, V> {
    private Object[] keys;
    private Object[] values;
    private int size;

    interface MyIterator<K> extends Iterator<K> {
        K nextByAmount(int amount);

        K prevByAmount(int amount);

        boolean hasPrevious();

        K previous();

        void remove();
        // same as Iterator remove

        int previousIndex();

        int nextIndex();
    }

    public MyMap() {
        keys = new Object[100];
        values = new Object[100];
        size = 0;
    }

    public V get(K key) {
        for (int i = 0; i < size; i++) {
            if (keys[i].equals(key)) return (V) (values[i]);
        }
        return null;
    }

    public V getOrDefault(K key, V value) {
        for (int i = 0; i < size; i++) {
            if (keys[i].equals(key)) return (V) values[i];
        }
        return value;
    }

    public V put(K key, V value) {
        for (int i = 0; i < size; i++) {
            if (keys[i].equals(key)) return (V) (values[i] = value);
        }
        increaseArraySize();
        keys[size - 1] = key;
        return (V) (values[size - 1] = value);
    }

    private void increaseArraySize() {
        Object[] tempKeys = new Object[size + 1];
        Object[] tempValues = new Object[size + 1];
        for (int i = 0; i < size; i++) {
            tempKeys[i] = keys[i];
            tempValues[i] = values[i];
        }
        keys = tempKeys;
        values = tempValues;
        size++;
    }

    public int indexOf(K key) {
        for (int i = 0; i < size; i++) {
            if (keys[i].equals(key)) return i;
        }
        return -1;
    }

    public V putIfAbsent(K key, V value) {
        for (int i = 0; i < size; i++) {
            if (keys[i].equals(key)) return (V) values[i];
        }
        increaseArraySize();
        keys[size - 1] = key;
        return (V) (values[size - 1] = value);
    }

    public K[] keys() {
        Object[] tempKeys = new Object[size];
        for (int i = 0; i < size; i++) {
            tempKeys[i] = keys[i];
        }
        return (K[]) tempKeys;
    }

    public V[] values() {
        Object[] tempValues = new Object[size];
        for (int i = 0; i < size; i++) {
            tempValues[i] = values[i];
        }
        return (V[]) tempValues;
    }

    public void putAll(MyMap<? extends K, ? extends V> map) {
        for (int i = 0; i < map.size(); i++) {
            this.put(map.getKey(i), map.getValue(i));
        }
    }

    public boolean containsKey(K key) {
        return this.indexOf(key) != -1;
    }

    public boolean containsValue(V value) {
        for (int i = 0; i < size; i++) {
            if (values[i].equals(value)) return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void clear() {
        keys = new Object[0];
        values = new Object[0];
        size = 0;
    }

    public V remove(K key) {
        int index = this.indexOf(key);
        if (index != -1) {
            V removedValue = (V) values[index];
            removeWithIndex(index);
            return removedValue;
        }
        return null;
    }

    public V remove(int index) {
        if (index >= 0 && index < size) {
            V removedValue = (V) values[index];
            removeWithIndex(index);
            return removedValue;
        }
        return null;
    }

    private void removeWithIndex(int index) {
        Object[] tempKeys = new Object[size - 1];
        Object[] tempValues = new Object[size - 1];
        for (int i = 0, x = 0; i < size - 1; i++) {
            if (i == index) x = 1;
            tempKeys[i] = keys[i + x];
            tempValues[i] = values[i + x];
        }
        keys = tempKeys;
        values = tempValues;
        size--;
    }

    public void removeAll(K[] keys) {
        for (int i = 0; i < size; i++) {
            remove(keys[i]);
        }
    }

    public void removeIfValue(Predicate<? super V> predicate) {
        if (predicate != null) {
            for (int i = 0; i < size; i++) {
                if (predicate.test((V) values[i])) {
                    this.remove((K) keys[i]);
                    i--;
                }
            }
        }
    }

    public void removeIfKey(Predicate<? super K> predicate) {
        if (predicate != null) {
            for (int i = 0; i < size; i++) {
                if (predicate.test((K) keys[i])) {
                    this.remove((K) keys[i]);
                    i--;
                }
            }
        }
    }

    public void sort(Comparator<? super K> comparator) {
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (comparator.compare((K) keys[i], (K) keys[j]) > 0) {
                    K tempKey = (K) keys[i];
                    V tempValue = (V) values[i];
                    keys[i] = keys[j];
                    values[i] = values[j];
                    keys[j] = tempKey;
                    values[j] = tempValue;
                }
            }
        }
    }

    public MyIterator<K> iterator() {
        return new MyIterator() {
            int index;

            @Override
            public K nextByAmount(int amount) {
                if (index + amount >= size) {
                    index = size;
                    return (K) keys[index - 1];
                }
                index += amount;
                return (K) keys[index - 1];
            }

            @Override
            public K prevByAmount(int amount) {
                if (index - amount < 0){
                    index = 0;
                    return (K) keys[index];
                }
                index -= amount;
                return (K) keys[index];
            }

            @Override
            public boolean hasPrevious() {
                return index > 0;
            }

            @Override
            public K previous() {
                return (K) keys[--index];
            }

            @Override
            public void remove() {
                MyMap.this.remove(--index);
            }

            @Override
            public int previousIndex() {
                return index - 1;
            }

            @Override
            public int nextIndex() {
                return index;
            }

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public K next() {
                return (K) keys[index++];
            }
        };
    }

    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> biFunction) {
        if (biFunction != null) {
            for (int i = 0; i < size; i++) {
                values[i] = biFunction.apply((K) keys[i], (V) values[i]);
            }
        }
    }

    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        int index = this.indexOf(key);
        if (biFunction != null && index != -1) {
            values[index] = biFunction.apply((K) keys[index], (V) values[index]);
            V value = (V) values[index];
            if (value == null) removeWithIndex(index);
            return value;
        }
        return null;
    }

    //additional1
    public V getValue(int index) {
        return (V) values[index];
    }

    //additional2
    public K getKey(int index) {
        return (K) keys[index];
    }
}