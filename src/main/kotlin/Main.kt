import java.io.File
import kotlin.math.hypot

fun newDb(){
    print("podaj nazwę nowej bazy danych: ")
    var resp = readln();

    if(resp.isNullOrBlank()){
        return newDb();
    }

    var fname = StringBuilder();

    fname.append(resp);

    if(!fname.endsWith(".csv")){
        fname.append(".csv");
    }

    var file = CsvFile(fname.toString());
    file.commit();
    println("utworzono plik o nazwie '${fname.toString()}'");
}

fun existingFile(){
    println("wybierz plik")

    var picked:String = "";
    var fileNames = File(".").list { d: File?, s: String -> s.lowercase().endsWith(".csv") };
    var pickFileMenu = CliMenu(*fileNames, isPersistent = false);

    for (i in fileNames.indices){
        pickFileMenu[i] = {
            println("wybrano bazę danych o nazwie ${fileNames[i]}");
            picked = fileNames[i];
        }
    }

    pickFileMenu.show();
    DbAccessMenu(picked).show();
}

fun main(args: Array<String>) {

    //program uses 2 files, one csv contains datatable structure and the other includes actual data
    //sklep muzyczny


    var menu = CliMenu("nowy plik", "otwórz instiejący plik", "zamknij program", isPersistent = true);

    menu[0] = ::newDb;
    menu[1] = ::existingFile;

    menu[2] = {
        menu.disable();
    };

    menu.show();


}