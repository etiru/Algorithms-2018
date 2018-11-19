package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        }
        else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        }
        else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    public Node<T> findParents(T value) {
        if (root == null)
            return null;

        return findParents(root, null, value);
    }

    private Node<T> findParents(Node<T> start, Node<T> parent, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return parent;
        }
        else if (comparison < 0) {
            if (start.left == null) return parent;
            return findParents(start.left, start, value);
        }
        else {
            if (start.right == null) return parent;
            return findParents(start.right, start, value);
        }
    }

    private Set<T> toSet() {
        Set<T> set = new HashSet<>();
        if (root == null)
            return set;

        toSet(root, set);

        return set;
    }

    private void toSet(Node<T> current, Set<T> returnSet) {
        if (current.left != null)
            toSet(current.left, returnSet);

        returnSet.add(current.value);

        if (current.right != null)
            toSet(current.right, returnSet);
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    //O(n)
    @Override
    public boolean remove(Object o) {
        if(!contains(o)) return false;
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> parent = findParents(t);
        Node<T> child = find(t);

        if (child == root) {
            Node<T> nearest;

            if (child.right != null) {
                nearest = child.right;
                while (nearest.left != null)
                    nearest = nearest.left;
            }
            else if (child.left != null) {
                nearest = child.left;
                while (nearest.right != null)
                    nearest = nearest.right;
            }
            else {
                root = null;
                size = 0;
                return true;
            }

            Node<T> newNode = new Node<T>(nearest.value);
            Node<T> subParent = findParents(nearest.value);

            if (subParent.value.compareTo(nearest.value) > 0)
                subParent.left = subParent.left.right;
            else subParent.right = subParent.right.left;

            newNode.right = root.right;
            newNode.left = root.left;

            root = newNode;
            size--;
            return true;
        }

        if(child.left == null && child.right == null) {
            if (parent.value.compareTo(child.value) < 0)
                parent.right = null;
            else parent.left = null;
            size--;
            return true;
        }

        if(child.left == null || child.right == null){
            Node<T> replace;
            if(child.left != null)
                replace = child.left;
            else replace = child.right;

            if (parent.value.compareTo(child.value) < 0)
                parent.right = replace;
            else parent.left = replace;
            size--;
            return true;
        }

        Node<T> step = child.right;
        while (step.left != null)
            step = step.left;

        Node<T> nearNode = new Node<>(step.value);
        remove(nearNode.value);

        if (child.left != null && child.left.value != nearNode.value)
            nearNode.left = child.left;

        if (child.right != null && child.right.value != nearNode.value)
            nearNode.right = child.right;

        if (parent.left != null && parent.left.value.compareTo(child.value) == 0)
            parent.left = nearNode;
        else parent.right = nearNode;
        return true;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private BinaryTreeIterator() {
            list = new ArrayList<T>(toSet());
            Collections.sort(list);
        }

        private List<T> list;

        private int index = 0;
        /**
         * Поиск следующего элемента
         * Средняя
         */
        //O(nlog(n))
        @Override
        public boolean hasNext() {
            return index < BinaryTree.this.size;
        }

        @Override
        public T next() {
            int duplicateIndex = index;
            T ret = list.get(duplicateIndex);
            index++;

            return ret;
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        @Override
        public void remove() {
            BinaryTree.this.remove(list.get(index - 1));
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        // TODO
        throw new NotImplementedError();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
