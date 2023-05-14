import java.lang.Exception

public class CliMenu(private vararg val mItems: String) {

    private fun prompt(): Int {

        print("wybierz opcję <0, ${mItems.size - 1}> ... ");

        return try {

            val value = readLine()?.trim()?.toInt()!!;

            if (mItems.indices.contains(value))
                value
            else
                prompt()

        } catch (e: Exception) {
            prompt();
        }
    }

    public fun choose(): Int {
        for (i in mItems.indices)
            println("$i.\t ${mItems[i]}");

        println();
        return prompt();
    }

    private var reactions: Array<() -> Unit> = arrayOf();
    public operator fun set(index: Int, action: () -> Unit) {
        reactions[index] = action;
    }

    public fun run() {
        reactions[choose()]();
    }

}