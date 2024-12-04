import com.google.gson.Gson;
import java.io.FileReader;
import java.util.Map;
import java.util.Arrays; // Add missing import

public class ImportLevels {
    public static void main(String[] args) {
        Gson gson = new Gson();
        try {
            // Read the JSON file
            FileReader reader = new FileReader("/Users/game_/Library/CloudStorage/OneDrive-Personal/Documents/dev/Java/SonjasWaterSort/assets/levels/import_levels.json");

            // Convert JSON to a Map
            Map<String, Map<String, String[]>> levels = gson.fromJson(reader, Map.class);

            // Convert Map to an array of Struct<String>[]
            Struct<String>[] levelArray = new Struct[levels.size()];
            int i = 0;
            for (Map.Entry<String, Map<String, String[]>> entry : levels.entrySet()) {
                String levelName = entry.getKey();
                Map<String, String[]> tubes = entry.getValue();
                Struct<String>[] tubeArray = new Struct[tubes.size()];
                int j = 0;
                for (Map.Entry<String, String[]> tubeEntry : tubes.entrySet()) {
                    String tubeName = tubeEntry.getKey();
                    String[] colors = tubeEntry.getValue();
                    tubeArray[j] = new Struct<String>(tubeName, colors); // Specify type arguments for Struct
                    j++;
                }
                levelArray[i] = new Struct<String>(levelName, tubeArray); // Specify type arguments for Struct
                i++;
            }

            // Print the array of Struct<String>[]
            for (Struct<String> level : levelArray) {
                System.out.println(level);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Struct<T> {
        String name;
        T[] values;

        public Struct(String name, T[] values) {
            this.name = name;
            this.values = values;
        }

        @Override
        public String toString() {
            return "Struct{" +
                    "name='" + name + '\'' +
                    ", values=" + Arrays.toString(values) +
                    '}';
        }
    }
}
