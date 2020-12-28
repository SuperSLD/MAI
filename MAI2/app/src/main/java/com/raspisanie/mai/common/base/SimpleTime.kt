package pro.midev.iprofi.common.base

/**
 * Время в формате HH:MM
 */
class SimpleTime: Cloneable {
    private var h: Int
    private var m: Int

    constructor(h: Int, m: Int) {
        this.h = h
        this.m = m
    }

    constructor(s: String) {
        val mn = s.split(":")
        h = Integer.parseInt(mn[0])
        m = Integer.parseInt(mn[1])
    }

    fun getH() = h
    fun getM() = m

    /**
     * Добавление минут
     */
    fun addMin(m: Int): SimpleTime {
        this.h += m / 60
        this.m += m % 60
        if (this.m >= 60) {
            this.m = this.m % 60
            this.h += 1
        }
        return this
    }

    /**
     * Добавление часов
     */
    fun addHour(h: Int): SimpleTime {
        this.h += h
        if (h > 24) this.h -= 24
        return this
    }

    /**
     * Возвращает true если дата до вводимой
     */
    fun before(time: SimpleTime): Boolean {
        return time.h > this.h || (time.h == this.h && time.m > this.m)
    }

    /**
     * Возвращает true если дата после вводимой
     */
    fun after(time: SimpleTime): Boolean {
        return time.h <= this.h || (time.h == this.h && time.m < this.m)
    }

    override fun toString(): String {
        val strH: String = if (h >= 10) h.toString() else "0$h"
        val strM: String = if (m >= 10) m.toString() else "0$m"
        return "$strH:$strM"
    }

    override fun equals(other: Any?): Boolean {
        if (other is SimpleTime) {
            return this.h == other.h && this.m == other.m
        } else return false
    }

    public override fun clone(): SimpleTime {
        return SimpleTime(h, m)
    }

    override fun hashCode(): Int {
        var result = h
        result = 31 * result + m
        return result
    }
}