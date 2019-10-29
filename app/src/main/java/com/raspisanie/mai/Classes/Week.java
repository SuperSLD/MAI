package com.raspisanie.mai.Classes;

import java.util.ArrayList;

/**
 * НЕделя расписания.
 * Содержит дни и предметы.
 */
public class Week {
    private int n;
    private String date;

    ArrayList<Day> days;

    /**
     * Пустой конструктор.
     * (Пока нигде не импользуется)
     */
    public Week() {

    }

    /**
     * Перегрузка с вводом дополнительных параметров.
     * @param n номер.
     * @param date дата.
     */
    public Week(int n, String date) {
        this.n = n;
        this.date = date;
        this.days = new ArrayList<>();
    }

    /**
     * Возврат номера недели.
     * @return int номер от одного до n.
     */
    public int getN() {
        return this.n;
    }

    /**
     * Возврат числа недели.
     * @return строка числа.
     */
    public String getDate() {
        return  this.date;
    }

    /**
     * Добавление дня в неделю.
     * @param day день.
     */
    public void addDay(Day day) {
        this.days.add(day);
    }

    /**
     * Возврат списка всех дней данной недели.
     * @return список дней.
     */
    public ArrayList<Day> getDaysList() {
        return this.days;
    }

    @Override
    public String toString() {
        String ret = "";

        ret += ConsoleColor.ANSI_YELLOW + "неделя " + this.n + " " + this.date + "\n" + ConsoleColor.ANSI_RESET;
        for (int i = 0; i < days.size(); i++) {
            ret += "___" + ConsoleColor.ANSI_BLUE + days.get(i).getName() + " "
                    + days.get(i).getDate() + ConsoleColor.ANSI_RESET + "\n";
            for (int j = 0; j < days.get(i).getSubjectList().size(); j++) {
                ret += "______" + days.get(i).getSubjectList().get(j).getName() + " " +
                        days.get(i).getSubjectList().get(j).getTime() + "\n"
                + "_________" + days.get(i).getSubjectList().get(j).getLecturer() + " " +
                        days.get(i).getSubjectList().get(j).getLecturer() + "\n";
            }
        }

        return ret;
    }
}
