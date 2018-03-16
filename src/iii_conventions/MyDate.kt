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

operator fun MyDate.plus(timeInterval: TimeInterval): MyDate {
    return this.addTimeIntervals(timeInterval, 1)
}

operator fun MyDate.plus(repeatedTimeInterval: RepeatedTimeInterval): MyDate {
    var newDate: MyDate = this
    for (int in 1..repeatedTimeInterval.number) {
        newDate = newDate.plus(repeatedTimeInterval.timeInterval)
    }
    return newDate
}

operator fun MyDate.times(other: MyDate): MyDate {
    TODO()
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

operator fun TimeInterval.times(number: Int): RepeatedTimeInterval {
    return RepeatedTimeInterval(this, number)
}

data class RepeatedTimeInterval(val timeInterval: TimeInterval, val number: Int)

class DateRange(private val start: MyDate, private val endInclusive: MyDate) : Iterable<MyDate> {
    operator fun contains(date: MyDate): Boolean {
        return (this.start <= date) && (date <= endInclusive)
    }

    override fun iterator(): Iterator<MyDate> = object : Iterator<MyDate> {
        var current: MyDate = start

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
