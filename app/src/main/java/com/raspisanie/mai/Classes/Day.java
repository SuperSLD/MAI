package com.raspisanie.mai.Classes;

import java.util.ArrayList;

/**
 * День. Чась недели.
 */
public class Day {
    private String date;
    private String name;
    private ArrayList<Subject> subjects;

    /**
     * Конструктор с вводои изначальных данных.
     * @param date дата.
     * @param name день недели.
     */
    public Day(String date, String name) {
        this.date = date;
        this.name = name;
        this.subjects = new ArrayList<>();
    }

    /**
     * Получение даты.
     * @return дата
     */
    public String getDate() {
        return this.date;
    }

    /**
     * Получение названия дня недели.
     * @return название
     */
    public String getName() {
        return this.name;
    }

    /**
     * Добавление учебного предмета в расписание дня.
     * @param subject заранее подготовленный предмет.
     */
    public void addSubject(Subject subject) {
        this.subjects.add(subject);
    }

    /**
     * Получение списка всех предметов дня.
     * @return
     */
    public ArrayList<Subject> getSubjectList() {
        return this.subjects;
    }
}
