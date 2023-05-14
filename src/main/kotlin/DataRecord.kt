data class DataRecord(private val line: String? = null) {

    private var mFields: MutableList<String> = mutableListOf();

    init {
        if (line != null)
            mFields = line.split(";").toMutableList();
    }

    public operator fun get(index: Int): String {
        if (index < 0)
            throw ArrayIndexOutOfBoundsException(index);

        return mFields[index];
    }

    public operator fun set(index: Int, value: Int) {
        this[index] = value.toString();
    }

    public operator fun set(index: Int, value: Float) {
        this[index] = value.toString();
    }

    public operator fun set(index: Int, value: Double) {
        this[index] = value.toString();
    }

    public operator fun set(index: Int, value: String) {
        if (index < 0)
            throw ArrayIndexOutOfBoundsException(index);

        if (index == mFields.count()) {
            mFields.add(value)

        } else if (index > mFields.count()) {
            for (i in 0 until index - mFields.count())
                mFields.add("")

            mFields.add(value);

        } else {
            mFields[index] = value;
        }

    }

    public fun wasModified(): Boolean {
        return line != toString();
    }

    override fun toString(): String {
        return mFields.joinToString(";");
    }

    public fun clear() {
        mFields.clear();
    }
}