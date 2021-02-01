package ru.jobvms3.examples.api;

import java.io.Serializable;
import java.util.List;

/**
 * Страница - содержит несколько выбранных элементов, а также информацию об общем количестве страниц в выборке.
 * @param <T> - элемент страницы.
 */
public class Page <T> implements Serializable {

    /**
     * Размер страницы.
     */
    private int size;

    /**
     * Номер страницы.
     */
    private int pageNumber;

    /**
     * Количество страниц.
     */
    private int pagesCount;

    /**
     * Список объектов на странице.
     */
    private List<T> elements;

    public Page(int pageNumber, int pagesCount, List<T> elements) {
        this.pageNumber = pageNumber;
        this.pagesCount = pagesCount;
        this.elements = elements;
        this.size = elements.size();
    }

    public int getSize() {
        return size;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public List<T> getElements() {
        return elements;
    }

}
