package com.raspisanie.mai.Classes.TimeTable;

public class Subject {
    private String time;
    private String type;
    private String name;
    private String lecturer;
    private String place;

    /**
     * Конструктор.
     * @param time время занятия.
     * @param type тип занятия.
     * @param name название предмета.
     * @param lecturer имя преподавателя.
     */
    public Subject(String time,
                   String type,
                   String name,
                   String lecturer,
                   String place) {
        this.time = time;
        this.type = type;
        this.name = name;
        this.lecturer = lecturer;
        this.place = place;
    }

    /**
     * Получение времени занятия.
     * @return время
     */
    public String getTime() {
        return this.time;
    }

    /**
     * Получение типа занятия
     * @return тип
     */
    public String getType() {
        return this.type;
    }

    /**
     * Получение названия занятия.
     * @return название предмета.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Получение места проведения занятия.
     * @return номер аудитории.
     */
    public String getPlace() {
        return this.place;
    }


    /**
     * Получение иени преподавателя.
     * @return имя преподователя.
     */
    public String getLecturer() {
        return this.lecturer;
    }
}
