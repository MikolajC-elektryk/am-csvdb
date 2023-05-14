
public class TweakStructureMenu(private var db:CsvFile)
    : CliMenu("Dodaj kolumnę", "Lista pól")
{
    init {
        this[0] = ::addColumn;

        this[2] = ::listFields;
    }

    private fun addColumn(){

        println("podaj nazwe kolumny: ");

        var name = readln();

        if(name.isNullOrBlank())
           return addColumn();

        var s = db[0].getSize();
        db[0][s] = name;

        println("dodano kolumnę $name");

    }

    private fun listFields(){
        println("--------------------------------------");
        for (i in db[0].indecies()){
            print("${db[0][i]} | ")
        }
        println();
        println("--------------------------------------");

    }

    private fun deleteField(){
//        var number = CliMenu(*db[0].asArray()).choose();

        //TODO

    }

}

public class DbAccessMenu(private val dbName:String)
    : CliMenu("Edytuj strukturę bazy","Przedlądaj rekordy","Dodaj rekord","Usun rekord","Zapisz zmiany")
{
    private var dbFile : CsvFile = CsvFile(dbName);
    init{
        this[0] = { // edit structure
            TweakStructureMenu(dbFile).show();
        }

        this[1] = ::browseRecords;
        this[2] = ::addRecord;
        this[3] = ::deleteRecord;
        this[4] = { // save changes
            dbFile.commit();
        }

    }


    private fun addRecord(){

        println();
        var rec = mutableListOf<String>();

        for(fieldName in dbFile[0].asArray()){
            print("podaj $fieldName> ");
            var resp = readln();
            rec.add(resp);
        }

        for(i in rec.indices)
            dbFile.newRecord()[i] = rec[i];

        println("OK!");
    }

    private fun browseRecords(){
        println();
        var i : Int = 1;

        while (i < dbFile.count()){
            print("${i-1}> ${dbFile[i].toString()}");

            println();
            i++;
        }
        println();
    }

    private fun deleteRecord(){

        var i : Int = 1;

        var arr = mutableListOf<String>();

        while (i < dbFile.count()){
            arr.add(dbFile[i].toString());
            i++;
        }

        var toBeDeleated = CliMenu(*arr.toTypedArray(), isPersistent = false).choose();

        if(toBeDeleated < dbFile.count() - 1)
            dbFile.dropRecord(toBeDeleated + 1);

        println("\nOK!");


    }



}