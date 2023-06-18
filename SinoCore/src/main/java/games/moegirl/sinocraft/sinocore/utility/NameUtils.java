package games.moegirl.sinocraft.sinocore.utility;

public class NameUtils {

    public static String to_snake_name(String camelName) {
        StringBuilder name = new StringBuilder().append(Character.toLowerCase(camelName.charAt(0)));
        for (int i = 1; i < camelName.length(); i++) {
            char c = camelName.charAt(i);
            if (Character.isUpperCase(c)) {
                name.append('_').append(Character.toLowerCase(c));
            } else {
                name.append(c);
            }
        }
        return name.toString();
    }

    public static String toPascalName(String snake_name, boolean hasSpace) {
        boolean next = true;
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < snake_name.length(); i++) {
            char c = snake_name.charAt(i);
            if (c == '_') {
                next = true;
                if (hasSpace && !name.isEmpty()) {
                    name.append(' ');
                }
                continue;
            }
            if (next) {
                next = false;
                name.append(Character.toUpperCase(c));
            } else {
                name.append(Character.toLowerCase(c));
            }
        }
        return name.toString();
    }
}
