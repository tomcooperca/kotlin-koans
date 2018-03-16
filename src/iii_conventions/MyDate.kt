package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        if (this.year != other.year) return this.year - other.year
        if (this.month != other.month) return this.month - other.month
        return this.dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange {
    return DateRange(this, other)
}



enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(private val start: MyDate, private val endInclusive: MyDate) : Iterable<MyDate> {
    operator fun contains(date: MyDate) : Boolean {
        return (this.start <= date) && (date <= endInclusive)
    }

    override fun iterator(): Iterator<MyDate> = object : Iterator<MyDate> {
        var current : MyDate = start

        override fun hasNext(): Boolean {
            return current <= endInclusive
        }

        override fun next(): MyDate {
            if (!hasNext()) {
                throw NoSuchElementException()
            }
            val result = current
            current = current.nextDay()
            return result
        }
    }
}
