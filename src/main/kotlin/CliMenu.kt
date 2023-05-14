import java.lang.Exception

public open class CliMenu(protected vararg val mItems: String, private var isPersistent: Boolean = true) {

    private var reactions: Array<() -> Unit> =
        Array(mItems.size + 1) { i -> { println("No action set for menu item at index $i") } };

    public constructor(names: List<String>) : this(*names.toTypedArray()) {}

    init {
        reactions[mItems.size] = {
            this.disable();
        }
    }

    private fun prompt(): Int {

        print("wybierz opcję <0, ${mItems.size}> ... ");

        return try {

            val value = readLine()?.trim()?.toInt()!!;

            if ((mItems.indices + mItems.size + 1).contains(value)) {
                println();
                value
            } else
                prompt()

        } catch (e: Exception) {
            prompt();
        }
    }

    public fun choose(): Int {

        println();

        for (i in mItems.indices)
            println("$i.\t ${mItems[i]}");

        println("${mItems.size}.\t Cofnij");
        println();
        return prompt();
    }

    public operator fun set(index: Int, action: () -> Unit) {
        if (index == -1) { // assign the same callback to every item
            for (i in mItems.indices) {
                reactions[i] = action;
            }

        } else {
            reactions[index] = action;
        }
    }

    public fun show() {
        reactions[choose()]();
        if (isPersistent) {
            return show();
        }
    }

    public fun disable() {
        isPersistent = false;
    }

}