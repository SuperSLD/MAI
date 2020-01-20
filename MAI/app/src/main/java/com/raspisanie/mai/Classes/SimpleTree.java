package com.raspisanie.mai.Classes;

import java.util.ArrayList;

/**
 * Реализация дерева написанная на коленках.
 * @param <Type> тип данных хранящийся в дереве.
 */
public class SimpleTree<Type> {
    private Type value;
    private ArrayList<SimpleTree<Type>> childs;

    /**
     * Конструктор с исходным значением.
     * @param value значение узла дерева.
     */
    public SimpleTree(Type value) {
        childs = new ArrayList<>();
        this.value = value;
    }

    /**
     * Возврат значения узла дерева.
     * @return значение узла дерева.
     */
    public Type getValue() {
        return value;
    }

    /**
     * Добавление дочерней ветки в дерево.
     * @param child элеметн дерева.
     */
    public void addChild(SimpleTree<Type> child) {
        childs.add(child);
    }

    /**
     * Возврат списка дочерних элеметнов данного
     * элемента.
     * @return список SimpleTree<Type>
     */
    public ArrayList<SimpleTree<Type>> getChildList() {
        return childs;
    }

    /**
     * Перевод деоева в строку.
     * @param n уровень дерева (для выравнивания текста).
     * @return Строка вида
     *    .n
     *    .___no
     *    .___n1
     *    .______n10
     *    .______n11
     *    .______n12
     *    .___n2
     *    .______n10
     *    .______n21
     */
    public String toString(int n) {
        String ret = "";

        String tab = "";
        for (int i = 0; i < n; i++) tab += "___";

        ret += "." + tab + value.toString() + "\n";
        for (int i = 0; i < childs.size(); i++) {
            ret += childs.get(i).toString(n + 1);
        }

        return ret;
    }
}