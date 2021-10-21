import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MyHashMap <K,V> {

  private DLList < V > [] table;
  private HashTable < K > keys;
  private SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

  @SuppressWarnings("unchecked")
  public MyHashMap() {
    table = new DLList[702];
    keys = new HashTable<K>();
  }

  public void put(K key, V value) {
    if (table[key.hashCode()] != null && table[key.hashCode()].get(0) != null) {

      int i;
      for (i = 0; i < table[key.hashCode()].size(); i++) {

        Date date1,
        date2;
        try {
          date1 = format.parse(((MyImage) table[key.hashCode()].get(i)).getDate());
          date2 = format.parse(((MyImage) value).getDate());
          if (date1.compareTo(date2) < 0) {
            break;
          }
        } catch(ParseException e) {
          e.printStackTrace();
        }

      }

      table[key.hashCode()].add(i, value);
    } else {
      table[key.hashCode()] = new DLList < V > ();
      table[key.hashCode()].add(value);
      keys.add(key);
    }
  }

  public DLList < V > get(K key) {
    if (key.hashCode() < 0 || key.hashCode() >= 702) {
      return null;
    }
    return table[key.hashCode()];
  }

  public HashTable < K > getKeys() {
    return keys;
  }

  public String toString() {
    String s = "";
    for (int i = 0; i < keys.SIZE; i++) {
      if (keys.get(i).size() > 0) {
        System.out.println(keys.get(i) + " " + keys.get(i).hashCode());
        s += "bucket #" + i + " - " + keys.get(i).toString() + " - " + table[keys.get(i).hashCode()].toString() + "\n";
      }
    }
    return s;
  }

  public void remove(K key, V value) {
    DLList < V > vals = get(key);
    for (int i = 0; i < vals.size(); i++) {
      if (vals.get(i).equals(value)) {
        vals.remove(i);
      }
    }
  }

  public void remove(K key) {
    if (keys.contains(key)) {
      keys.remove(key);
      table[key.hashCode()] = null;
    }
  }

}