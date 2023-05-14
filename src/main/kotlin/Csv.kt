import java.io.File

class CsvFile(private val filePath: String) {

    private var mRecords: MutableList<DataRecord> = mutableListOf();

    init {
        var file = File(filePath);
        if (file.exists()) {
            file.forEachLine { line ->
                mRecords.add(DataRecord(line));
            }
        }
    }

    public operator fun get(index: Int): DataRecord {

        if (index < 0)
            throw ArrayIndexOutOfBoundsException(index);

        return if (index == mRecords.count()) {
            mRecords.add(DataRecord());
            mRecords.last();

        } else if (index > mRecords.count()) {
            for (i in 0..index - mRecords.count())
                mRecords.add(DataRecord())

            mRecords.last();

        } else {
            mRecords[index];
        }

    }

    public fun newRecord(): DataRecord {
        var record = DataRecord();
        mRecords.add(record);
        return record;
    }

    public fun count(): Int {
        return mRecords.size;
    }

    public fun commit() {
        var out: String = "";
        mRecords.forEach() { record ->
            out += record.toString() + "\n";
        }
        File(filePath).writeText(out);
    }

    public fun clear() {
        mRecords.clear();
    }

    public fun dropRecord(index: Int) {
        mRecords.removeAt(index);
    }


}